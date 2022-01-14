package com.kunalapk.smarthttplogginginterceptor.interceptor

import com.kunalapk.smarthttplogginginterceptor.helper.LogHelper
import com.kunalapk.smarthttplogginginterceptor.model.RequestModel
import com.kunalapk.smarthttplogginginterceptor.model.ResponseModel
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.Response
import org.greenrobot.eventbus.EventBus

class SmartHttpLoggingInterceptor:Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        chain.call().request().let { request ->
            RequestModel.requestModelConvertor(chain.call(),request).let { requestModel ->
                postHttpLogEvent(requestModel)
                chain.proceed(request).let { response ->
                    postHttpLogEvent(RequestModel.requestModelConvertor(requestModel,response))
                    return response
                }
            }
        }
    }

    private fun postHttpLogEvent(requestModel: RequestModel){
        EventBus.getDefault().post(requestModel)
    }


}