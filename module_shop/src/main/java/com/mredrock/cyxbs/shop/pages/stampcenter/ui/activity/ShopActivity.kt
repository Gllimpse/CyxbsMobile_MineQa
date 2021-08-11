package com.mredrock.cyxbs.shop.pages.stampcenter.ui.activity


import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Window
import com.aefottt.module_shop.R
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.mredrock.cyxbs.common.config.SHOP_ENTRY
import com.mredrock.cyxbs.common.ui.BaseViewModelActivity
import com.mredrock.cyxbs.common.utils.extensions.dp2px
import com.mredrock.cyxbs.shop.pages.stampcenter.adapter.ShopPagerAdapter
import com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment.ShopFragment
import com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment.TaskFragment
import com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel.ShopViewModel
import com.mredrock.cyxbs.shop.pages.stampdetail.ui.activity.StampDetailActivity
import kotlinx.android.synthetic.main.shop_activity_main.*

@Route(path= SHOP_ENTRY)
class ShopActivity : BaseViewModelActivity<ShopViewModel>(){
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_activity_main)
        initView()
        initListener()

    }
    private fun initView(){
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
        // 卡券动画
        shop_main_iv_coupon.animate().setDuration(600)
            .translationY(0f).alpha(1f).start()
        // 开启数字动画
        shop_main_tv_banner_number.post {
            shop_main_tv_banner_number.setText("526321")
            shop_main_tv_banner_number.startSlide(800)
        }
        // 设置字体
        shop_main_tv_my_stamps.typeface = Typeface.createFromAsset(assets, "fonts/shop_font_price_number.otf")
    }

    private fun initListener(){
        shop_main_tv_banner_detail.setOnClickListener {
            startActivity(Intent(this,StampDetailActivity::class.java))
        }
    }
}