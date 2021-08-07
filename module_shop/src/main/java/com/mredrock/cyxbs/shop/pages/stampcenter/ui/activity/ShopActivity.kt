package com.mredrock.cyxbs.shop.pages.stampcenter.ui.activity


import android.content.Intent
import android.os.Bundle
import com.aefottt.module_shop.R
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayoutMediator
import com.mredrock.cyxbs.common.config.SHOP_ENTRY
import com.mredrock.cyxbs.common.ui.BaseViewModelActivity
import com.mredrock.cyxbs.shop.pages.stampcenter.adapter.ShopPagerAdapter
import com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment.ShopFragment
import com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment.TaskFragment
import com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel.ShopViewModel
import com.mredrock.cyxbs.shop.pages.stampdetail.ui.activity.StampDetailActivity
import kotlinx.android.synthetic.main.shop_activity_main.*

@Route(path= SHOP_ENTRY)
class ShopActivity : BaseViewModelActivity<ShopViewModel>(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_activity_main)
        initVPager()
        initListener()

    }
    private fun initVPager(){
        shop_main_vp.adapter = ShopPagerAdapter(this).apply {
            addFragment(ShopFragment())
            addFragment(TaskFragment())
        }
        TabLayoutMediator(shop_main_tl,shop_main_vp){tab, position ->
            when(position){
                0 -> tab.text = "邮票小店"
                1 -> tab.text = "邮票任务"
            }
        }.attach()
    }

    private fun initListener(){
        shop_main_tv_banner_detail.setOnClickListener {
            startActivity(Intent(this,StampDetailActivity::class.java))
        }
    }
}