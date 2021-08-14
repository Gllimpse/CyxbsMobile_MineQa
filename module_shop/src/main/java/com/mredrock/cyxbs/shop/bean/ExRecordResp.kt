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
        @SerializedName("goods_name")
        val goodsName: String,
        @SerializedName("goods_price")
        val goodsPrice: Int,
        @SerializedName("data")
        val date: Long,
        @SerializedName("is_received")
        val isReceived: Boolean,
        @SerializedName("order_id")
        val orderId: String
) : Serializable