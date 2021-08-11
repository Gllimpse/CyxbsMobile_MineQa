package com.mredrock.cyxbs.shop.bean

import com.mredrock.cyxbs.shop.config.ShopConfig
import java.io.Serializable

data class MoreTask(
    val describe: String,
    val doneAmount: Int,
    val isFinished: Boolean,
    val isProgress: Boolean,
    val rewardNumber: Int,
    val taskName: String,
    val totalAmount: Int
) : Serializable