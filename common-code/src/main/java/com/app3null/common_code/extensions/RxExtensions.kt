package com.app3null.common_code.extensions

import android.Manifest
import android.content.Context
import android.widget.TextView
import com.app3null.common_code.Log
import com.jakewharton.rxbinding4.widget.textChanges
import com.tbruyelle.rxpermissions3.RxPermissions
import com.app3null.common_code.mvvm.common.extensions.just
import io.reactivex.Completable
import io.reactivex.Observable
import java.net.ConnectException

// Temporarily commented for because of version change conflicts
//fun Observable<*>.obtainAccessCamera(rxPermissions: RxPermissions): Observable<Boolean> {
//    return this.compose(
//        rxPermissions.ensure(Manifest.permission.CAMERA)
//    ).onErrorReturn { false }
//}
//
//fun Observable<*>.obtainAccessMedia(rxPermissions: RxPermissions): Observable<Boolean> {
//    return this.compose(
//        rxPermissions.ensure(
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.CAMERA
//        )
//    ).onErrorReturn { false }
//}

fun Observable<*>.checkInternetConnect(context: Context): Observable<Boolean> {
    return this.flatMap {
        when (context.isConnected) {
            true -> true.just()
            else -> Observable.error(Throwable(ConnectException()))
        }
    }
}

fun Observable<CharSequence>.mapToString(): Observable<String> = this.map { it.toString() }

// Temporarily commented for because of version change conflicts
//fun TextView.customTextChanges(isHasValue: Boolean = false): Observable<String> {
//    return when (isHasValue) {
//        true -> textChanges().flatMap { it.just() }.mapToString()
//        else -> textChanges().skipInitialValue().mapToString()
//    }
//}

fun Observable<String>.notEmpty(): Observable<String> = this.filter { it.isNotEmpty() }

fun <T> Observable<T>.loggingAction(actionName: String = ""): Observable<T> {
    return this
        .doOnNext { Log.debug("success $actionName") }
        .doOnError { Log.debug("error $actionName: ${it.message}") }
}

fun Completable.loggingAction(actionName: String = ""): Completable {
    return this
        .doOnComplete { Log.debug("success $actionName") }
        .doOnError { Log.debug("error $actionName: ${it.message}") }
}

fun <T> Observable<T>.takeFirst(): Observable<T> {
    return this.take(1)
}