package com.example.debaran.core.utils

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.lifecycle.MutableLiveData
import com.example.debaran.R
import com.example.debaran.features.callQuality.domain.usecases.CallMonitoring
import es.dmoral.toasty.Toasty

object LocationManagerUtil {


    val hasDeviceLocation: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun isLocationEnabled(context: Context): Boolean {
        // Get the LocationManager instance
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Check if the device location is enabled
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun turnOnDeviceLocation(context: Context): Boolean {
        // Check if the device location is enabled
        val isLocationEnabled = isLocationEnabled(context)

        if (!isLocationEnabled) {
            // Request the user to enable the device location
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(intent)
        }

        return isLocationEnabled
    }

    @Composable
    fun Context.requestLocationAccess(): ManagedActivityResultLauncher<String, Boolean> {
        return rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            run {
                if (isGranted) {
                    if (isLocationEnabled(this)) {
                        // Pre-fetch Best Cell information
                        CallMonitoring(this).getBestCellInfo()
                        hasDeviceLocation.value = true
                    } else {
                        turnOnDeviceLocation(this)
                        Toasty.warning(this, getString(R.string.please_turn_on_location)).show()
                    }
                } else {
                    Toasty.error(this, getString(R.string.permission_denied_by_user)).show()
                }
            }
        }
    }
}