package com.kunalapk.smarthttplogginginterceptor.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.kunalapk.smarthttplogginginterceptor.R
import com.kunalapk.smarthttplogginginterceptor.helper.LogHelper
import com.kunalapk.smarthttplogginginterceptor.model.RequestModel
import com.kunalapk.smarthttplogginginterceptor.window.LoggerFloatingWindow
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HttpLogsBackgroundService:Service() {

    private var TAG = this::class.java.simpleName

    private var loggerFloatingWindow: LoggerFloatingWindow? = null

    private var coroutineJob: Job = Job()

    protected val ioScope = CoroutineScope(Dispatchers.IO + coroutineJob)

    companion object{
        fun getIntent(context: Context?): Intent {
            return Intent(context, HttpLogsBackgroundService::class.java)
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground()
        else
            startForeground(1, Notification())

        loggerFloatingWindow = LoggerFloatingWindow(this)
        loggerFloatingWindow?.open()

        EventBus.getDefault().register(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun getHttpLogs(requestModel : RequestModel) {
        ioScope.launch {
            processRequestModelList(requestModel).let {
                withContext(Dispatchers.Main){
                    loggerFloatingWindow?.addRequestModelList(it)
                }
            }
        }
    }

    private suspend fun processRequestModelList(requestModel: RequestModel):MutableList<RequestModel>{
        var found = false
        val requestModelList = loggerFloatingWindow?.getItemList()?.toCollection(mutableListOf())

        requestModelList?.let {
            (0 until requestModelList.size)
                .map { requestModelList[it] }
                .filter { it.call==requestModel.call }
                .forEach {
                    requestModelList.indexOf(it).let { index ->
                        if(index!=-1){
                            found = true
                            LogHelper.debug(TAG,"Ree - > ${index}")
                            requestModelList[index] = requestModel
                            //it.responseModel = requestModel.responseModel
                        }
                    }
                }
            if(!found){
                requestModelList.add(0,requestModel)
            }
            return requestModelList
        }
        return mutableListOf()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startMyOwnForeground() {
        val notificationChannelId = "CallerIdService.Id"
        val channelName = "CallerIdService"
        val chan = NotificationChannel(notificationChannelId, channelName, NotificationManager.IMPORTANCE_MIN)
        val manager = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(chan)
        val notificationBuilder = NotificationCompat.Builder(this, notificationChannelId)
        val notification = notificationBuilder.setOngoing(true)
            .setContentTitle("Caller ID is active")
            .setSmallIcon(R.drawable.ic_baseline_network_check_24)
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(2, notification)
    }


}