package com.kunalapk.smarthttplogginginterceptor.sample.network

import android.content.Intent
import com.kunalapk.smarthttplogginginterceptor.helper.LogHelper
import com.kunalapk.smarthttplogginginterceptor.interceptor.SmartHttpLoggingInterceptor
import com.kunalapk.smarthttplogginginterceptor.sample.AppController
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import retrofit2.adapter.rxjava2.Result.response

import okio.BufferedSource
import java.nio.charset.Charset


interface BaseCloudAPIService {
    companion object{
        operator fun invoke(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://datausa.io/api/")
                .client(OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(SmartHttpLoggingInterceptor())
                    .build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }

        fun<T> getApiService(service: Class<T>):T{
            if(AppController.cloudApiService!=null){
                return AppController.cloudApiService!!.create(service)
            }else{
                throw Throwable("CloudApiService cannot be null")
            }
        }

    }
}