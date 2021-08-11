package com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel
import com.mredrock.cyxbs.shop.bean.MoreTask
import com.mredrock.cyxbs.shop.bean.TodayTask
import com.mredrock.cyxbs.shop.config.ShopConfig
import kotlin.random.Random

class TaskViewModel : BaseViewModel() {
    /**
     * 每日任务的返回值
     */
    val _todayTaskResp = MutableLiveData<List<TodayTask>>()

    /**
     * 更多任务的返回值
     */
    val _moreTaskResp = MutableLiveData<List<MoreTask>>()


    /**
     * 获取任务名称
     */
    fun getTitle(position: Int, type: Int) = if (type == ShopConfig.SHOP_TASK_TYPE_TODAY) {
        Transformations.map(_todayTaskResp) {
            it[position].taskName
        }
    } else {
        Transformations.map(_moreTaskResp) {
            it[position].taskName
        }
    }

    /**
     * 获取任务描述
     */
    fun getDesc(position: Int, type: Int) = if (type == ShopConfig.SHOP_TASK_TYPE_TODAY) {
        Transformations.map(_todayTaskResp) {
            it[position].describe
        }
    } else {
        Transformations.map(_moreTaskResp) {
            it[position].describe
        }
    }

    /**
     * 获取完成进度
     */
    fun getDoneAmount(position: Int,type: Int) = if (type == ShopConfig.SHOP_TASK_TYPE_TODAY){
        Transformations.map(_todayTaskResp) {
            it[position].doneAmount
        }
    }else {
        Transformations.map(_moreTaskResp) {
            it[position].doneAmount
        }
    }

    /**
     * 获取是否已完成
     */
    fun isFinished(position: Int,type: Int) = if (type == ShopConfig.SHOP_TASK_TYPE_TODAY){
        Transformations.map(_todayTaskResp) {
            it[position].isFinished
        }
    }else {
        Transformations.map(_moreTaskResp) {
            it[position].isFinished
        }
    }

    /**
     *
     */
    fun isProgress(position: Int,type: Int) = if (type == ShopConfig.SHOP_TASK_TYPE_TODAY){
        Transformations.map(_todayTaskResp) {
            it[position].isProgress
        }
    }else {
        Transformations.map(_moreTaskResp) {
            it[position].isProgress
        }
    }

    /**
     * 获取奖励邮票数量
     */
    fun getRewardNum(position: Int,type: Int) = if (type == ShopConfig.SHOP_TASK_TYPE_TODAY){
        Transformations.map(_todayTaskResp) {
            it[position].rewardNumber
        }
    }else {
        Transformations.map(_moreTaskResp) {
            it[position].rewardNumber
        }
    }

    /**
     * 获取总进度
     */
    fun getTotalAmount(position: Int,type: Int) = if (type == ShopConfig.SHOP_TASK_TYPE_TODAY){
        Transformations.map(_todayTaskResp) {
            it[position].totalAmount
        }
    }else {
        Transformations.map(_moreTaskResp) {
            it[position].totalAmount
        }
    }

    /**
     * 获取任务数量
     */
    fun getTodayTaskSize() = _todayTaskResp.value?.size ?: 0
    fun getMoreTaskSize() = _moreTaskResp.value?.size ?:0

    fun getTodayTaskData(){}
    fun getMoreTaskData(){}

    fun test(){
        val todayTask = TodayTask(Random(System.currentTimeMillis()).nextInt().toString(),5,false,true,10,"233",1)
        val todayList = MutableList(20){todayTask}
        _todayTaskResp.value = todayList
        val moreTask = MoreTask(Random(System.currentTimeMillis()).nextInt().toString(),5,false,true,10,"233",1)
        val moreList = MutableList(20){moreTask}
        _moreTaskResp.value = moreList
    }
}
