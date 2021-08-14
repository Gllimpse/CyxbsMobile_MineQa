package com.mredrock.cyxbs.shop.pages.stampdetail.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.aefottt.module_shop.R
import com.aefottt.module_shop.databinding.ShopActivityExchangeDetailBinding
import com.google.gson.Gson
import com.mredrock.cyxbs.common.ui.BaseActivity
import com.mredrock.cyxbs.shop.bean.SingleExRecord

class ExDetailActivity: BaseActivity() {

    companion object{
        fun activityStart(context: Context, exRecord: SingleExRecord){
            context.startActivity(Intent(context, ExDetailActivity::class.java).apply {
                putExtra("exRecord", Gson().toJson(exRecord))
            })
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ShopActivityExchangeDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.shop_activity_exchange_detail)
        binding.record = Gson().fromJson(intent.getStringExtra("exRecord"), SingleExRecord::class.java)
    }
}