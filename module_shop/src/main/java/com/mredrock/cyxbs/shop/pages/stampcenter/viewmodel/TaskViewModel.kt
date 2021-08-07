package com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel
import com.mredrock.cyxbs.shop.bean.MoreTask
import com.mredrock.cyxbs.shop.bean.TodayTask
import com.mredrock.cyxbs.shop.config.ShopConfig
import kotlin.math.log
import kotlin.random.Random

class TaskViewModel : BaseViewModel() {
    val _todayTaskData = MutableLiveData<List<TodayTask>>()

    val _moreTaskData = MutableLiveData<List<MoreTask>>()


    fun getTitle(position: Int, type: Int) = if (type == ShopConfig.TASK_TYPE_TODAY) {
        Transformations.map(_todayTaskData) {
            it[position].taskName
        }
    } else {
        Transformations.map(_moreTaskData) {
            it[position].taskName
        }
    }

    fun getDesc(position: Int, type: Int) = if (type == ShopConfig.TASK_TYPE_TODAY) {
        Transformations.map(_todayTaskData) {
            it[position].describe
        }
    } else {
        Transformations.map(_moreTaskData) {
            it[position].describe
        }
    }

    fun getDoneAmount(position: Int,type: Int) = if (type == ShopConfig.TASK_TYPE_TODAY){
        Transformations.map(_todayTaskData) {
            it[position].doneAmount
        }
    }else {
        Transformations.map(_moreTaskData) {
            it[position].doneAmount
        }
    }

    fun isFinished(position: Int,type: Int) = if (type == ShopConfig.TASK_TYPE_TODAY){
        Transformations.map(_todayTaskData) {
            it[position].isFinished
        }
    }else {
        Transformations.map(_moreTaskData) {
            it[position].isFinished
        }
    }

    fun isProgress(position: Int,type: Int) = if (type == ShopConfig.TASK_TYPE_TODAY){
        Transformations.map(_todayTaskData) {
            it[position].isProgress
        }
    }else {
        Transformations.map(_moreTaskData) {
            it[position].isProgress
        }
    }

    fun getRewardNum(position: Int,type: Int) = if (type == ShopConfig.TASK_TYPE_TODAY){
        Transformations.map(_todayTaskData) {
            it[position].rewardNumber
        }
    }else {
        Transformations.map(_moreTaskData) {
            it[position].rewardNumber
        }
    }

    fun getTotalAmount(position: Int,type: Int) = if (type == ShopConfig.TASK_TYPE_TODAY){
        Transformations.map(_todayTaskData) {
            it[position].totalAmount
        }
    }else {
        Transformations.map(_moreTaskData) {
            it[position].totalAmount
        }
    }

    fun getTodayTaskSize() = _todayTaskData.value?.size ?: 0
    fun getMoreTaskSize() = _moreTaskData.value?.size ?:0

    fun getTodayTaskData(){}
    fun getMoreTaskData(){}

    fun test(){
        val todayTask = TodayTask(Random(System.currentTimeMillis()).nextInt().toString(),5,false,true,10,"233",1)
        val todayList = MutableList(20){todayTask}
        _todayTaskData.value = todayList
        val moreTask = MoreTask(Random(System.currentTimeMillis()).nextInt().toString(),5,false,true,10,"233",1)
        val moreList = MutableList(20){moreTask}
        _moreTaskData.value = moreList
    }
}
