package com.mredrock.cyxbs.shop.pages.stampdetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel

class GetRecordViewModel : BaseViewModel() {
    val _GetRecordData = MutableLiveData<List<String>>()

    fun getGetRecordData(position: Int) = Transformations.map(_GetRecordData) {
        it[position]
    }

    fun getRecordDataSize(): Int{
        return _GetRecordData.value?.size ?:0
    }
}