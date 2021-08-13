package com.mredrock.cyxbs.shop.network

import com.mredrock.cyxbs.common.bean.RedrockApiStatus
import com.mredrock.cyxbs.common.bean.RedrockApiWrapper
import com.mredrock.cyxbs.shop.bean.*
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    /**
     * 兑换商品
     */
    @POST("")
    fun exGood(@Field("id") goodId: String): Observable<RedrockApiWrapper<ExRecordRes>>

    /**
     * 获取单个商品详情
     */
    @GET
    fun getSingleGoodData(@Query("id") goodId: String): Observable<RedrockApiWrapper<GoodResp>>

    /**
     * 获取所有兑换记录
     */
    @GET
    fun getAllExRecord(): Observable<RedrockApiWrapper<ExRecordRes>>

    /**
     * 获取所有兑换记录
     */
    @GET
    fun getAllGetRecord(): Observable<RedrockApiWrapper<GetRecordRes>>


}