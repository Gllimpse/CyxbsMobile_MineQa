package com.mredrock.cyxbs.shop.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ExRecordRes(
        @SerializedName("data")
        val data: List<SingleExRecord>,
        @SerializedName("info")
        val info: String,
        @SerializedName("status")
        val status: Int
) : Serializable
data class SingleExRecord(
        @SerializedName("cos_stamp")
        val cos_stamp: Int,
        @SerializedName("data")
        val date: String,
        @SerializedName("is_collected")
        val is_collected: Boolean,
        @SerializedName("moment")
        val moment: String,
        @SerializedName("order_id")
        val order_id: String,
        @SerializedName("ware_name")
        val ware_name: String
) : Serializable