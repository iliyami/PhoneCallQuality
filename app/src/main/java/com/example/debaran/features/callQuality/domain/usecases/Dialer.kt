package com.example.debaran.features.callQuality.domain.usecases

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.debaran.R
import es.dmoral.toasty.Toasty


class Dialer private constructor() {

    companion object {

        @Volatile
        private var instance: Dialer? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: Dialer().also { instance = it }
            }
    }

    val dialerValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun dial(context: Context, activity: Activity) {
        val permissionCheck =
            ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity, arrayOf(Manifest.permission.CALL_PHONE),
                123
            )
        } else {
            if (dialerValue.value != null) {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:${dialerValue.value}")
                context.startActivity(callIntent)
            } else {
                Toasty.error(context, "Please enter your number!").show()
            }
        }
    }


}