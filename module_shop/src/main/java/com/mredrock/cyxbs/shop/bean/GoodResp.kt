package com.mredrock.cyxbs.shop.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GoodResp(
        @SerializedName("data")
        val data: GoodInfo,
        @SerializedName("data")
        val info: String,
        @SerializedName("data")
        val status: Int
)  : Serializable

data class GoodInfo(
        @SerializedName("amount")
        val amount: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("life")
        val life: Int,
        @SerializedName("price")
        val price: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("type")
        val type: Int,
        @SerializedName("urls")
        val urls: List<Url>
) : Serializable

data class Url(
        @SerializedName("url")
        val url: String
)