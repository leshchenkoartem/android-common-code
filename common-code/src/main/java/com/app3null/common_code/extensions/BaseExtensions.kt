package com.app3null.common_code.extensions

import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.LocationManager
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.app3null.common_code.R
import com.google.android.material.snackbar.Snackbar
import com.zuluft.mvvm.common.extensions.colorOf
import com.zuluft.mvvm.common.extensions.notNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File

fun String.valueOrNull(): String? = if (this.isEmpty()) null else this

inline fun String.notEmpty(callback: (String) -> Unit) {
    if (this.isNotEmpty()) {
        callback(this)
    }
}

inline fun <T : Any> T?.notNull(callback: (T) -> Unit): T? {
    return this?.also(callback)
}

inline fun <T : Any> List<T>?.notNullAndNotEmpty(callback: (List<T>) -> Unit) {
    if (!this.isNullOrEmpty()) {
        callback(this)
    }
}

fun <T> List<T>.replace(newValue: T, block: (T) -> Boolean): List<T> {
    return map {
        if (block(it)) newValue else it
    }
}

fun Context.showAlertMessage(@StringRes titleRes: Int, @StringRes messageRes: Int, @StringRes buttonOk: Int = R.string.buttons_ok, @StringRes buttonCancel: Int = R.string.buttons_cancel, listener: ((DialogInterface, Int) -> Unit)? = null) {
    showAlertMessage(getString(titleRes), getString(messageRes), getString(buttonOk), getString(buttonCancel), listener)
}

fun Context.showAlertMessage(title: String?, message: String?, buttonOk: String, buttonCancel: String, positiveListener: ((DialogInterface, Int) -> Unit)? = null, negativeListener: ((DialogInterface, Int) -> Unit)? = null) {
    AlertDialog.Builder(this)
        .apply {
            title.notNull {
                setTitle(it)
            }
        }
        .setMessage(message)
        .setPositiveButton(buttonOk, positiveListener)
        .setNegativeButton(buttonCancel, negativeListener)
        .show()
}

fun Fragment.showSimpleSelectorDialog(@StringRes titleRes: Int?, items: List<String>, listener: (DialogInterface, Int) -> Unit, negativeButtonText: Int = R.string.buttons_cancel, negativeListener: ((DialogInterface, Int) -> Unit)? = null) {
    requireContext().showSimpleSelectorDialog(titleRes, items, listener, negativeButtonText, negativeListener)
}

fun Context.showSimpleSelectorDialog(@StringRes titleRes: Int?, items: List<String>, listener: (DialogInterface, Int) -> Unit, negativeButtonText: Int = R.string.buttons_cancel, negativeListener: ((DialogInterface, Int) -> Unit)? = null) {
    val dialog = AlertDialog.Builder(this)
    titleRes.notNull {
        dialog.setTitle(it)
    }
    dialog.setItems(items.toTypedArray(), listener)
    negativeListener.notNull {
        dialog.setNegativeButton(getString(negativeButtonText), negativeListener)
    }
    dialog.show()
}

fun Fragment.showThrowableMessage(throwable: Throwable) {
    requireContext().showThrowableMessage(throwable)
}

fun Context.showThrowableMessage(throwable: Throwable) {
    val dialog = AlertDialog.Builder(this)
        .setMessage(throwable.localizedMessage)
        .setPositiveButton(R.string.buttons_ok, null)
        .create()
    dialog.show()
}

//fun View.showErrorSnackBar(title: String, listener: () -> Unit) {
//    Snackbar.make(this, title, Snackbar.LENGTH_INDEFINITE).apply {
//        view.setBackgroundColor(ContextCompat.getColor(this.context, R.color.colorAccent))
//        setAction(context.resources.getString(R.string.buttons_retry)) {
//            listener()
//        }
//        setActionTextColor(context.colorOf(R.color.white))
//        show()
//    }
//}

fun Context.isGPSEnabled(): Boolean {
    val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

fun Context.isLocationGranted(): Boolean {
    val accessFineLocation = ContextCompat.checkSelfPermission(
        this,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    val accessCoarseLocation = ContextCompat.checkSelfPermission(
        this,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    return accessFineLocation && accessCoarseLocation
}

fun Context.isAccessLocationAndGps(): Boolean {
    return isLocationGranted() && isGPSEnabled()
}

fun String.convertToPlainTextRequestBody(keyName: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(keyName, this)
}

fun File.convertToImageFileRequestBody(keyName: String): MultipartBody.Part {
    val requestBody = this.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(keyName, name, requestBody)
}

fun File.convertToVideoFileRequestBody(keyName: String): MultipartBody.Part {
    val requestBody = this.asRequestBody("video/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(keyName, name, requestBody)
}

fun Pair<String, Any>.valuesToJson(): RequestBody =
    JSONObject(mapOf(this)).toString().toRequestBody("application/json".toMediaTypeOrNull())

fun Map<String, Any>.valuesToJson(): RequestBody =
    JSONObject(this).toString().toRequestBody("application/json".toMediaTypeOrNull())