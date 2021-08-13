package com.mredrock.cyxbs.shop.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetRecordRes(
        @SerializedName("data")
        val data: List<SingleGetRecord>,
        @SerializedName("info")
        val info: String,
        @SerializedName("status")
        val status: Int
) : Serializable

data class SingleGetRecord(
        @SerializedName("gain_stamp")
        val gain_stamp: Int,
        @SerializedName("red_id")
        val red_id: String,
        @SerializedName("task_name")
        val task_name: String
) : Serializable