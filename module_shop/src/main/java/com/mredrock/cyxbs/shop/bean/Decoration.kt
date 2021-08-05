package com.mredrock.cyxbs.shop.bean

import java.io.Serializable

data class Decoration(
        val title: String,
        val left_Count : Int,
        val desc: String,
        val period: Int,
        val price: Int,
        val image_urls: List<String>
        ) : Serializable
