package com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mredrock.cyxbs.common.network.ApiGenerator
import com.mredrock.cyxbs.common.utils.extensions.mapOrThrowApiException
import com.mredrock.cyxbs.common.utils.extensions.safeSubscribeBy
import com.mredrock.cyxbs.common.utils.extensions.setSchedulers
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel
import com.mredrock.cyxbs.shop.bean.AllDecorationResp
import com.mredrock.cyxbs.shop.bean.AllStampGoodResp
import com.mredrock.cyxbs.shop.bean.Decoration
import com.mredrock.cyxbs.shop.network.ApiService

class ShopViewModel : BaseViewModel() {
    private val _allDecorationData = MutableLiveData<AllDecorationResp>()
    val allDecorationData : LiveData<AllDecorationResp>
        get() = _allDecorationData

    private val _allStampGoodData = MutableLiveData<AllStampGoodResp>()
    val allStampGoodData : LiveData<AllStampGoodResp>
        get() = _allStampGoodData

    fun getAllDecoration(){
        ApiGenerator.getCommonApiService(ApiService::class.java)
                .getAllDecoration()
                .mapOrThrowApiException()
                .setSchedulers()
                .doOnSubscribe {
                }
                .doOnError {
                }
                .safeSubscribeBy {
                    _allDecorationData.postValue(it)
                }
    }

    fun getAllStampGood(){
        ApiGenerator.getCommonApiService(ApiService::class.java)
                .getAllStampGood()
                .mapOrThrowApiException()
                .setSchedulers()
                .doOnSubscribe {
                }
                .doOnError {
                }
                .safeSubscribeBy {
                    _allStampGoodData.postValue(it)
                }
    }
}