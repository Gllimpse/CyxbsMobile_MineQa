package com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.common.network.ApiGenerator
import com.mredrock.cyxbs.common.utils.extensions.mapOrThrowApiException
import com.mredrock.cyxbs.common.utils.extensions.safeSubscribeBy
import com.mredrock.cyxbs.common.utils.extensions.setSchedulers
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel
import com.mredrock.cyxbs.mine.TestRetrofit
import com.mredrock.cyxbs.shop.bean.CenterResp
import com.mredrock.cyxbs.shop.bean.CenterShop
import com.mredrock.cyxbs.shop.bean.CenterTask
import com.mredrock.cyxbs.shop.config.ShopConfig
import com.mredrock.cyxbs.shop.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShopViewModel : BaseViewModel() {
    val isSuccess = MutableLiveData<Boolean>()
    /**
     * 所有装饰商品数据
     */
    private val decorationData = MutableLiveData<List<CenterShop>>()

    /**
     * 所有邮货商品数据
     */
    private val stampGoodData = MutableLiveData<List<CenterShop>>()

    /**
     * 所有每日任务数据
     */
    private val todayTaskData = MutableLiveData<List<CenterTask>>()

    /**
     * 所有更多任务数据
     */
    private val moreTaskData = MutableLiveData<List<CenterTask>>()

    /**
     * 用户邮票数量
     */
    val stampCount =MutableLiveData<Int>()
//    val stampCount : LiveData<Int>
//    get() = _stampCount

    /**
     * 是否有未领取商品
     */
    private val isUnGet = MutableLiveData<Boolean>()

    /**
     * 网络请求，获取邮票中心数据
     */
    fun getCenterData(){
        TestRetrofit.testRetrofit.create(ApiService::class.java)
                .getCenterData()
                .enqueue(object :Callback<CenterResp>{
                    override fun onResponse(call: Call<CenterResp>, response: Response<CenterResp>) {
//                        Log.d("TAG","(ShopViewModel.kt:61)->${response.body().toString()}")
                        val decorArray = ArrayList<CenterShop>()
                        val stampArray = ArrayList<CenterShop>()
                        val todayArray = ArrayList<CenterTask>()
                        val moreArray = ArrayList<CenterTask>()
                        response.body()?.let {
                            if (it.status == 10000) {
                                it.data.apply {
                                    repeat(shop.size) { index ->
                                        Log.d("TAG","(ShopViewModel.kt:73)->======${shop[index].title}")
                                        when (shop[index].type) {
                                            ShopConfig.SHOP_GOOD_TYPE_DECORATION -> decorArray.add(shop[index])
                                            ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD -> stampArray.add(shop[index])
                                        }
                                    }
                                    decorationData.value = decorArray
                                    stampGoodData.value = stampArray

                                    repeat(task.size) { index ->
                                        when (task[index].type) {
                                            "base" -> todayArray.add(task[index])
                                            "more" -> moreArray.add(task[index])
                                        }
                                    }
                                    todayTaskData.value = todayArray
                                    moreTaskData.value = moreArray

                                    stampCount.value = userAmount
                                    isUnGet.value = unGotGood
                                    isSuccess.value = true
                                }
                            } else {
                                toastEvent.value = R.string.shop_good_toast_get_all_decoration_info_error
                            }
                        }
                    }
                    override fun onFailure(call: Call<CenterResp>, t: Throwable) {
                    }
                })
    }

    /**
     * 根据type获取商品数据
     */
    fun getGoodData(type: Int,position: Int) : CenterShop {
        return if (type == ShopConfig.SHOP_GOOD_TYPE_DECORATION){
            decorationData.value?.get(position) ?: CenterShop(0,"","",0,0,ShopConfig.SHOP_GOOD_TYPE_DECORATION)
        }
        else {
            stampGoodData.value?.get(position) ?: CenterShop(0,"","",0,0,ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD)
        }
    }

    /**
     * 获取未领取商品提示语
     */
    fun getBannerData() : String = if (isUnGet.value == true) "你还有待领取的商品，请尽快领取"
            else ""

    /**
     * 根据type获取任务数据
     */
    fun getTaskData(type: String,position: Int) : CenterTask {
        return if (type == ShopConfig.SHOP_TASK_TYPE_TODAY) {
            todayTaskData.value?.get(position) ?: CenterTask("",0,0,"",ShopConfig.SHOP_TASK_TYPE_TODAY,0)
        } else {
            moreTaskData.value?.get(position) ?: CenterTask("",0,0,"",ShopConfig.SHOP_TASK_TYPE_MORE,0)
        }
    }

    /**
     * 初始化数据
     */
    fun initData(){
        getCenterData()
    }


    /**
     * 获取装饰商品数量
     */
    fun getDecorationCount(): Int = decorationData.value?.size ?:0

    /**
     * 获取邮货商品数量
     */
    fun getStampGoodCount(): Int = stampGoodData.value?.size ?:0

    /**
     * 获取每日任务数量
     */
    fun getTodayTaskCount(): Int = todayTaskData.value?.size ?:0

    /**
     * 获取更多任务数量
     */
    fun getMoreTaskCount(): Int = moreTaskData.value?.size ?:0


}