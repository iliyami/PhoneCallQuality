package com.example.debaran

import android.Manifest
import android.os.Bundle
import android.telephony.TelephonyManager
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.debaran.core.constants.FrequentlyMessages
import com.example.debaran.core.theme.DebaranTheme
import com.example.debaran.core.utils.LocationManagerUtil
import com.example.debaran.core.utils.LocationManagerUtil.requestLocationAccess
import com.example.debaran.features.callQuality.data.repositories.CallQualityRepositoryImpl
import com.example.debaran.features.callQuality.data.repositories.CallStateRepositoryImpl
import com.example.debaran.features.callQuality.domain.entities.MyCallState
import com.example.debaran.features.callQuality.domain.usecases.CallMonitoring
import com.example.debaran.features.callQuality.domain.usecases.Dialer
import com.example.debaran.features.callQuality.domain.usecases.PhoneStateChecker
import com.example.debaran.features.callQuality.views.CallQualityViewModel
import com.example.debaran.features.callQuality.views.DashboardScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DebaranTheme {
                val launcher = this.requestLocationAccess()

                val callRepo = CallQualityRepositoryImpl.getInstance()
                val callMonitoring = CallMonitoring(this)

                val hasLocationAccess = LocationManagerUtil.hasLocationAccess.observeAsState()

                // Phone State Checker Initialization
                val phoneStateChecker = PhoneStateChecker(this)
                phoneStateChecker.registerPhoneStateChecker()
                val callStateRepo = CallStateRepositoryImpl.getInstance()
                val callState = callStateRepo.getCallStateLiveData().observeAsState()

                DashboardScreen(
                    onConnectClick = {
                        if (hasLocationAccess.value == true) {
                            callMonitoring.connect(callRepo)
                            callStateRepo.setCallStateLiveData(
                                MyCallState(
                                    phoneStateChecker.getCallState(),
                                    ""
                                )
                            )
                            if (phoneStateChecker.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK)
                                CallMonitoring(this@MainActivity).startCheckingCallQuality(
                                    callRepo
                                )
                            else {
                                // TODO
                                //  stop the timer for better experience
                            }
                        }
                        else
                            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                     },
                    onDisconnect = {callMonitoring.disconnect()},
                    onCallClick = {Dialer.getInstance().dial(this)},
                )



//                Surface(modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Box (
//                        Modifier.fillMaxSize(),
//                        Alignment.Center
//                    ) {
//                        FloatingActionButton(onClick = {
//                            if (isLocationOn.value) {
//                                callStateRepo.setCallStateLiveData(MyCallState(phoneStateChecker.getCallState(),
//                                    ""))
//                                if (phoneStateChecker.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK)
//                                    CallMonitoring(this@MainActivity).startCheckingCallQuality(
//                                        callRepo
//                                    )
//                                else {
//                                    // TODO
//                                //  stop the timer for better experience
//                                }
//                            } else
//                                launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//                        }) {
//                            Icon(Icons.Rounded.Call, "Call")
//                        }
//                    }
//
//                    if (isLocationOn.value) {
//                        if (callState.value?.callState == TelephonyManager.CALL_STATE_OFFHOOK)
//                            CallQualityViewModel(callRepo).CallQualityView()
//                        else
//                            Alert("No Call in Process!")
//                    }
//                    else
//                        Alert("Click To Get Permission!")
//                }
            }
        }
    }
}


//@Composable
//fun Alert(text: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "$text",
//        modifier = modifier
//    )
//}
