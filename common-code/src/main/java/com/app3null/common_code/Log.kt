package com.app3null.common_code

import android.text.TextUtils

object Log {

    private val TAG = "app3null"

//    private val IS_DEBUG = BuildConfig.DEBUG

    private val location: String
        get() {
            val className = Log::class.java.name
            val traces = Thread.currentThread().stackTrace
            var found = false

            for (i in traces.indices) {
                val trace = traces[i]

                try {
                    if (found) {
                        if (!trace.className.startsWith(className)) {
                            val clazz = Class.forName(trace.className)
                            return "[" + getClassName(clazz) + ":" + trace.methodName + ":" + trace
                                .lineNumber + "]: "
                        }
                    } else if (trace.className.startsWith(className)) {
                        found = true
                        continue
                    }
                } catch (e: ClassNotFoundException) {
                }

            }

            return "[]: "
        }

    fun verbose(msg: String) {
//        if (IS_DEBUG) {
            android.util.Log.v(TAG, location + msg)
//        }
    }

    fun debug(msg: String) {
//        if (IS_DEBUG) {
            android.util.Log.d(TAG, location + msg)
//        }
    }

    fun error(msg: String, throwable: Throwable) {
//        if (IS_DEBUG) {
            android.util.Log.e(TAG, location + msg, throwable)
//        }
    }

    private fun getClassName(clazz: Class<*>?): String {
        return if (clazz != null) {
            if (!TextUtils.isEmpty(clazz.simpleName)) {
                clazz.simpleName
            } else getClassName(clazz.enclosingClass)

        } else ""
    }

    fun test(){
        android.util.Log.e(TAG, "Test")
    }
}