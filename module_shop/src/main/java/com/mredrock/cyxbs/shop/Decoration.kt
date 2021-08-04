package com.mredrock.cyxbs.shop


import androidx.annotation.Keep

@Keep
data class Decoration(
    val Decorations: List<Decoration>,
    val status: Int
) {
    @Keep
    data class Decoration(
        val desc: String,
        val id: String,
        val imageUrl: List<ImageUrl>,
        val imageUrls: List<ImageUrl>,
        val leftCount: Int,
        val periods: Int,
        val price: Int,
        val title: String
    ) {
        @Keep
        data class ImageUrl(
            val imageUrl1: String,
            val imageUrl2: String
        )

    }
}