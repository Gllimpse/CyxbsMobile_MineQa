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

/**
 * 邮票明细页面
 */
class StampDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_activity_detail_stamp)
        initPager()
    }

    private fun initPager(){
        shop_detail_stamp_vp.apply {
            adapter = StampPagerAdapter(this@StampDetailActivity).apply {
                addFragment(ExRecordFragment())
                addFragment(GetRecordFragment())
            }
            setPageTransformer{view, position ->
                val pageWidth = view.width
                val pageHeight = view.height
                if (position < -1 || position > 1){
                    view.alpha = 0f
                }else if (position > 0){
                    view.apply {
                        scaleX = 0.4f * (position - 1/2f)*(position - 1/2f) +0.9f
                        scaleY = 0.4f * (position - 1/2f)*(position - 1/2f) +0.9f
                    }
                }else {
                    view.apply {
                        scaleX = 0.4f * (-position - 1/2f)*(-position - 1/2f) +0.9f
                        scaleY = 0.4f * (-position - 1/2f)*(-position - 1/2f) +0.9f
                    }
                }
            }
        }
        TabLayoutMediator(shop_detail_stamp_tl,shop_detail_stamp_vp){tab,position ->
            when(position){
                0 -> tab.text = "兑换记录"
                1 -> tab.apply {
                    text = "获取记录"
                    badge
                }
            }
        }.attach()
    }
}