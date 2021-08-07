package com.mredrock.cyxbs.shop.pages.stampdetail.ui.activity

import android.os.Bundle
import com.aefottt.module_shop.R
import com.google.android.material.tabs.TabLayoutMediator
import com.mredrock.cyxbs.common.ui.BaseActivity
import com.mredrock.cyxbs.common.ui.BaseViewModelActivity
import com.mredrock.cyxbs.shop.pages.stampdetail.adapter.StampPagerAdapter
import com.mredrock.cyxbs.shop.pages.stampdetail.ui.fragment.ExRecordFragment
import com.mredrock.cyxbs.shop.pages.stampdetail.ui.fragment.GetRecordFragment
import kotlinx.android.synthetic.main.shop_activity_detail_stamp.*

class StampDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_activity_detail_stamp)
        initPager()
    }

    private fun initPager(){
        shop_detail_stamp_vp.adapter = StampPagerAdapter(this).apply {
            addFragment(ExRecordFragment())
            addFragment(GetRecordFragment())
        }
        TabLayoutMediator(shop_detail_stamp_tl,shop_detail_stamp_vp){tab,position ->
            when(position){
                0 -> tab.text = "兑换记录"
                1 -> tab.text = "获取记录"
            }
        }
    }
}