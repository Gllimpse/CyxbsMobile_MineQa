package com.mredrock.cyxbs.shop.bean

import java.io.Serializable

data class StampGood(
    val desc: String,
    val image_urls: List<String>,
    val left_count: Int,
    val price: Int,
    val title: String
) : Serializable