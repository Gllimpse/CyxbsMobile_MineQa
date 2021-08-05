package com.mredrock.cyxbs.shop.pages.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mredrock.cyxbs.common.network.ApiGenerator
import com.mredrock.cyxbs.common.utils.extensions.mapOrThrowApiException
import com.mredrock.cyxbs.common.utils.extensions.safeSubscribeBy
import com.mredrock.cyxbs.common.utils.extensions.setSchedulers
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel
import com.mredrock.cyxbs.shop.bean.DecorationResp
import com.mredrock.cyxbs.shop.bean.ExResp
import com.mredrock.cyxbs.shop.bean.StampGoodResp
import com.mredrock.cyxbs.shop.network.ApiService

class DetailViewModel : BaseViewModel(){
    private val _exResp = MutableLiveData<ExResp>()
    val exResp : LiveData<ExResp>
        get() = _exResp


    private val _decorResp = MutableLiveData<DecorationResp>()
    val decorResp : LiveData<DecorationResp>
            get() = _decorResp

    private val _stampGoodResp = MutableLiveData<StampGoodResp>()
    val stampGoodResp : LiveData<StampGoodResp>
        get() = _stampGoodResp

    fun exGood(title: String){

        ApiGenerator.getApiService(ApiService::class.java)
                .exGood(title)
                .mapOrThrowApiException()
                .setSchedulers()
                .doOnSubscribe {
                }
                .doOnError {
                }
                .safeSubscribeBy {
                    _exResp.postValue(it)
                }
    }

    fun getDecorationData(title: String){
        ApiGenerator.getCommonApiService(ApiService::class.java)
                .getDecorationData(title)
                .mapOrThrowApiException()
                .setSchedulers()
                .doOnSubscribe {
                }
                .doOnError {
                }
                .safeSubscribeBy {
                    _decorResp.postValue(it)
                }
    }

    fun getStampGoodData(title: String){
        ApiGenerator.getCommonApiService(ApiService::class.java)
                .getStampGoodData(title)
                .mapOrThrowApiException()
                .setSchedulers()
                .doOnSubscribe {
                }
                .doOnError {
                }
                .safeSubscribeBy {
                    _stampGoodResp.postValue(it)
                }
    }
}