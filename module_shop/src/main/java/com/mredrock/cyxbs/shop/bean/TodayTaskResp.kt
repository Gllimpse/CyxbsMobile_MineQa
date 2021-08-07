package com.mredrock.cyxbs.shop.bean

import java.io.Serializable

data class TodayTaskResp(
    val count: Int,
    val status: Int,
    val todayTasks: List<TodayTask>
) : Serializable