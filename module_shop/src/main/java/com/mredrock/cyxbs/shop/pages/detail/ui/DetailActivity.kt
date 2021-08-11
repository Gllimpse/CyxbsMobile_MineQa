package com.mredrock.cyxbs.shop.pages.detail.ui

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.aefottt.module_shop.R
import com.aefottt.module_shop.databinding.ShopActivityDetailBinding
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.mredrock.cyxbs.common.ui.BaseActivity
import com.mredrock.cyxbs.common.ui.BaseViewModelActivity
import com.mredrock.cyxbs.common.utils.extensions.dp2px
import com.mredrock.cyxbs.common.utils.extensions.startActivityForResult
import com.mredrock.cyxbs.shop.config.ShopConfig
import com.mredrock.cyxbs.shop.config.ShopConfig.SHOP_TRANSITION_DETAIL_IMAGE
import com.mredrock.cyxbs.shop.pages.detail.adapter.BannerPagerAdapter
import com.mredrock.cyxbs.shop.pages.detail.viewmodel.DetailViewModel
import com.mredrock.cyxbs.shop.widget.ShopDialog
import kotlinx.android.synthetic.main.shop_activity_detail.*
import kotlinx.android.synthetic.main.shop_dialog_detail_exchange.view.*

class DetailActivity : BaseViewModelActivity<DetailViewModel>() {
    private lateinit var binding: ShopActivityDetailBinding

    // Banner当前位置
    private var curPos = 0

    // Banner上一个指示器的位置指示器
    private var lastPos = 1

    companion object {
        fun activityStart(context: Context, title: String, type: Int, shareElement: View) {
            context.startActivity(
                Intent(context, DetailActivity::class.java)
                    .apply {
                        putExtra("title", title)
                        putExtra("type", type)
                    }, ActivityOptions.makeSceneTransitionAnimation(
                    context as Activity, shareElement, ShopConfig.SHOP_TRANSITION_GOOD_TO_DETAIL
                ).toBundle()
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        findViewById<View>(android.R.id.content).transitionName =
            ShopConfig.SHOP_TRANSITION_GOOD_TO_DETAIL
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
            duration = 300L
            setAllContainerColors(Color.WHITE)
        }
        window.sharedElementExitTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
            duration = 250L
            setAllContainerColors(Color.WHITE)
        }
        super.onCreate(savedInstanceState)
        // 获取传入的数据
        viewModel.type = intent.getIntExtra("type", 0) // 商品类型
        val title = intent.getStringExtra("title") // 商品名称
        // 初始化数据
        viewModel.initGoodData(title)
        // 绑定布局
        binding = DataBindingUtil.setContentView(this, R.layout.shop_activity_detail)
        binding.type = viewModel.type
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initView()
        initObserve()
    }

    private fun initObserve() {
        // 对话框事件
        viewModel.dialogEvent.observe(this, Observer {
            var content = ""
            val onDeny: (() -> Unit) = {}
            var onPositive: (() -> Unit)? = null
            when (it) {
                ShopConfig.SHOP_DIALOG_TYPE_FIRST_SURE_STAMP -> {
                    content =
                        "确认要用${viewModel.getPrice(ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD)}邮票兑换PM名片吗"
                    onPositive = { viewModel.exchangeGood(ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD) }
                }
                ShopConfig.SHOP_DIALOG_TYPE_FIRST_SURE_DECORATION -> {
                    content =
                        "确认要用${viewModel.getPrice(ShopConfig.SHOP_GOOD_TYPE_DECORATION)}邮票兑换PM名片吗"
                    onPositive = { viewModel.exchangeGood(ShopConfig.SHOP_GOOD_TYPE_DECORATION) }
                }
                ShopConfig.SHOP_DIALOG_TYPE_STAMP_SHORTAGE -> content = "邮票数量不足！"
                ShopConfig.SHOP_DIALOG_TYPE_FAIL -> content = "兑换失败！"
                ShopConfig.SHOP_DIALOG_TYPE_COUNT_SHORTAGE -> content = "啊哦！手慢了！下次再来吧!"
                ShopConfig.SHOP_DIALOG_TYPE_SUCCESS -> content = "兑换成功"
            }
            ShopDialog.show(this, content, onDeny, onPositive)
        })
    }

    private fun initView() {
        // 获取邮货/装饰详情的图片地址
        val picUrls = viewModel.getPicUrls()
        // 初始化Banner下方的圆点
        initBannerDots(picUrls?.size ?: 0)
        // 设置ViewPager
        shop_detail_vp_banner.adapter = BannerPagerAdapter(picUrls).apply {
            photoTapClick = {
                // 共享元素跳转
                this@DetailActivity.startActivityForResult(
                    Intent(this@DetailActivity, ImageActivity::class.java)
                        .apply {
                            // 将图片地址和当前图片位置作为参数传入
                            picUrls?.let {
                                val picUrlsArray = arrayOfNulls<String>(it.size)
                                for ((i, url) in it.withIndex()) {
                                    picUrlsArray[i] = url
                                }
                                putExtra("picUrls", picUrlsArray)
                                putExtra("pos", curPos)
                            }
                        }, 1,
                    ActivityOptions.makeSceneTransitionAnimation(
                        this@DetailActivity,
                        shop_detail_vp_banner,
                        SHOP_TRANSITION_DETAIL_IMAGE
                    ).toBundle()
                )
            }
        }

        // 监听ViewPager界面滑动
        shop_detail_vp_banner.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                curPos = position
                shop_detail_ll_banner_dots.getChildAt(position)
                    .setBackgroundResource(R.drawable.shop_shape_banner_dots_selected)
                shop_detail_ll_banner_dots.getChildAt(lastPos)
                    .setBackgroundResource(R.drawable.shop_shape_banner_dots)
                lastPos = position
            }
        })
    }

    /**
     * 初始化Banner下方的指示器圆点
     * @count 圆点数量
     */
    private fun initBannerDots(count: Int) {
        if (count <= 0) return
        repeat(count) {
            shop_detail_ll_banner_dots.addView(View(this).apply {
                if (it == 0)
                    setBackgroundResource(R.drawable.shop_shape_banner_dots_selected)
                else
                    setBackgroundResource(R.drawable.shop_shape_banner_dots)
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.width = dp2px(6f)
                lp.height = dp2px(6f)
                lp.marginEnd = dp2px(6f)
                layoutParams = lp
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            curPos = data?.getIntExtra("pos", 0) ?: 0
            shop_detail_vp_banner.setCurrentItem(curPos, false)
        }
    }

}