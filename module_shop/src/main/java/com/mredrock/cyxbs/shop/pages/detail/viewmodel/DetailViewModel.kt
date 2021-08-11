package com.mredrock.cyxbs.shop.pages.detail.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.api.account.IAccountService
import com.mredrock.cyxbs.common.network.ApiGenerator
import com.mredrock.cyxbs.common.service.ServiceManager
import com.mredrock.cyxbs.common.utils.extensions.mapOrThrowApiException
import com.mredrock.cyxbs.common.utils.extensions.safeSubscribeBy
import com.mredrock.cyxbs.common.utils.extensions.setOnSingleClickListener
import com.mredrock.cyxbs.common.utils.extensions.setSchedulers
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel
import com.mredrock.cyxbs.common.viewmodel.event.SingleLiveEvent
import com.mredrock.cyxbs.shop.bean.*
import com.mredrock.cyxbs.shop.config.ShopConfig
import com.mredrock.cyxbs.shop.network.ApiService

class DetailViewModel : BaseViewModel() {
    /**
     * 商品类型，默认为装饰
     */
    var type = ShopConfig.SHOP_GOOD_TYPE_DECORATION

    /**
     * Dialog响应网络请求事件
     */
    val dialogEvent: MutableLiveData<Int> by lazy { SingleLiveEvent<Int>() }

    /**
     * 兑换商品请求的返回值
     */
    private val exResp = MutableLiveData<ExResp>()

    /**
     * 装饰数据LiveData
     */
    private val decorResp = MutableLiveData<DecorationResp>()

    /**
     * 邮货数据LiveData
     */
    private val stampGoodResp = MutableLiveData<StampGoodResp>()

    /**
     * 获取邮货/装饰名称
     */
    fun getName(type: Int): LiveData<String> = if (type == ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD)
        Transformations.map(stampGoodResp) { it.stamp_good.title }
    else Transformations.map(decorResp) { it.decoration.title }

    /**
     * 获取邮货/装饰的价格
     */
    fun getPrice(type: Int): LiveData<Int> = if (type == ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD)
        Transformations.map(stampGoodResp) { it.stamp_good.price }
    else Transformations.map(decorResp) { it.decoration.price }

    /**
     * 获取邮货/装饰的描述信息
     */
    fun getDescription(type: Int): LiveData<String> =
        if (type == ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD)
            Transformations.map(stampGoodResp) { it.stamp_good.desc }
        else Transformations.map(decorResp) { it.decoration.desc }

    fun getLeftCounts(type: Int): LiveData<Int> = if (type == ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD)
        Transformations.map(stampGoodResp) { it.stamp_good.left_count }
    else Transformations.map(decorResp) { it.decoration.left_Count }

    /**
     * 获取装饰的有效期
     */
    fun getPeriod(): LiveData<Int> = Transformations.map(decorResp) { it.decoration.period }

    /**
     * 获取用户邮票数量
     */
    fun getUserStamps() = ServiceManager.getService(IAccountService::class.java)
        .getUserService().getIntegral()

    /**
     * 兑换按钮点击事件
     */
    fun onExchangeClick(v: View, type: Int) {
        // 单击判断
        v.setOnSingleClickListener {
            // 第一次点击，弹出是否确认兑换对话框
            dialogEvent.value = if (type == ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD)
                ShopConfig.SHOP_DIALOG_TYPE_FIRST_SURE_STAMP else ShopConfig.SHOP_DIALOG_TYPE_FIRST_SURE_DECORATION
        }
    }

    /**
     * 兑换商品 网络请求判断并将请求结果回馈到Activity
     */
    fun exchangeGood(type: Int) {
        if (getPrice(type).value ?: 0 > getUserStamps()) {
            // 本地判断
            dialogEvent.value = ShopConfig.SHOP_DIALOG_TYPE_STAMP_SHORTAGE
        } else {
            // 网络请求
            if (type == ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD) { // 邮货数据
                // 邮货信息不为空
                stampGoodResp.value?.stamp_good?.let {
                    exGoodRequest(it.title)
                }
            } else if (type == ShopConfig.SHOP_GOOD_TYPE_DECORATION) { // 装饰数据
                // 装饰信息不为空
                decorResp.value?.decoration?.let {
                    exGoodRequest(it.title)
                }
            }
            // 判断请求结果
            if (exResp.value?.status == true) {
                // 兑换成功
                dialogEvent.value = ShopConfig.SHOP_DIALOG_TYPE_SUCCESS
            } else if (exResp.value?.status == false && exResp.value?.data == "物品存货不够") {
                // 兑换失败且是因为货存不足
                dialogEvent.value = ShopConfig.SHOP_DIALOG_TYPE_COUNT_SHORTAGE
            }
        }
    }

    /**
     * 兑换商品请求
     */
    private fun exGoodRequest(title: String) {
        ApiGenerator.getApiService(ApiService::class.java)
            .exGood(title)
            .mapOrThrowApiException()
            .setSchedulers()
            .doOnSubscribe {
            }
            .doOnError {
                dialogEvent.value = ShopConfig.SHOP_DIALOG_TYPE_FAIL
            }
            .safeSubscribeBy {
                exResp.postValue(it)
            }
    }

    /**
     * 初始化商品信息
     */
    fun initGoodData(title: String) {
        if (type == ShopConfig.SHOP_GOOD_TYPE_DECORATION) {
//            getDecorationData(title)
            getFakeDecorData()
        } else {
//            getStampGoodData(title)
            getFakeStampGoodData()
        }
    }

    private fun getFakeDecorData() {
        val fakePicUrls = listOf(
            "https://www.hualigs.cn/image/610e4e74f3ca0.jpg",
            "https://www.hualigs.cn/image/610e4e7417790.jpg",
            "https://www.hualigs.cn/image/610e4e7594213.jpg"
        )
        decorResp.value = DecorationResp(
            Decoration(
                "掌邮PM名片", 999,
                "带上这个名片，你就是这条街最亮的仔带上这个名片，你就是这条街最亮的仔",
                15, 121, fakePicUrls
            ), true
        )
    }

    private fun getFakeStampGoodData() {
        val fakePicUrls = listOf(
            "https://www.hualigs.cn/image/610e4e74f3ca0.jpg",
            "https://www.hualigs.cn/image/610e4e7417790.jpg",
            "https://www.hualigs.cn/image/610e4e7594213.jpg"
        )
        stampGoodResp.value = StampGoodResp(
            StampGood(
                "规格：10000mAh\n放电：5V 2.1A", fakePicUrls, 999, 121,
                "小米充电宝"
            ), true
        )
    }

    /**
     * 获取装饰品信息
     */
    private fun getDecorationData(title: String) {
        ApiGenerator.getCommonApiService(ApiService::class.java)
            .getDecorationData(title)
            .mapOrThrowApiException()
            .setSchedulers()
            .doOnSubscribe {
            }
            .doOnError {
                toastEvent.value = R.string.shop_detail_toast_get_decor_info_error
            }
            .safeSubscribeBy {
                decorResp.postValue(it)
            }
    }

    /**
     * 获取邮货信息
     */
    private fun getStampGoodData(title: String) {
        ApiGenerator.getCommonApiService(ApiService::class.java)
            .getStampGoodData(title)
            .mapOrThrowApiException()
            .setSchedulers()
            .doOnSubscribe {
            }
            .doOnError {
                toastEvent.value = R.string.shop_detail_toast_get_stamp_good_info_error
            }
            .safeSubscribeBy {
                stampGoodResp.postValue(it)
            }
    }

    /**
     * 获取详情图片信息
     */
    fun getPicUrls() = if (type == ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD)
        stampGoodResp.value?.stamp_good?.image_urls
    else decorResp.value?.decoration?.image_urls

}