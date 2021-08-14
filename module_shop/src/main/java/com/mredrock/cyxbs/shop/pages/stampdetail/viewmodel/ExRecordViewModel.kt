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
import com.mredrock.cyxbs.shop.bean.SingleExRecord
import com.mredrock.cyxbs.shop.config.ShopConfig
import com.mredrock.cyxbs.shop.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExRecordViewModel : BaseViewModel() {
    /**
     * 兑换记录的返回值
     */
    private val exRecordData = MutableLiveData<List<SingleExRecord>>()

    val isSuccess = MutableLiveData<Boolean>()

    fun getExRecordData(position: Int) = Transformations.map(exRecordData) {
            it[position]
    }

    fun exRecordDataSize(): Int{
        return exRecordData.value?.size ?:0
    }

    fun getExRecordData(){
        TestRetrofit.testRetrofit.create(ApiService::class.java)
                .getAllExRecord()
                .enqueue(object :Callback<ExRecordResp>{
                    override fun onFailure(call: Call<ExRecordResp>, t: Throwable) {
                        toastEvent.value = R.string.shop_detail_toast_get_all_get_record_error
                    }

                    override fun onResponse(call: Call<ExRecordResp>, response: Response<ExRecordResp>) {
                        Log.d("TAG","(ExRecordViewModel.kt:44)->${response.body().toString()}")
                        response.body()?.let {
                            if (it.status == ShopConfig.SHOP_STATUS_GET_EXCHANGE_INFO_SUCCESS){
                                // 请求成功
                                exRecordData.value = it.data
                                isSuccess.value = true
                            }else{
                                toastEvent.value = R.string.shop_detail_toast_get_all_ex_record_error
                            }
                        }
                    }
                })
    }

}