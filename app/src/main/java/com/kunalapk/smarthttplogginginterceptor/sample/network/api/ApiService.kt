package com.kunalapk.smarthttplogginginterceptor.sample.network.api

import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("data?drilldowns=Nation&measures=Population")
    suspend fun getEnteries(): Response<String>

}