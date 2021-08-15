package com.mredrock.cyxbs.shop.widget

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.common.utils.extensions.setOnSingleClickListener
import kotlinx.android.synthetic.main.shop_dialog_detail_exchange.view.*

object ShopDialog {
    
    fun show(context: Context, content: String, onDeny: (() -> Unit)? = null, onPositive: (() -> Unit)? ) {
        val dialog = Dialog(context, R.style.shop_transparent_dialog)
        dialog.setContentView(R.layout.shop_dialog_detail_exchange)
        val view = dialog.window.decorView
        view.shop_dialog_tv_content.text = content
        dialog.window?.attributes?.gravity = Gravity.CENTER
        dialog.show()

        view.shop_dialog_tv_positive.setOnSingleClickListener {
            if (onPositive == null) {
                view.shop_dialog_tv_negative.visibility = View.GONE
            }else onPositive.invoke()
            dialog.dismiss()
        }
        view.shop_dialog_tv_negative.setOnSingleClickListener {
            onDeny?.invoke()
            dialog.dismiss()
        }
    }
}