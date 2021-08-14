package com.mredrock.cyxbs.shop.pages.stampcenter.ui.activity


import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.aefottt.module_shop.R
import com.aefottt.module_shop.databinding.ShopActivityMainBinding
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
import com.mredrock.cyxbs.shop.config.ShopConfig
import com.mredrock.cyxbs.shop.pages.stampcenter.adapter.ShopPagerAdapter
import com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment.ShopFragment
import com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment.TaskFragment
import com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel.ShopViewModel
import com.mredrock.cyxbs.shop.pages.stampdetail.ui.activity.StampDetailActivity
import kotlinx.android.synthetic.main.shop_activity_main.*
import java.text.SimpleDateFormat
import java.util.*

@Route(path = SHOP_ENTRY)
class ShopActivity : BaseViewModelActivity<ShopViewModel>() {
    private lateinit var binding : ShopActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_activity_main)
        setTheme(R.style.Theme_MaterialComponents)
        // 绑定布局,数据
        binding = DataBindingUtil.setContentView(this, R.layout.shop_activity_main)
        binding.apply {
            lifecycleOwner = this@ShopActivity
            this.viewModel = viewModel
        }

        viewModel.initData()
        initObserve()
        initView()
        initListener()
    }

    private fun initObserve(){
        viewModel.stampCount.observe(this) {
            shop_main_tv_banner_number.setCurrNum(it)
        }
    }

    private fun initView() {
        shop_main_vp.apply {
            adapter = ShopPagerAdapter(this@ShopActivity).apply {
                addFragment(ShopFragment())
                addFragment(TaskFragment())
            }

            //viewpager切换动画
            setPageTransformer { view, position ->
                if (position < -1 || position > 1) {
                    view.alpha = 0f
                } else if (position > 0) {
                    view.apply {
                        scaleX = 0.4f * (position - 1 / 2f) * (position - 1 / 2f) + 0.9f
                        scaleY = 0.4f * (position - 1 / 2f) * (position - 1 / 2f) + 0.9f
                    }
                } else {
                    view.apply {
                        scaleX = 0.4f * (-position - 1 / 2f) * (-position - 1 / 2f) + 0.9f
                        scaleY = 0.4f * (-position - 1 / 2f) * (-position - 1 / 2f) + 0.9f
                    }
                }
            }
        }

        shop_main_tl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.removeBadge()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        TabLayoutMediator(shop_main_tl, shop_main_vp) { tab, position ->
            when (position) {
                0 -> tab.text = "邮票小店"
                1 -> tab.apply {
                    text = "邮票任务"

                    /**
                     * 设置右上角badge样式，当天隐藏后不再显示
                     */
                    this@ShopActivity.sharedPreferences("tab_click_time").apply {
                        val sdf = SimpleDateFormat(
                                "yyyy年MM月dd日",
                                Locale.getDefault()
                        )
                        val todayTimeStamp = sdf.format(System.currentTimeMillis()).split("月")[1]
                        if (this.getString("tab_time_stamp", "") != todayTimeStamp) {
                            val badge = orCreateBadge
                            try {
                                val field = badge.javaClass.getDeclaredField("badgeRadius")
                                field.isAccessible = true
                                field.set(badge, dp2px(3.5f))
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            orCreateBadge.backgroundColor = Color.parseColor("#6D68FF")
                            orCreateBadge.horizontalOffset = dp2px(-6f)
                        }
                        editor {
                            putString("tab_time_stamp", todayTimeStamp)
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
            shop_main_tv_banner_number.setCurrNum(viewModel.stampCount.value ?:0)
            shop_main_tv_banner_number.setDuration(900)
        }

        // 设置字体
        shop_main_tv_my_stamps.typeface =
                Typeface.createFromAsset(assets, "fonts/shop_font_price_number.otf")
        val arr: IntArray = intArrayOf(0, 0)
        shop_main_ll_bottom.post {
            shop_main_ll_bottom.getLocationOnScreen(arr)
            ShopConfig.SHOP_CHILD_TOP = arr[1]
        }
    }

    private fun initListener() {
        shop_main_tv_banner_detail.setOnSingleClickListener {
            startActivity(Intent(this, StampDetailActivity::class.java))
        }
        shop_main_iv_back.setOnSingleClickListener {
            finish()
        }
    }
}



