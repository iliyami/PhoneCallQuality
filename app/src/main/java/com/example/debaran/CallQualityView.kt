package com.example.debaran

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

class CallQualityView {
    @Composable
    fun QualityButton(callQualityChecker: CallQualityChecker) {
        val callQuality = remember { mutableStateOf("") }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Call Quality")
            Text(text = "Signal Strength Level: ${callQuality.value}")

            Button(onClick = {
                val contact = "+98"
                val intent = Intent(Intent.ACTION_DIAL)
//                intent.data = Uri.parse("tel:$contact")
//                startActivity(intent)
//                while (call.isAlive) {
//                    callQuality.value = callQualityChecker.checkCallQuality()
//                }
            }) {
                Text(text = "Make Call")
            }
        }
    }
}
