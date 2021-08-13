package com.mredrock.cyxbs.shop.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ExRecordResp(
        @SerializedName("data")
        val data: List<SingleExRecord>,
        @SerializedName("info")
        val info: String,
        @SerializedName("status")
        val status: Int
) : Serializable
data class SingleExRecord(
        @SerializedName("ware_name")
        val wareName: String,
        @SerializedName("cos_stamp")
        val costStamp: Int,
        @SerializedName("data")
        val date: String,
        @SerializedName("moment")
        val moment: String,
        @SerializedName("is_collected")
        val isCollected: Boolean,
        @SerializedName("order_id")
        val orderId: String
) : Serializable