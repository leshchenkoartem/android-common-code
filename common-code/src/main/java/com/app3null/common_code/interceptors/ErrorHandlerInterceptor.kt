package com.app3null.common_code.interceptors

import com.app3null.common_code.exceptions.ServerSideException
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject

const val BAD_REQUEST_CODE = 400

class ErrorHandlerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response = chain.proceed(chain.request())
        val responseBody = response.body
        if (responseBody != null) {
            val rawJson = responseBody.string()
            if (response.code == BAD_REQUEST_CODE) {
                val jsonObject = JSONObject(rawJson)
                val responseCode = jsonObject.getInt("code")
                val message = jsonObject.getString("message")
                throw ServerSideException(responseCode, message)
            }
            response = response.newBuilder().body(rawJson.toResponseBody(responseBody.contentType())).build()
        }
        return response
    }
}