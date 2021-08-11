package com.mredrock.cyxbs.shop.bean

import com.google.gson.annotations.SerializedName

/**
 * 获取记录
 */
data class ExRecordResp(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: List<ExRecord>
)

data class ExRecord(
    @SerializedName("ware_name")
    val name: String,
    @SerializedName("cost_stamp")
    val costStamp: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("moment")
    val moment: String,
    @SerializedName("isCollected")
    val isCollected: Boolean,
    @SerializedName("order_id")
    val orderId: String
)