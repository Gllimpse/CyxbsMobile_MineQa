package com.mredrock.cyxbs.shop.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author Qt
 * @date 2021/8/13
 * You are universe.
 **/
data class CenterResp(
    @SerializedName("data")
    val data: CenterData,
    @SerializedName("info")
    val info: String, // 请求信息 success
    @SerializedName("status")
    val status: Int // 请求状态 10000
) : Serializable

data class CenterData(
    @SerializedName("task")
    val task: List<CenterTask>, // 任务列表
    @SerializedName("shop")
    val shop: List<CenterShop>, // 商品列表
    @SerializedName("user_amount")
    val userAmount: Int, // 用户邮票数量
    @SerializedName("un_got_good")
    val unGotGood: Boolean // 是否有未领取商品
) : Serializable

data class CenterTask(
    @SerializedName("title")
    val title: String, // 任务名称
    @SerializedName("max_progress")
    val maxProgress: Int, // 任务最大进度
    @SerializedName("current_progress")
    val curProgress: Int, // 任务当前进度
    @SerializedName("description")
    val desc: String, // 任务详情
    @SerializedName("type")
    val type: String, // 任务类型
    @SerializedName("gain_stamp")
    val gainStamp: Int
) : Serializable

data class CenterShop(
    @SerializedName("id")
    val id: Int, // 商品id
    @SerializedName("shop")
    val title: String, // 商品名称
    @SerializedName("url")
    val imgUrl: String, // 商品图片地址
    @SerializedName("amount")
    val amount: Int, // 商品余额
    @SerializedName("price")
    val price: Int, // 商品价格
    @SerializedName("type")
    val type: Int // 商品类型
) : Serializable