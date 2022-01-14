package com.kunalapk.smarthttplogginginterceptor.sample.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kunalapk.smarthttplogginginterceptor.sample.R
import com.kunalapk.smarthttplogginginterceptor.sample.network.BaseCloudAPIService
import com.kunalapk.smarthttplogginginterceptor.sample.network.api.ApiService
import com.kunalapk.smarthttplogginginterceptor.screens.SettingsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        startActivity(Intent(this,SettingsActivity::class.java))

        CoroutineScope(Dispatchers.IO).launch {
            while(true){
                delay(2000)
                BaseCloudAPIService.getApiService(ApiService::class.java).getEnteries()
            }
        }
    }

}