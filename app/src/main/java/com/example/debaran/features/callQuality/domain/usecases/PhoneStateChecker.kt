@file:Suppress("DEPRECATION")

package com.example.debaran.features.callQuality.domain.usecases

import android.content.Context
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import com.example.debaran.features.callQuality.data.repositories.CallStateRepositoryImpl
import com.example.debaran.features.callQuality.domain.entities.MyCallState

class PhoneStateChecker(private val context: Context) : PhoneStateListener() {
    private val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    val callStateRepo = CallStateRepositoryImpl.getInstance()

    fun getCallState(): Int {
        return telephonyManager.callState
    }

    fun registerPhoneStateChecker() {
        val phoneStateListener = PhoneStateChecker(context)
        (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager)
            .listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
    }

    override fun onCallStateChanged(callState: Int, phoneNumber: String?) {
        callStateRepo.setCallStateLiveData(MyCallState(callState = callState, phoneNumber = phoneNumber))

        when (callState) {
            TelephonyManager.CALL_STATE_IDLE -> {
                // The user is not calling
                Log.println(Log.DEBUG, null, "DEBUG LOG: IDLE")
            }
            TelephonyManager.CALL_STATE_RINGING -> {
                // The user is receiving a call
                Log.println(Log.DEBUG, null, "DEBUG LOG: Ringing")
            }
            TelephonyManager.CALL_STATE_OFFHOOK -> {
                // The user is in a call
                Log.println(Log.DEBUG, null, "DEBUG LOG: In Call [OFFHOOK]")
            }
        }
    }
}