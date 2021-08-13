package com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.common.network.ApiGenerator
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel
import com.mredrock.cyxbs.shop.network.ApiService

class ShopViewModel : BaseViewModel() {
    /**
     * 所有装饰商品数据
     */
    private val _allDecorationData = MutableLiveData<AllDecorationResp>()
    val allDecorationData : LiveData<AllDecorationResp>
        get() = _allDecorationData

    /**
     * 所有邮货商品数据
     */
    private val _allStampGoodData = MutableLiveData<AllStampGoodResp>()
    val allStampGoodData : LiveData<AllStampGoodResp>
        get() = _allStampGoodData

    /**
     * 网络请求，获取所有装饰商品数据
     */
    fun getAllDecoration(){
        ApiGenerator.getCommonApiService(ApiService::class.java)
                .getAllDecoration()
                .mapOrThrowApiException()
                .setSchedulers()
                .doOnSubscribe {
                }
                .doOnError {
                    toastEvent.value = R.string.shop_good_toast_get_all_decoration_info_error
                }
                .safeSubscribeBy {
                    _allDecorationData.postValue(it)
                }
    }

    /**
     * 网络请求，获取所有邮货商品数据
     */
    fun getAllStampGood(){
        ApiGenerator.getCommonApiService(ApiService::class.java)
                .getAllStampGood()
                .mapOrThrowApiException()
                .setSchedulers()
                .doOnSubscribe {
                }
                .doOnError {
                    toastEvent.value = R.string.shop_good_toast_get_all_stamp_good_info_error
                }
                .safeSubscribeBy {
                    _allStampGoodData.postValue(it)
                }
    }

    /**
     * 初始化数据
     */
    fun initData(){
//        getAllDecoration()
//        getAllStampGood()
        getFakeData()
    }

    /**
     * 假数据
     */
    fun getFakeData(){
        val fakeDecoration = Decoration("title",15,"描述",233,666, MutableList(2){""})
        val fakeStampGood = StampGood("title",MutableList(2){""},15,233,"描述")

        val decorationData = MutableList(20){fakeDecoration}
        val stampGoodData = MutableList(20){fakeStampGood}

        _allDecorationData.value = AllDecorationResp(true,decorationData)
        _allStampGoodData.value = AllStampGoodResp(true,stampGoodData)
    }

    /**
     * 获取装饰商品数量
     */
    fun getDecorationCount(): Int = allDecorationData.value?.allDecoration?.size ?:0

}