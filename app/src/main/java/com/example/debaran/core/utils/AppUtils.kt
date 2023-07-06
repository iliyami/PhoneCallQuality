package com.example.debaran.core.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.debaran.R
import es.dmoral.toasty.Toasty

object AppUtils {
    fun Context.launchUrl(url: String) {
        try {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
                startActivity(this)
            }
        } catch (e: Exception) {
            Toasty.error(this, getString(R.string.error_no_activity_url)).show()
        }
    }
}