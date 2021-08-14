package com.mredrock.cyxbs.shop.pages.stampdetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.common.network.ApiGenerator
import com.mredrock.cyxbs.common.utils.extensions.mapOrThrowApiException
import com.mredrock.cyxbs.common.utils.extensions.safeSubscribeBy
import com.mredrock.cyxbs.common.utils.extensions.setSchedulers
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel
import com.mredrock.cyxbs.shop.bean.SingleGetRecord
import com.mredrock.cyxbs.shop.config.ShopConfig
import com.mredrock.cyxbs.shop.network.ApiService

class GetRecordViewModel : BaseViewModel() {
    /**
     * 获取记录的返回值
     */
    private val getRecordData = MutableLiveData<List<SingleGetRecord>>()

    fun getGetRecordData(position: Int) = Transformations.map(getRecordData) {
        it[position]
    }

    fun getRecordDataSize(): Int {
        return getRecordData.value?.size ?: 0
    }

    fun getExRecordData() {
        ApiGenerator.getApiService(ApiService::class.java)
            .getAllGetRecord()
            .mapOrThrowApiException()
            .setSchedulers()
            .doOnSubscribe {
            }
            .doOnError {
                toastEvent.value = R.string.shop_detail_toast_get_all_get_record_error
            }
            .safeSubscribeBy {
                if (it.status == ShopConfig.SHOP_STATUS_GET_GET_INFO_SUCCESS) {
                    getRecordData.value = it.data
                } else {
                    toastEvent.value = R.string.shop_detail_toast_get_all_get_record_error
                }
            }
    }

}