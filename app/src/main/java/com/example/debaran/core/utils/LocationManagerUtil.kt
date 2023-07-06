package com.example.debaran.core.utils

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.lifecycle.MutableLiveData
import com.example.debaran.R
import com.example.debaran.core.constants.FrequentlyMessages
import com.example.debaran.core.utils.AppUtils.launchUrl
import com.example.debaran.features.callQuality.domain.entities.MyCallState
import es.dmoral.toasty.Toasty

object LocationManagerUtil {

    val hasLocationAccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun turnOnDeviceLocation(context: Context): Boolean {
        // Get the LocationManager instance
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Check if the device location is enabled
        val isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

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
        ) {
                isGranted ->
            run {
                if (isGranted) {
                    hasLocationAccess.value = turnOnDeviceLocation(this)
                } else {
                    Toasty.error(this, getString(R.string.permission_denied_by_user)).show()
                }
            }
        }
    }
}