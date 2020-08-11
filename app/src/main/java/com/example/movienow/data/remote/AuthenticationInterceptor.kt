package com.example.movienow.data.remote

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor used to intercept the actual request and
 * to supply your API Key in REST API calls via a custom header.
 */
class AuthenticationInterceptor : Interceptor {
    private val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url()

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build();

        val newRequest = originalRequest
            .newBuilder().url(url).build()

        return chain.proceed(newRequest)
    }
}