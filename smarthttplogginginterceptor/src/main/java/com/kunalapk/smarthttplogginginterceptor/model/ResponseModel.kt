package com.kunalapk.smarthttplogginginterceptor.model

import com.kunalapk.smarthttplogginginterceptor.helper.LogHelper
import okhttp3.Call
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.lang.Exception
import java.nio.charset.Charset

data class ResponseModel(
    var responseCode: Int = 0,
    var responseBody: String? = null
){
    companion object{

        fun responseBodyConvertor(response: Response?):ResponseModel?{
                return if(response!=null){
                    ResponseModel(
                        responseCode = response.code,
                        responseBody = getApiResponse(response)
                    )
                }else{
                    ResponseModel(
                        responseCode = 0,
                        responseBody = null
                    )
                }
        }


        fun getApiResponse(response: Response?):String?{
            response?.body?.source().let {
                it?.request(Long.MAX_VALUE)
                return it?.buffer?.clone()?.readString(Charset.forName("UTF-8"))
            }
        }
    }
}