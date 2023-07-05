package com.example.debaran

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.debaran.core.constants.FrequentlyMessages
import com.example.debaran.core.theme.DebaranTheme
import com.example.debaran.core.utils.LocationManagerUtil
import com.example.debaran.features.callQuality.data.repositories.CallQualityRepositoryImpl
import com.example.debaran.features.callQuality.domain.usecases.CallQualityChecker
import com.example.debaran.features.callQuality.views.CallQualityViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DebaranTheme {
                val isLocationOn = remember { mutableStateOf(false) }
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) {
                        isGranted ->
                    run {
                        if (isGranted) {
                            isLocationOn.value = LocationManagerUtil.turnOnDeviceLocation(this@MainActivity)
                        } else {
                            Toast.makeText(this, FrequentlyMessages.permissionDeniedByUser, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box (
                        Modifier.fillMaxSize(),
                        Alignment.Center
                    ) {
                        FloatingActionButton(onClick = {
                            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }) {
                            Icon(Icons.Rounded.Call, "Call")
                        }
                    }

                    if (isLocationOn.value) {
                        val callRepo = CallQualityRepositoryImpl(this@MainActivity)
                        CallQualityChecker(this@MainActivity).startCheckingCallQuality(callRepo)
                        CallQualityViewModel(callRepo).CallQualityView()
                    }
                    else {
                        Alert("No Permissions")
                    }
                }
            }
        }
    }
}


@Composable
fun Alert(text: String, modifier: Modifier = Modifier) {
    Text(
        text = "$text!",
        modifier = modifier
    )
}
