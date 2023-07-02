package com.example.debaran.features.callQuality.views

import android.telephony.CellInfo
import android.telephony.CellInfoLte
import android.telephony.CellInfoNr
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.debaran.features.callQuality.domain.repositories.CallQualityRepository

class CallQualityViewModel(
    private val repository: CallQualityRepository
) : ViewModel() {

    private val callQualityLiveData = repository.getCallQualityLiveData()

    @Composable
    fun CallQualityView() {
        Column {
            Text(text = "Call Quality")
            Text(text = "Signal Strength Level: ${callQualityLiveData.value?.signalStrengthLevel}")
            cellInfoView(cellInfo = callQualityLiveData.value?.cellInfo)
        }
    }

    @Composable
    fun cellInfoView(cellInfo: CellInfo?) {
        when (cellInfo) {
            // EUTRAN (4G LTE): RSRP, RSRQ, RSSNR
            is CellInfoLte -> {
                Column() {
                    Text(text = "Level: ${cellInfo.cellSignalStrength.level}")
                    Text(text = "RSRP: ${cellInfo.cellSignalStrength.rsrp}")
                    Text(text = "RSRQ: ${cellInfo.cellSignalStrength.rsrq}")
                    Text(text = "RSSNR: ${cellInfo.cellSignalStrength.rssnr}")
                    Text(text = "RSSI: ${cellInfo.cellSignalStrength.rssi}")
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