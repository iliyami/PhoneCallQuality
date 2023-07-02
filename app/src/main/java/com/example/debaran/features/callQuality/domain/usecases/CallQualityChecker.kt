package com.example.debaran.features.callQuality.domain.usecases


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.CellInfo
import android.telephony.CellInfoLte
import android.telephony.CellInfoNr
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import com.example.debaran.features.callQuality.data.repositories.CallQualityRepositoryImpl
import com.example.debaran.features.callQuality.domain.entities.CallQuality
import com.example.debaran.features.callQuality.domain.repositories.CallQualityRepository
import java.util.Timer
import java.util.TimerTask


class CallQualityChecker(private val context: Context) {
    private val telephonyManager =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager


    fun getSignalStrengthLevel(): String {
        val signalStrength = telephonyManager.signalStrength
        val callQuality: String = when (signalStrength?.level) {
            0 -> "Unknown"
            1 -> "Poor"
            2 -> "Moderate"
            3 -> "Good"
            4 -> "Great"
            else -> "Unknown"
        }
        return callQuality
    }

    fun getBestCellInfo(): CellInfo? {
        val cellInfoList = if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null
        } else
            telephonyManager.allCellInfo

        return cellInfoList[0]

        // Calculate a weighted average of the RSRP, RSRQ, SINR, and MOS values
//        val callQuality = (0.2 * rsrp + 0.3 * rsrq + 0.4 * sinr + 0.1 * mos) / 1.0

        // Update the call quality state
//        callQuality.value = callQuality

    }

    fun startCheckingCallQuality(callQualityRepo: CallQualityRepository) {
        // Start a timer to check the call quality every 1 second
        val timer = Timer()
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    callQualityRepo.getCallQuality(
                        CallQuality(
                            getSignalStrengthLevel(),
                            getBestCellInfo(),
                        )
                    )
                }
            },
            0,
            1000,
        )
    }
}