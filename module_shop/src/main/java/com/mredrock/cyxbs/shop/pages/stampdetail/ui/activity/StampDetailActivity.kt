package com.mredrock.cyxbs.shop.pages.stampdetail.ui.activity

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aefottt.module_shop.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mredrock.cyxbs.common.ui.BaseActivity
import com.mredrock.cyxbs.common.ui.BaseViewModelActivity
import com.mredrock.cyxbs.common.utils.extensions.dp2px
import com.mredrock.cyxbs.common.utils.extensions.setOnSingleClickListener
import com.mredrock.cyxbs.common.utils.extensions.sp
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
        initListener()
    }

    private fun initPager(){
        shop_detail_stamp_vp.apply {
            adapter = StampPagerAdapter(this@StampDetailActivity).apply {
                addFragment(ExRecordFragment())
                addFragment(GetRecordFragment())
            }

            //viewpager切换动画
            setPageTransformer{view, position ->
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

        /**
         * tab状态改变，字体大小动态变化
         */
        shop_detail_stamp_tl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                val textView = if (tab.customView != null) {
                    tab.customView as TextView
                }else {
                    (LayoutInflater.from(this@StampDetailActivity)
                            .inflate(R.layout.shop_layout_detail_stamp_tab, null, false) as TextView)
                            .also {
                                tab.customView = it
                            }
                }
                textView.apply {
                    text = tab.text
                    setTextColor(Color.parseColor("#5F75E4"))
                }

                ObjectAnimator.ofFloat(textView,"textSize",
                        14f,16f)
                        .apply {
                            duration = 500
                            start()
                        }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val textView = tab.customView as TextView

                textView.apply {
                    text = tab.text
                    setTextColor(Color.parseColor("#CC15315B"))
                }
                ObjectAnimator.ofFloat(textView,"textSize",
                        16f,14f)
                        .apply {
                            duration = 500
                            start()
                        }

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        TabLayoutMediator(shop_detail_stamp_tl,shop_detail_stamp_vp){tab,position ->
            when(position){
                0 -> tab.text = "兑换记录"
                1 -> tab.text = "获取记录"
            }
        }.attach()
    }

    private fun initListener() {
        shop_stampdetail_iv_back.setOnSingleClickListener { finish() }
    }
}