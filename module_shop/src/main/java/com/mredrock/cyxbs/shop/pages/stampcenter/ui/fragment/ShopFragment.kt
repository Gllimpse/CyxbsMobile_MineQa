package com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.aefottt.module_shop.R
import com.aefottt.module_shop.databinding.*
import com.bumptech.glide.Glide
import com.mredrock.cyxbs.common.ui.BaseViewModelFragment
import com.mredrock.cyxbs.shop.adapter.DataBindingAdapter
import com.mredrock.cyxbs.shop.config.ShopConfig
import com.mredrock.cyxbs.shop.pages.detail.ui.DetailActivity
import com.mredrock.cyxbs.shop.pages.stampcenter.ui.activity.ShopActivity
import com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel.ShopViewModel
import kotlinx.android.synthetic.main.shop_fragment_shop.*

class ShopFragment: BaseViewModelFragment<ShopViewModel>() {
    private lateinit var goodsRvAdapter: DataBindingAdapter
    //邮货title的位置
    private var stampStartPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shop_fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initData()
        initView()
    }

    private fun initView(){
        viewModel.isSuccess.observe(viewLifecycleOwner, Observer { it ->
            if (it){
                goodsRvAdapter = DataBindingAdapter(viewLifecycleOwner,viewModel)
                        .addDataBinding(DataBindingAdapter.MyDataBinding<ShopRecycleItemTitleGoodBinding>(
                                R.layout.shop_recycle_item_title_good,0,1,
                                { _,binding ->
                                    binding?.title = "装饰"
                                }))
                        .addDataBinding(DataBindingAdapter.MyDataBinding<ShopRecycleItemGoodBinding>(
                                R.layout.shop_recycle_item_good,1,viewModel.getDecorationCount(),
                                bindData = { position, binding ->
                                    binding?.let {
                                        it.viewModel = viewModel
                                        it.position = position
                                        it.type = ShopConfig.SHOP_GOOD_TYPE_DECORATION
                                        Glide.with(this@ShopFragment).load(viewModel.getGoodData(ShopConfig.SHOP_GOOD_TYPE_DECORATION,position).imgUrl)
                                                .error(R.drawable.shop_ic_shop_good)
                                                .into(it.shopItemIvDesc)
                                    }
                                },
                                onItemClick = { _, binding ->
                                    (activity as ShopActivity).let { it1 ->
                                        binding?.let {
                                            DetailActivity.activityStart(it1,
                                                    viewModel.getGoodData(it.type ?: ShopConfig.SHOP_GOOD_TYPE_DECORATION,it.position ?:0).id ,viewModel.stampCount.value ?:0, it.shopItemIvDesc)
                                        }
                                    }
                                }))
                        .addDataBinding(DataBindingAdapter.MyDataBinding<ShopRecycleItemTitleGoodBinding>(
                                R.layout.shop_recycle_item_title_good,2,1,
                                {
                                    _,binding ->
                                        binding?.title = "邮货"
                                }))
                        .addDataBinding(DataBindingAdapter.MyDataBinding<ShopRecycleItemGoodBinding>(
                                R.layout.shop_recycle_item_good,3,viewModel.getStampGoodCount(),
                                bindData = { position, binding ->
                                    binding?.let {
                                        it.viewModel = this.viewModel
                                        it.position = position
                                        it.type = ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD
                                        Glide.with(this@ShopFragment).load(viewModel.getGoodData(ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD, position).imgUrl)
                                                .error(R.drawable.shop_ic_shop_good)
                                                .into(it.shopItemIvDesc)
                                    }
                                },
                                onItemClick = { _, binding ->
                                    (activity as ShopActivity).let { it1 ->
                                        binding?.let {
                                            DetailActivity.activityStart(it1,
                                                    viewModel.getGoodData(it.type ?:ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD,it.position ?:0).id,viewModel.stampCount.value ?:0, it.shopItemIvDesc)
                                        }
                                    }
                                }))

                stampStartPosition = viewModel.getDecorationCount() + 1

                shop_shop_rv_goods.apply {

                    adapter = goodsRvAdapter

                    layoutManager = GridLayoutManager(this.context,2).also {it1 ->
                        it1.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int {
                                if (position == 0 || position == stampStartPosition){
                                    return 2
                                }
                                return 1
                            }
                        }
                    }

                    layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(context,R.anim.shop_loading_in_shop_rv))

                }
            }
        })
    }
}