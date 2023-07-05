package com.example.debaran.core.utils

import kotlin.math.log10

class Conversions {
    companion object {
        fun dBTodBm(dB: Double): Double {
            // dBm is defined as 10 * log10(power) where power is in milli-watts (mW).
            // So, to convert from dB to dBm, we need to multiply by 10 and then add 100.
            return 10 * log10(dB) + 100
        }

        fun dBTodBm(dB: Int): Double {
            // dBm is defined as 10 * log10(power) where power is in milli-watts (mW).
            // So, to convert from dB to dBm, we need to multiply by 10 and then add 100.
            return 10 * log10(dB.toDouble()) + 100
        }
    }
}