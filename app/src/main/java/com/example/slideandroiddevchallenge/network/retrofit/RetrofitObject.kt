package com.example.slideandroiddevchallenge.network.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitObject private constructor() {

    companion object {
        private val clientInterceptor = OkHttpClient().newBuilder()
            .addInterceptor(ClientInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .callTimeout(4, TimeUnit.MINUTES)
            .writeTimeout(4, TimeUnit.MINUTES)
            .readTimeout(4, TimeUnit.MINUTES)
            .build()

        private const val BASE_URL: String = URLS.BASE_URL
        private var ourInstance: Retrofit? = null

        @Synchronized
        fun getInstance(): Retrofit? {

            if (ourInstance == null) {
                ourInstance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(clientInterceptor)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return ourInstance
        }
    }
}