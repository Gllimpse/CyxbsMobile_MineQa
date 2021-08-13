package com.mredrock.cyxbs.shop.network

import com.mredrock.cyxbs.common.bean.RedrockApiStatus
import com.mredrock.cyxbs.common.bean.RedrockApiWrapper
import com.mredrock.cyxbs.shop.bean.*
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    /**
     * 查询主页信息
     */
    @GET("User/info")
    fun getCenterData(): Observable<RedrockApiWrapper<CenterResp>>

}