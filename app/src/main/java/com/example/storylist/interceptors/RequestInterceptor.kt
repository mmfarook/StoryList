package com.example.storylist.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RequestInterceptor : Interceptor {
    private val TAG: String = "RequestInterceptor";
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        Log.e(TAG, "url:" + original.url() );
        // Request customization: add request headers
        return chain.proceed(original)
    }
}