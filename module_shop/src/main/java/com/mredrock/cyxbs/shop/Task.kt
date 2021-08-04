package com.mredrock.cyxbs.shop

import androidx.annotation.Keep

@Keep
data class Task(
    val count: Int,
    val status: Int,
    val todayTasks: List<TodayTask>
) {
    @Keep
    data class TodayTask(
            val describe: String,
            val doneAmount: Int,
            val isFinished: Boolean,
            val isProgress: Boolean,
            val rewardNumber: Int,
            val taskName: String,
            val totalAmount: Int
    )
}