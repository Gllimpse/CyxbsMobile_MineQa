package com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel
import com.mredrock.cyxbs.shop.config.ShopConfig

class TaskViewModel : BaseViewModel() {
    /**
     * 每日任务的返回值
     */
    private val todayTaskResp = MutableLiveData<List<TodayTask>>()

    /**
     * 更多任务的返回值
     */
    private val moreTaskResp = MutableLiveData<List<MoreTask>>()


    /**
     * 获取任务名称
     */
    fun getTitle(position: Int, type: Int) = if (type == ShopConfig.SHOP_TASK_TYPE_TODAY) {
        Transformations.map(todayTaskResp) {
            it[position].taskName
        }
    } else {
        Transformations.map(moreTaskResp) {
            it[position].taskName
        }
    }

    /**
     * 获取任务描述
     */
    fun getDesc(position: Int, type: Int) = if (type == ShopConfig.SHOP_TASK_TYPE_TODAY) {
        Transformations.map(todayTaskResp) {
            it[position].describe
        }
    } else {
        Transformations.map(moreTaskResp) {
            it[position].describe
        }
    }

    /**
     * 获取完成进度
     */
    fun getDoneAmount(position: Int,type: Int) = if (type == ShopConfig.SHOP_TASK_TYPE_TODAY){
        Transformations.map(todayTaskResp) {
            it[position].doneAmount
        }
    }else {
        Transformations.map(moreTaskResp) {
            it[position].doneAmount
        }
    }

    /**
     * 获取是否已完成
     */
    fun isFinished(position: Int,type: Int) = if (type == ShopConfig.SHOP_TASK_TYPE_TODAY){
        Transformations.map(todayTaskResp) {
            it[position].isFinished
        }
    }else {
        Transformations.map(moreTaskResp) {
            it[position].isFinished
        }
    }

    /**
     * 是否需要进度条
     */
    fun isProgress(position: Int,type: Int) = if (type == ShopConfig.SHOP_TASK_TYPE_TODAY){
        Transformations.map(todayTaskResp) {
            it[position].isProgress
        }
    }else {
        Transformations.map(moreTaskResp) {
            it[position].isProgress
        }
    }

    /**
     * 获取奖励邮票数量
     */
    fun getRewardNum(position: Int,type: Int) = if (type == ShopConfig.SHOP_TASK_TYPE_TODAY){
        Transformations.map(todayTaskResp) {
            it[position].rewardNumber
        }
    }else {
        Transformations.map(moreTaskResp) {
            it[position].rewardNumber
        }
    }

    /**
     * 获取总进度
     */
    fun getTotalAmount(position: Int,type: Int) = if (type == ShopConfig.SHOP_TASK_TYPE_TODAY){
        Transformations.map(todayTaskResp) {
            it[position].totalAmount
        }
    }else {
        Transformations.map(moreTaskResp) {
            it[position].totalAmount
        }
    }

    /**
     * 获取每日任务数量
     */
    fun getTodayTaskSize() = todayTaskResp.value?.size ?: 0

    /**
     * 获取更多任务数量
     */
    fun getMoreTaskSize() = moreTaskResp.value?.size ?:0

    /**
     * 初始化数据
     */
    fun initData(){
        getFakeData()
    }

    /**
     * 假数据
     */
    fun getFakeData(){
        val todayTask = TodayTask("每日签到  ",4,false,true,10,"每日打卡",5)
        val todayList = MutableList(5){todayTask}
        todayTaskResp.value = todayList

        val moreTask = MoreTask("浏览5条动态  ",4,false,true,10,"逛逛邮问",5)
        val moreList = MutableList(5){moreTask}
        moreTaskResp.value = moreList
    }
}
