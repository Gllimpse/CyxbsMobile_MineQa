package com.mredrock.cyxbs.shop.pages.detail.ui

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.aefottt.module_shop.R
import com.aefottt.module_shop.databinding.ShopActivityDetailBinding
import com.mredrock.cyxbs.common.ui.BaseActivity
import com.mredrock.cyxbs.common.ui.BaseViewModelActivity
import com.mredrock.cyxbs.common.utils.extensions.dp2px
import com.mredrock.cyxbs.shop.config.ShopConfig
import com.mredrock.cyxbs.shop.config.ShopConfig.SHOP_DETAIL_IMAGE_TRANSITION
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
    private var lastPos = 0

    companion object {
        fun activityStart(context: Context, title: String, type: Int) {
            context.startActivity(Intent(context, DetailActivity::class.java)
                .apply {
                    putExtra("title", title)
                    putExtra("type", type)
                })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
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
                ShopConfig.DIALOG_TYPE_FIRST_SURE_STAMP -> {
                    content = "确认要用${viewModel.getPrice(ShopConfig.TYPE_STAMP_GOOD)}邮票兑换PM名片吗"
                    onPositive = { viewModel.exchangeGood(ShopConfig.TYPE_STAMP_GOOD) }
                }
                ShopConfig.DIALOG_TYPE_FIRST_SURE_DECORATION -> {
                    content = "确认要用${viewModel.getPrice(ShopConfig.TYPE_DECORATION)}邮票兑换PM名片吗"
                    onPositive = { viewModel.exchangeGood(ShopConfig.TYPE_DECORATION) }
                }
                ShopConfig.DIALOG_TYPE_STAMP_SHORTAGE -> content = "邮票数量不足！"
                ShopConfig.DIALOG_TYPE_FAIL -> content = "兑换失败！"
                ShopConfig.DIALOG_TYPE_COUNT_SHORTAGE -> content = "啊哦！手慢了！下次再来吧!"
                ShopConfig.DIALOG_TYPE_SUCCESS -> content = "兑换成功"
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
                this@DetailActivity.startActivity(
                    Intent(this@DetailActivity, BaseActivity::class.java)
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
                        },
                    ActivityOptions.makeSceneTransitionAnimation(
                        this@DetailActivity,
                        shop_detail_vp_banner,
                        SHOP_DETAIL_IMAGE_TRANSITION
                    ).toBundle()
                )
            }
        }
        // 监听ViewPager界面滑动
        shop_detail_vp_banner.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                curPos = position
                shop_detail_ll_banner_dots.getChildAt(position)
                    .setBackgroundResource(R.drawable.shop_shape_banner_dots_selected)
                shop_detail_ll_banner_dots.getChildAt(lastPos)
                    .setBackgroundResource(R.drawable.shop_shape_banner_dots)
                lastPos = position
            }

            override fun onPageScrollStateChanged(state: Int) {}
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
                if (count == 0)
                    setBackgroundResource(R.drawable.shop_shape_banner_dots_selected)
                else
                    setBackgroundResource(R.drawable.shop_shape_banner_dots)
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.marginEnd = dp2px(6f)
                layoutParams = lp
            })
        }
    }
}