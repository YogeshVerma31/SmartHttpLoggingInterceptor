package com.kunalapk.smarthttplogginginterceptor.window

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kunalapk.smarthttplogginginterceptor.adapters.LogsAdapter
import com.kunalapk.smarthttplogginginterceptor.databinding.LayoutLoggerFloatingWindowBinding
import com.kunalapk.smarthttplogginginterceptor.helper.LogHelper
import com.kunalapk.smarthttplogginginterceptor.model.RequestModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@SuppressLint("ClickableViewAccessibility")
class LoggerFloatingWindow(private val context: Context) {

    private val viewBinding by lazy { LayoutLoggerFloatingWindowBinding.inflate(LayoutInflater.from(context),null,false) }
    private var mParams: WindowManager.LayoutParams? = null
    private var mWindowManager: WindowManager? = null
    private var logsAdapter:LogsAdapter? = null



    fun open() {
        try {
            if (viewBinding.root.windowToken == null) {
                if (viewBinding.root.parent == null) {
                    mWindowManager?.addView(viewBinding.root, mParams)
                }
            }else{

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getItemList():MutableList<RequestModel>?{
        logsAdapter?.let {
             return it.getItemList()
        }
        return null
    }

    fun addRequestModelList(requestModelList: MutableList<RequestModel>){
        //LogHelper.debug("TAG","ListSize - ${requestModelList}")
        logsAdapter?.addItemsWithDiffUtils(requestModelList)
        logsAdapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                viewBinding.rvRequest.smoothScrollToPosition(0)
            }
        })
    }

    init {
        logsAdapter = LogsAdapter()
        viewBinding.rvRequest.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = logsAdapter
        }

        mParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,  // Display it on top of other application windows
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            }, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            PixelFormat.TRANSLUCENT
        )

        mParams?.gravity = Gravity.TOP
        var initialX = 0
        var initialY = 0
        var  initialTouchX = 0F
        var  initialTouchY = 0F
        var totalDisplacement = 0F

        mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        viewBinding.root.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = mParams!!.x
                    initialY = mParams!!.y

                    initialTouchX = motionEvent.rawX
                    initialTouchY = motionEvent.rawY

                    totalDisplacement = view.x - motionEvent.rawX
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_UP -> {

                    return@setOnTouchListener true
                }

                MotionEvent.ACTION_MOVE -> {
                    mParams?.x = initialX + ((motionEvent.rawX - initialTouchX)).toInt()
                    mParams?.y = initialY + ((motionEvent.rawY - initialTouchY)).toInt()

                    mWindowManager?.updateViewLayout(viewBinding?.root, mParams)

                    return@setOnTouchListener true
                }
                else -> {
                    return@setOnTouchListener false
                }
            }
        }
    }


}