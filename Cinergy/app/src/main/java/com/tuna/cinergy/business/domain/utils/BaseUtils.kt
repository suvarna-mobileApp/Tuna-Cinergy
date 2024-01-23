package com.tuna.cinergy.business.domain.utils

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.graphics.*
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.*
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


val <T>T.exhaustive: T
    get() = this
var secretKey = "402620C92552D9303C58B901B43B0A41718E772C19520DD9A9AA52CE5A8FCB99"
var deviceType = "2"
var deviceId = "CI-3"
var loginType = "2"
var locationId = "5"

fun dateFormater(oldFormat: String?): String? {
    var convertedDate: String? = null
    try {
        val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val targetFormat: DateFormat = SimpleDateFormat("MMM dd", Locale.ENGLISH)
        val date: Date = originalFormat.parse(oldFormat)
        convertedDate = targetFormat.format(date)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return convertedDate
}

fun weekFormater(oldFormat: String?): String? {
    var convertedDate: String? = null
    try {
        val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val targetFormat: DateFormat = SimpleDateFormat("EE", Locale.ENGLISH)
        val date: Date = originalFormat.parse(oldFormat)
        convertedDate = targetFormat.format(date)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return convertedDate
}

fun isOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val n = cm.activeNetwork
        if (n != null) {
            val nc = cm.getNetworkCapabilities(n)
            //It will check for both wifi and cellular network
            return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI)
        }
        return false
    } else {
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}
