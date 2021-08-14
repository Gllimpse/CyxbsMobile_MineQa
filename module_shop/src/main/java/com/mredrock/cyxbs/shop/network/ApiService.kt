package com.mredrock.cyxbs.shop.network

import com.mredrock.cyxbs.common.bean.RedrockApiWrapper
import com.mredrock.cyxbs.shop.bean.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    /**
     * 兑换商品
     */
    @FormUrlEncoded
    @POST("magipoke-intergral/Integral/purchase")
    fun exGood(@Field("id") goodId: Int): Call<ExGoodResp>

    /**
     * 获取单个商品详情
     */
    @GET("magipoke-intergral/Integral/getItemInfo")
    fun getSingleGoodData(@Query("id") goodId: Int): Call<GoodResp>

    /**
     * 获取所有兑换记录
     */
    @GET("magipoke-intergral/User/exchange")
    fun getAllExRecord(): Call<ExRecordResp>

    /**
     * 获取所有获取记录
     */
    @GET("magipoke-intergral/User/getRecord")
    fun getAllGetRecord(@Query("page") page: Int,
                        @Query("size") size: Int): Call<GetRecordResp>

    /**
     * 查询主页信息
     */
    @GET("magipoke-intergral/User/info")
    fun getCenterData(): Call<CenterResp>

}