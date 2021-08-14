package com.mredrock.cyxbs.shop.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GoodResp(
        @SerializedName("data")
        val data: GoodInfo,
        @SerializedName("info")
        val info: String,
        @SerializedName("status")
        val status: Int
)  : Serializable

data class GoodInfo(
        @SerializedName("title")
        val title: String,
        @SerializedName("price")
        val price: Int,
        @SerializedName("amount")
        val amount: Int,
        @SerializedName("type")
        val type: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("life")
        val life: Int,
        @SerializedName("urls")
        val urls: ArrayList<String>
) : Serializable