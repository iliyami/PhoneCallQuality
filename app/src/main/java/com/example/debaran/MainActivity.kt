package com.example.debaran

import android.Manifest
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import com.example.debaran.core.theme.DebaranTheme
import com.example.debaran.core.utils.LocationManagerUtil
import com.example.debaran.core.utils.LocationManagerUtil.requestLocationAccess
import com.example.debaran.features.callQuality.data.repositories.CallQualityRepositoryImpl
import com.example.debaran.features.callQuality.data.repositories.CallStateRepositoryImpl
import com.example.debaran.features.callQuality.domain.entities.MyCallState
import com.example.debaran.features.callQuality.domain.usecases.CallMonitoring
import com.example.debaran.features.callQuality.domain.usecases.Dialer
import com.example.debaran.features.callQuality.domain.usecases.PhoneStateChecker
import com.example.debaran.features.callQuality.views.DashboardScreen
import com.example.debaran.features.callQuality.views.components.ConnectivityStatus


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
                CallMonitoring.connectivityStatus.value = ConnectivityStatus.NONE
                DashboardScreen(
                    onConnectClick = {
                        if (hasLocationAccess.value == true) {
                            callStateRepo.setCallStateLiveData(
                                MyCallState(
                                    phoneStateChecker.getCallState(),
                                    ""
                                )
                            )
                            if (phoneStateChecker.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK) {
                                callMonitoring.connect(callRepo)
                            }
                            else {
                                callMonitoring.disconnect()
                            }
                        }
                        else
                            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                     },
                    onDisconnect = {callMonitoring.disconnect()},
                    onCallClick = {Dialer.getInstance().dial(this, this)},
                    connectivityStatus = CallMonitoring.connectivityStatus.value!!
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
