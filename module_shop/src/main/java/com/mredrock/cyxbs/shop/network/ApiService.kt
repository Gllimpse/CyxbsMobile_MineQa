package com.mredrock.cyxbs.shop.network

import com.mredrock.cyxbs.common.bean.RedrockApiWrapper
import com.mredrock.cyxbs.shop.bean.*
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    /**
     * 兑换商品
     */
    @FormUrlEncoded
    @POST("Integral/purchase")
    fun exGood(@Field("id") goodId: String): Observable<RedrockApiWrapper<ExGoodResp>>

    /**
     * 获取单个商品详情
     */
    @GET("Integral/getItemInfo")
    fun getSingleGoodData(@Query("id") goodId: String): Observable<RedrockApiWrapper<GoodResp>>

    /**
     * 获取所有兑换记录
     */
    @GET("User/exchange")
    fun getAllExRecord(): Observable<RedrockApiWrapper<ExRecordResp>>

    /**
     * 获取所有获取记录
     */
    @GET("User/getRecord")
    fun getAllGetRecord(): Observable<RedrockApiWrapper<GetRecordResp>>

    /**
     * 查询主页信息
     */
    @GET("User/info")
    fun getCenterData(): Observable<RedrockApiWrapper<CenterResp>>

}