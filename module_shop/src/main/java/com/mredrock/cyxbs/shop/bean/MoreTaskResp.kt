package com.mredrock.cyxbs.shop.bean

import java.io.Serializable

data class MoreTaskResp(
    val count: Int,
    val moreTasks: List<MoreTask>,
    val status: Int
) : Serializable