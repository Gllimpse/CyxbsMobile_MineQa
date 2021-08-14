package com.mredrock.cyxbs.shop.pages.stampdetail.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.common.network.ApiGenerator
import com.mredrock.cyxbs.common.utils.extensions.mapOrThrowApiException
import com.mredrock.cyxbs.common.utils.extensions.safeSubscribeBy
import com.mredrock.cyxbs.common.utils.extensions.setSchedulers
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel
import com.mredrock.cyxbs.mine.TestRetrofit
import com.mredrock.cyxbs.shop.bean.ExRecordResp
import com.mredrock.cyxbs.shop.bean.GetRecordResp
import com.mredrock.cyxbs.shop.bean.GoodResp
import com.mredrock.cyxbs.shop.bean.SingleGetRecord
import com.mredrock.cyxbs.shop.config.ShopConfig
import com.mredrock.cyxbs.shop.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetRecordViewModel : BaseViewModel() {
    /**
     * 获取记录的返回值
     */
    private val getRecordData = MutableLiveData<List<SingleGetRecord>>()

    val isSuccess = MutableLiveData<Boolean>()

    fun getGetRecordData(position: Int) = Transformations.map(getRecordData) {
        it[position]
    }

    fun getRecordDataSize(): Int {
        return getRecordData.value?.size ?: 0
    }

    fun getGetRecordData(page: Int,size: Int) {
        TestRetrofit.testRetrofit.create(ApiService::class.java)
                .getAllGetRecord(page, size)
                .enqueue(object : Callback<GetRecordResp> {
                    override fun onFailure(call: Call<GetRecordResp>, t: Throwable) {
                        toastEvent.value = R.string.shop_detail_toast_get_all_get_record_error
                    }

                    override fun onResponse(call: Call<GetRecordResp>, response: Response<GetRecordResp>) {
                        response.body()?.let {
                            if (it.status == ShopConfig.SHOP_STATUS_GET_GET_INFO_SUCCESS) {
                                getRecordData.value = it.data
                                isSuccess.value = true
                                Log.d("TAG","(GetRecordViewModel.kt:48)->123321132321#${getRecordDataSize()}")
                            } else {
                                toastEvent.value = R.string.shop_detail_toast_get_all_get_record_error
                            }
                        }
                    }
                })
    }
}