package com.example.debaran.features.callQuality.views

import android.telephony.CellInfo
import android.telephony.CellInfoLte
import android.telephony.CellInfoNr
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModel
import com.example.debaran.core.utils.Conversions
import com.example.debaran.features.callQuality.domain.repositories.CallQualityRepository

class CallQualityViewModel(
    repository: CallQualityRepository
) : ViewModel() {

    private val callQualityLiveData = repository.getCallQualityLiveData()

    @Composable
    fun CallQualityView() {
        val callQualityState = callQualityLiveData.observeAsState()
        Column {
            Text(text = "Call Quality")
            Text(text = "Signal Strength Level: ${callQualityState.value?.signalStrengthLevel}")
            CellInfoView(cellInfo = callQualityState.value?.cellInfo)
            val totalCallQuality = callQualityState.value?.totalCallQuality
            if (totalCallQuality != null) {
                Text(
                    text = "Total Call Quality: ${String.format("%.2f", totalCallQuality)}",
                )
            }
        }
    }

    @Composable
    fun CellInfoView(cellInfo: CellInfo?) {
        when (cellInfo) {
            // EUTRAN (4G LTE): RSRP, RSRQ, RSSNR
            is CellInfoLte -> {
                Column() {
                    Text(text = "RSRP [dbM]: ${cellInfo.cellSignalStrength.rsrp}")
                    Text(text = "RSRQ: ${cellInfo.cellSignalStrength.rsrq}")
                    Text(text = "RSSI [dbM]: ${cellInfo.cellSignalStrength.rssi}")
                    Text(text = "Signal-to-Noise [dBm]: " +
                            String.format("%.2f", Conversions.dBTodBm(cellInfo.cellSignalStrength.rssnr))
                    )
                }
            }

            // NGRAN (5G NR): SSRSRP, SSRSRQ, SSSINR
            is CellInfoNr -> {
                Column() {
                    Text(text = "Level: ${cellInfo.cellSignalStrength.level}")
//                    Text(text = "SSRSRQ: ${cellInfo.cellSignalStrength.rsrq}")
                    Text(text = "SSRSRP: ${cellInfo.cellSignalStrength.dbm}")
//                    Text(text = "SSSINR: ${cellInfo.cellSignalStrength.rssi}")
                }
            }

            else -> {
                // Show Toast - NOT UMTS, LTE, or 5G
            }
        }
    }
}