package com.example.debaran.core.utils

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings

class LocationManagerUtil {
    companion object {
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
    }
}