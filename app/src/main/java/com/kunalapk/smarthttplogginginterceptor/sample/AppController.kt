package com.kunalapk.smarthttplogginginterceptor.sample

import android.app.Application
import android.content.res.Configuration
import com.kunalapk.smarthttplogginginterceptor.sample.network.BaseCloudAPIService
import retrofit2.Retrofit

class AppController:Application() {

    companion object{
        val TAG:String = AppController::class.java.simpleName
        var appController:AppController? = null
        var cloudApiService:Retrofit? = null
    }

    override fun onCreate() {
        super.onCreate()

        appController = this


        cloudApiService = BaseCloudAPIService()

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    override fun onTerminate() {
        super.onTerminate()
    }


}