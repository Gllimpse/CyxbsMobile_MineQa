package com.mredrock.cyxbs.shop.bean

import com.google.gson.annotations.SerializedName

data class TaskResp(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: List<Task>
)

data class Task(
    @SerializedName("title")
    val title: String,
    @SerializedName("max_progress")
    val maxProgress: Int,
    @SerializedName("current_progress")
    val curProgress: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("name")
    val name: String
)