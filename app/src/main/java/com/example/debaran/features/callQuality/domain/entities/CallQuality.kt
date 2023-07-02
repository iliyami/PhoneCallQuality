package com.example.debaran.features.callQuality.domain.entities

import android.telephony.CellInfo

data class CallQuality(
    val signalStrengthLevel: String,
    val cellInfo: CellInfo?,
)