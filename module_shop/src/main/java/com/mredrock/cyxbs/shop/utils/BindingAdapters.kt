package com.mredrock.cyxbs.shop.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.shop.config.ShopConfig

@BindingAdapter("app:titleByType")
fun titleByType(tv: TextView, type: Int){
    tv.text = if (type == ShopConfig.TYPE_STAMP_GOOD)
        tv.context.getString(R.string.shop_detail_title_stamp)
    else tv.context.getString(R.string.shop_detail_title_decoration)
}

@BindingAdapter("app:rightTipsByType")
fun rightTipsByType(tv: TextView, type: Int){
    tv.text = if (type == ShopConfig.TYPE_STAMP_GOOD)
        tv.context.getString(R.string.shop_detail_right_tips_stamp)
    else tv.context.getString(R.string.shop_detail_right_tips_decoration)
}