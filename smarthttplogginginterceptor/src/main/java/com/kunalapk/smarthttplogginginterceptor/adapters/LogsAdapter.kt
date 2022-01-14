package com.kunalapk.smarthttplogginginterceptor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kunalapk.smarthttplogginginterceptor.databinding.ItemHttpLogsBinding
import com.kunalapk.smarthttplogginginterceptor.listeners.DiffUtilCallBack
import com.kunalapk.smarthttplogginginterceptor.model.RequestModel
import com.kunalapk.smarthttplogginginterceptor.viewholders.LogsViewHolder

class LogsAdapter(): RecyclerView.Adapter<LogsViewHolder>() {

    private var requestModelList = mutableListOf<RequestModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsViewHolder {
        return LogsViewHolder(ItemHttpLogsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: LogsViewHolder, position: Int) {
        holder.bind(requestModelList[position])
    }

    override fun getItemCount(): Int = requestModelList.size

    internal fun getItemList():MutableList<RequestModel>{
        return requestModelList
    }

    internal fun addItemsWithDiffUtils(newData: MutableList<RequestModel>){
        DiffUtil.calculateDiff(DiffUtilCallBack(newData,requestModelList)).dispatchUpdatesTo(this)
        requestModelList.clear()
        requestModelList.addAll(newData)
    }
}