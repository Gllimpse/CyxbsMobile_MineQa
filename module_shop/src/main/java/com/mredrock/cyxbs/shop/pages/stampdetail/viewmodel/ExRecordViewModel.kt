package com.mredrock.cyxbs.shop.pages.stampdetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.common.network.ApiGenerator
import com.mredrock.cyxbs.common.utils.extensions.mapOrThrowApiException
import com.mredrock.cyxbs.common.utils.extensions.safeSubscribeBy
import com.mredrock.cyxbs.common.utils.extensions.setSchedulers
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel
import com.mredrock.cyxbs.shop.bean.SingleExRecord
import com.mredrock.cyxbs.shop.network.ApiService

class ExRecordViewModel : BaseViewModel() {
    /**
     * 兑换记录的返回值
     */
    private val exRecordData = MutableLiveData<List<SingleExRecord>>()

    fun getExRecordData(position: Int) = Transformations.map(exRecordData) {
            it[position]
    }

    fun exRecordDataSize(): Int{
        return exRecordData.value?.size ?:0
    }

    fun initData(){
        getExRecordData()
    }

    fun getExRecordData(){
        ApiGenerator.getApiService(ApiService::class.java)
                .getAllExRecord()
                .mapOrThrowApiException()
                .setSchedulers()
                .doOnSubscribe {
                }
                .doOnError {
                    toastEvent.value = R.string.shop_detail_toast_get_all_ex_record_error
                }
                .safeSubscribeBy {
                    exRecordData.postValue(it.data)
                }
    }
}