package com.kunalapk.smarthttplogginginterceptor.screens

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.kunalapk.smarthttplogginginterceptor.R
import com.kunalapk.smarthttplogginginterceptor.services.HttpLogsBackgroundService
import com.kunalapk.smarthttplogginginterceptor.window.LoggerFloatingWindow

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this@SettingsActivity)) {
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + packageName)))
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(HttpLogsBackgroundService.getIntent(this))
            } else {
                startService(HttpLogsBackgroundService.getIntent(this))
            }
        }
    }
}