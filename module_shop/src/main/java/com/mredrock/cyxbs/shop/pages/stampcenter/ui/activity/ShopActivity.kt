package com.mredrock.cyxbs.shop.pages.stampcenter.ui.activity

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
import kotlinx.android.synthetic.main.shop_activity_main.*

@Route(path= SHOP_ENTRY)
class ShopActivity : BaseViewModelActivity<ShopViewModel>(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_activity_main)
        initVPager()

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
}