package com.example.debaran.features.callQuality.views

import android.telephony.CellInfo
import android.telephony.CellInfoLte
import android.telephony.CellInfoNr
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.debaran.core.utils.Conversions
import com.example.debaran.features.callQuality.data.repositories.CallQualityRepositoryImpl

class CallQualityViewModel(
) : ViewModel() {

    private val callQualityLiveData = CallQualityRepositoryImpl.getInstance().getCallQualityLiveData()

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun CallQualityView() {
        val callQualityState = callQualityLiveData.observeAsState()
        Column (
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
                ) {
            AnimatedContent(targetState = callQualityState.value) {
                InfoText("Signal Strength Level: ", "${callQualityState.value?.signalStrengthLevel}")
            }
            CellInfoView(cellInfo = callQualityState.value?.cellInfo)
            val totalCallQuality = callQualityState.value?.totalCallQuality
            if (totalCallQuality != null) {
                InfoText(
                    "Total Call Quality: ", String.format("%.2f", totalCallQuality),
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
                    InfoText("RSRP [dbM]: ", "${cellInfo.cellSignalStrength.rsrp}")
                    InfoText("RSRQ: ", "${cellInfo.cellSignalStrength.rsrq}")
                    InfoText("RSSI [dbM]: ", "${cellInfo.cellSignalStrength.rssi}")
                    InfoText("Signal-to-Noise [dBm]: ",
                            String.format("%.2f", Conversions.dBTodBm(cellInfo.cellSignalStrength.rssnr))
                    )
                }
            }

            // NGRAN (5G NR): SSRSRP, SSRSRQ, SSSINR
            is CellInfoNr -> {
                Column() {
                    InfoText("Level: ", "${cellInfo.cellSignalStrength.level}")
//                    Text(text = "SSRSRQ: ${cellInfo.cellSignalStrength.rsrq}")
                    InfoText("SSRSRP: ", "${cellInfo.cellSignalStrength.dbm}")
//                    Text(text = "SSSINR: ${cellInfo.cellSignalStrength.rssi}")
                }
            }

            else -> {
                // Show Toast - NOT UMTS, LTE, or 5G
            }
        }
    }

    @Composable
    fun InfoText(title: String, value: String) {
        Row(
        ) {
            Text(text = title,
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer(Modifier.weight(1f))

            Text(text = value,
                style = MaterialTheme.typography.titleLarge,
            )
        }

    }
}