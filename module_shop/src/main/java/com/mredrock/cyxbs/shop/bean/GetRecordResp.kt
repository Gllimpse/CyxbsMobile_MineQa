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
        @SerializedName("date")
        val date: Long,
        @SerializedName("task_income")
        val taskIncome: Int,
        @SerializedName("task_name")
        val taskName: String
) : Serializable