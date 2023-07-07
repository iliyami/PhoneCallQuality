package com.example.debaran

import android.Manifest
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import com.example.debaran.core.theme.DebaranTheme
import com.example.debaran.core.utils.LocationManagerUtil
import com.example.debaran.core.utils.LocationManagerUtil.isLocationEnabled
import com.example.debaran.core.utils.LocationManagerUtil.requestLocationAccess
import com.example.debaran.features.callQuality.data.repositories.CallQualityRepositoryImpl
import com.example.debaran.features.callQuality.data.repositories.CallStateRepositoryImpl
import com.example.debaran.features.callQuality.domain.entities.MyCallState
import com.example.debaran.features.callQuality.domain.usecases.CallMonitoring
import com.example.debaran.features.callQuality.domain.usecases.Dialer
import com.example.debaran.features.callQuality.domain.usecases.PhoneStateChecker
import com.example.debaran.features.callQuality.views.DashboardScreen
import com.example.debaran.features.callQuality.views.components.ConnectivityStatus
import es.dmoral.toasty.Toasty


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DebaranTheme {
                // Location Access Managing
                val launcher = this.requestLocationAccess()
                val hasLocationAccess = LocationManagerUtil.hasDeviceLocation.observeAsState()

                // Call Monitoring Initialization
                val callRepo = CallQualityRepositoryImpl.getInstance()
                val callMonitoring = CallMonitoring(this)
                CallMonitoring.connectivityStatus.value = ConnectivityStatus.DISCONNECT


                // Phone State Checker Initialization
                val phoneStateChecker = PhoneStateChecker(this)
                phoneStateChecker.registerPhoneStateChecker()
                val callStateRepo = CallStateRepositoryImpl.getInstance()


                DashboardScreen(
                    onConnectClick = {
                        if (CallMonitoring.connectivityStatus.value == ConnectivityStatus.DISCONNECT) {
                            if (hasLocationAccess.value == true && isLocationEnabled(this)) {
                                CallMonitoring.connectivityStatus.value = ConnectivityStatus.CONNECTING
                                callStateRepo.setCallStateLiveData(
                                    MyCallState(
                                        phoneStateChecker.getCallState(),
                                        ""
                                    )
                                )
                                if (phoneStateChecker.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK) {
                                    callMonitoring.connect(callRepo)
                                } else {
                                    callMonitoring.disconnect()
                                    Toasty.warning(this, getString(R.string.no_call_detected)).show()
                                }
                            }
                            else {
                                launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            }
                        }
                                     },
                    onDisconnect = {callMonitoring.disconnect()},
                    onCallClick = {Dialer.getInstance().dial(this, this)},
                    connectivityStatusLiveData = CallMonitoring.connectivityStatus
                )
            }
        }
    }
}
