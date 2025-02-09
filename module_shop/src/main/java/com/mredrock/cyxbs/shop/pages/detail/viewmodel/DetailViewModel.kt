package com.mredrock.cyxbs.shop.pages.detail.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.common.network.ApiGenerator
import com.mredrock.cyxbs.common.utils.extensions.mapOrThrowApiException
import com.mredrock.cyxbs.common.utils.extensions.safeSubscribeBy
import com.mredrock.cyxbs.common.utils.extensions.setOnSingleClickListener
import com.mredrock.cyxbs.common.utils.extensions.setSchedulers
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel
import com.mredrock.cyxbs.common.viewmodel.event.SingleLiveEvent
import com.mredrock.cyxbs.mine.TestRetrofit
import com.mredrock.cyxbs.shop.bean.CenterResp
import com.mredrock.cyxbs.shop.bean.ExGoodResp
import com.mredrock.cyxbs.shop.bean.GoodInfo
import com.mredrock.cyxbs.shop.bean.GoodResp
import com.mredrock.cyxbs.shop.config.ShopConfig
import com.mredrock.cyxbs.shop.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : BaseViewModel() {

    /**
     * Dialog响应网络请求事件
     */
    val dialogEvent: MutableLiveData<Int> by lazy { SingleLiveEvent<Int>() }

    /**
     * 商品信息
     */
    val goodInfo = MutableLiveData<GoodInfo>()

    /**
     * 我的邮票数量
     */
    private val _myStamps = MutableLiveData<Int>()
    val myStamps get() = _myStamps

    /**
     * 兑换商品请求结果
     */
    private val _exGoodResp = MutableLiveData<ExGoodResp>()
    val exGoodResp get() = _exGoodResp

    /**
     * 发送获取商品信息网络请求
     */
    fun getGoodInfo(id: Int) {
        TestRetrofit.testRetrofit.create(ApiService::class.java)
                .getSingleGoodData(id)
                .enqueue(object : Callback<GoodResp> {
                    override fun onFailure(call: Call<GoodResp>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<GoodResp>, response: Response<GoodResp>) {
                        response.body()?.let {
                            if (it.info == "success" && it.status == 10000) {
                                goodInfo.value = it.data
                            } else {
                                toastEvent.value = R.string.shop_detail_toast_get_good_info_error
                            }
                        }
                    }
                })
    }

    /**
     * 兑换商品 网络请求判断并将请求结果回馈到Activity
     */
    fun exchangeGood(id: Int) {
        if (goodInfo.value?.price ?: 0 > _myStamps.value ?: 0) {
            // 本地价格判断
            dialogEvent.value = ShopConfig.SHOP_DETAIL_DIALOG_STAMP_SHORTAGE
        } else {
            // 网络请求
                TestRetrofit.testRetrofit.create(ApiService::class.java)
                        .exGood(id)
                        .enqueue(object :Callback<ExGoodResp>{
                            override fun onResponse(call: Call<ExGoodResp>, response: Response<ExGoodResp>) {
                                _exGoodResp.value = response.body()
                                // 判断请求结果
                                if (_exGoodResp.value?.status == 10000) {
                                    // 兑换失败
                                    dialogEvent.value = ShopConfig.SHOP_DETAIL_DIALOG_EXCHANGE_SUCCESS
                                } else {
                                    // 兑换成功
                                    dialogEvent.value = ShopConfig.SHOP_DETAIL_DIALOG_EXCHANGE_FAIL
                                }
                            }

                            override fun onFailure(call: Call<ExGoodResp>, t: Throwable) {
                                dialogEvent.value = ShopConfig.SHOP_DETAIL_DIALOG_EXCHANGE_REQUEST_FAIL
                            }

                        })
        }
    }
}








