package com.mredrock.cyxbs.shop.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetRecordResp(
        @SerializedName("data")
        val data: List<SingleGetRecord>,
        @SerializedName("info")
        val info: String,
        @SerializedName("status")
        val status: Int
) : Serializable

data class SingleGetRecord(
        @SerializedName("red_id")
        val redId: String,
        @SerializedName("gain_stamp")
        val gainStamp: Int,
        @SerializedName("task_name")
        val taskName: String,
        @SerializedName("date")
        val date: String
) : Serializable