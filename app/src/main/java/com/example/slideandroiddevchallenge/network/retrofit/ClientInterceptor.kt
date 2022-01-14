package com.example.slideandroiddevchallenge.network.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class ClientInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val request = request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", "Bearer 532bc9cf28a348dcd4c467909511ad338fd7d7700271c94b14302497a01581cd")
        proceed(request.build())
    }
}