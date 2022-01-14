package com.kunalapk.smarthttplogginginterceptor.model

import okhttp3.Call
import okhttp3.Request
import okhttp3.Response

data class RequestModel(
    var call: Call,
    var method:String? = null,
    var url:String? = null,
    var requestBody: String? = null,
    var responseModel: ResponseModel? = null,
){
    companion object{

        fun requestModelConvertor(call:Call, request: Request):RequestModel{
            return requestModelConvertor(call,request,null)
        }

        fun requestModelConvertor(call:Call, request: Request, response: Response?):RequestModel{
            return RequestModel(
                call = call,
                method = request.method,
                url = request.url.toString(),
            ).apply {
                requestModelConvertor(this,response)
            }
        }

        fun requestModelConvertor(requestModel: RequestModel,response: Response?):RequestModel{
            return requestModel.apply {
                responseModel = ResponseModel.responseBodyConvertor(response)
            }
        }
    }
}