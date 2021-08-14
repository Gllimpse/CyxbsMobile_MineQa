package com.mredrock.cyxbs.shop.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.shop.config.ShopConfig
import java.text.SimpleDateFormat
import java.util.*

/**
 * 根据商品类型设置标题
 */
@BindingAdapter("app:detailTitleByType")
fun detailTitleByType(tv: TextView, type: Int){
    tv.text = if (type == ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD)
        tv.context.getString(R.string.shop_detail_title_stamp)
    else tv.context.getString(R.string.shop_detail_title_decoration)
}

/**
 * 根据商品类型设置不同的权益说明
 */
@BindingAdapter("app:detailRightTipsByType")
fun detailRightTipsByType(tv: TextView, type: Int){
    tv.text = if (type == ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD)
        tv.context.getString(R.string.shop_detail_right_tips_stamp)
    else tv.context.getString(R.string.shop_detail_right_tips_decoration)
}

@BindingAdapter("app:goodsDetailGetVisible")
fun goodsDetailGetVisible(tv: TextView, isCollected: Boolean){
    tv.visibility = if (isCollected) View.GONE else View.VISIBLE
}

@BindingAdapter("app:stampDetailTime")
fun stampDetailTime(tv: TextView, time: Long){
    val format = SimpleDateFormat("yyyy.MM.dd", Locale.CHINA)
    tv.text = format.format(Date(time))
}

@BindingAdapter("app:exchangeDetailTime")
fun exchangeDetailTime(tv: TextView, time: Long){
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
    tv.text = format.format(Date(time))
}