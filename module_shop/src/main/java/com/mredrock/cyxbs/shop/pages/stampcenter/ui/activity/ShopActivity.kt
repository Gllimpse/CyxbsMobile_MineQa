package com.mredrock.cyxbs.shop.pages.stampcenter.ui.activity


import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Window
import com.aefottt.module_shop.R
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.mredrock.cyxbs.common.config.SHOP_ENTRY
import com.mredrock.cyxbs.common.ui.BaseViewModelActivity
import com.mredrock.cyxbs.common.utils.extensions.dp2px
import com.mredrock.cyxbs.common.utils.extensions.editor
import com.mredrock.cyxbs.common.utils.extensions.setOnSingleClickListener
import com.mredrock.cyxbs.common.utils.extensions.sharedPreferences
import com.mredrock.cyxbs.shop.pages.stampcenter.adapter.ShopPagerAdapter
import com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment.ShopFragment
import com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment.TaskFragment
import com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel.ShopViewModel
import com.mredrock.cyxbs.shop.pages.stampdetail.ui.activity.StampDetailActivity
import kotlinx.android.synthetic.main.shop_activity_main.*
import java.text.SimpleDateFormat
import java.util.*

@Route(path= SHOP_ENTRY)
class ShopActivity : BaseViewModelActivity<ShopViewModel>(){
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_activity_main)
        setTheme(R.style.Theme_MaterialComponents)
        initView()
        initListener()
    }

    private fun initView(){
        shop_main_vp.adapter = ShopPagerAdapter(this).apply {
            addFragment(ShopFragment())
            addFragment(TaskFragment())
        }

        shop_main_tl.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.removeBadge()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        TabLayoutMediator(shop_main_tl,shop_main_vp){tab, position ->
            when(position){
                0 -> tab.text = "邮票小店"
                1 -> tab.apply {
                    text = "邮票任务"
                    this@ShopActivity.sharedPreferences("tab_click_time").apply {
                        val sdf = SimpleDateFormat("yyyy年MM月dd日",
                                Locale.getDefault())
                        val todayTimeStamp = sdf.format(System.currentTimeMillis()).split("月")[1]
                        if (this.getString("tab_time_stamp","") != todayTimeStamp){
                            orCreateBadge.backgroundColor = Color.parseColor("#6D68FF")
                            orCreateBadge.horizontalOffset =dp2px( -6f)
                        }
                        editor {
                            putString("tab_time_stamp",todayTimeStamp)
                        }
                    }
                }
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
        shop_main_tv_banner_detail.setOnSingleClickListener {
            startActivity(Intent(this,StampDetailActivity::class.java))
        }
    }
}