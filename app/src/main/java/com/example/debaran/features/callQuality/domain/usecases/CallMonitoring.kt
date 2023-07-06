package com.example.debaran.features.callQuality.domain.usecases


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.CellInfo
import android.telephony.CellInfoLte
import android.telephony.CellInfoNr
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.example.debaran.core.utils.Conversions
import com.example.debaran.features.callQuality.domain.entities.CallQuality
import com.example.debaran.features.callQuality.domain.repositories.CallQualityRepository
import java.util.Timer
import java.util.TimerTask


class CallMonitoring(private val context: Context) {
    private val telephonyManager =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    companion object {
        val isMeasuring: MutableLiveData<Boolean> by lazy {
            MutableLiveData<Boolean>()
        }

        var timer = Timer()
    }

    fun connect(callQualityRepo: CallQualityRepository) {
        isMeasuring.postValue(true)
        startCheckingCallQuality(callQualityRepo)
    }

    fun disconnect() {
        timer.cancel()
        timer.purge()
        isMeasuring.postValue(false)
    }

    fun getSignalStrengthLevel(): String {
        val signalStrength = telephonyManager.signalStrength
        val callQualityLevel: String = when (signalStrength?.level) {
            0 -> "Unknown"
            1 -> "Poor"
            2 -> "Moderate"
            3 -> "Good"
            4 -> "Great"
            else -> "Error"
        }
        return callQualityLevel
    }

    fun getBestCellInfo(): CellInfo? {
        val cellInfoList = if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            telephonyManager.allCellInfo
        } else
            return null

        if (cellInfoList.isEmpty()) {
            return null
        }

        return cellInfoList[0]



        // Update the call quality state
//        callQuality.value = callQuality

    }


    fun getAvgCallQuality(cellInfo: CellInfo): Double? {
        // Calculate a weighted average of the RSRP, RSRQ, SINR, and MOS values
        when (cellInfo) {
            // EUTRAN (4G LTE): RSRP, RSRQ, RSSNR
            is CellInfoLte -> {
                val cellDetail = cellInfo.cellSignalStrength
                return (0.2 * cellDetail.rsrp + 0.3 * cellDetail.rsrq + 0.4 * (Conversions.dBTodBm(cellDetail.rssnr )) /*+ 0.1 * mos*/) / 1.0
            }

            // NGRAN (5G NR): SSRSRP, SSRSRQ, SSSINR
            is CellInfoNr -> {
                val cellDetail = cellInfo.cellSignalStrength
                return (0.2 * cellDetail.dbm + 0.3 * cellDetail.asuLevel + 0.4 * cellDetail.dbm /*+ 0.1 * mos*/) / 1.0
            }

            else -> null
        }
        return null
    }

    fun startCheckingCallQuality(callQualityRepo: CallQualityRepository) {
        // Start a timer to check the call quality every 1 second
        timer = Timer()
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    val bestCellInfo = getBestCellInfo()
                    val newCallQuality = CallQuality(
                        getSignalStrengthLevel(),
                        bestCellInfo,
                        if (bestCellInfo == null) null else getAvgCallQuality(bestCellInfo)
                    )
                    callQualityRepo.getCallQuality(
                        newCallQuality
                    )
                }
            },
            0,
            1000,
        )
    }
}