package com.kunalapk.smarthttplogginginterceptor.listeners

import androidx.recyclerview.widget.DiffUtil
import com.kunalapk.smarthttplogginginterceptor.model.RequestModel
import java.lang.Exception

class DiffUtilCallBack(var newList: MutableList<RequestModel>, var oldList: MutableList<RequestModel>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition].call == oldList[oldItemPosition].call
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return false
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return null
    }
}