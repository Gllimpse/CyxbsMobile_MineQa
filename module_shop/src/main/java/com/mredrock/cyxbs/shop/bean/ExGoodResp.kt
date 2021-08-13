package com.mredrock.cyxbs.shop.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author Qt
 * @date 2021/8/13
 * 兑换商品请求结果
 **/
data class ExGoodResp(
    @SerializedName("status")
    val status: Int, // 请求结果状态 400为失败
    @SerializedName("info")
    val info: String // 请求结果描述
) : Serializable