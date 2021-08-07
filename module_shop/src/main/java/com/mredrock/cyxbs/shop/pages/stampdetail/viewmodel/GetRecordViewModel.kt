package com.mredrock.cyxbs.shop.pages.stampdetail.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel

class GetRecordViewModel : BaseViewModel() {
    val _GetRecordData = MutableLiveData<List<String>>()

    fun getRecordDataSize(): Int{
        return _GetRecordData.value?.size ?:0
    }
}