package com.kunalapk.smarthttplogginginterceptor.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.kunalapk.smarthttplogginginterceptor.databinding.ItemHttpLogsBinding
import com.kunalapk.smarthttplogginginterceptor.model.RequestModel

class LogsViewHolder(private val binding: ItemHttpLogsBinding) : RecyclerView.ViewHolder(binding.root) {

    internal fun bind(requestModel: RequestModel) {
        binding.tvRequestMethod.text = requestModel.method
        binding.tvRequestUrl.text = requestModel.url
        binding.tvRequestCode.text = requestModel.responseModel?.responseCode.toString()
    }
}