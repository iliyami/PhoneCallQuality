package com.example.debaran.features.callQuality.domain.entities

import android.telephony.CellInfo

data class MyCallState(
    val callState: Int,
    val phoneNumber: String?,
)