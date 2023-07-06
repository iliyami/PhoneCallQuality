package com.example.debaran.features.callQuality.domain.usecases

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData


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

    fun dial(context: Context) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$dialerValue") //change the number
        context.startActivity(callIntent)
    }


}