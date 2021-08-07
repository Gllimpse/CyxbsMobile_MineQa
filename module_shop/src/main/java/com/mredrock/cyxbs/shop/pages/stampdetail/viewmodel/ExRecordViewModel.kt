package com.mredrock.cyxbs.shop.pages.stampdetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel

class ExRecordViewModel : BaseViewModel() {
    val _ExRecordData = MutableLiveData<List<String>>()

    fun getExRecordData(position: Int) = Transformations.map(_ExRecordData) {
            it[position]
        }

    fun exRecordDataSize(): Int{
        return _ExRecordData.value?.size ?:0
    }

}