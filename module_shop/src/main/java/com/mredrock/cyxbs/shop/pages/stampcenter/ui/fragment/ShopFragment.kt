package com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.recyclerview.widget.GridLayoutManager
import com.aefottt.module_shop.R
import com.aefottt.module_shop.databinding.ShopRecycleItemGoodBinding
import com.aefottt.module_shop.databinding.ShopRecycleItemTitleGoodBinding
import com.bumptech.glide.Glide
import com.mredrock.cyxbs.common.ui.BaseViewModelFragment
import com.mredrock.cyxbs.shop.adapter.DataBindingAdapter
import com.mredrock.cyxbs.shop.config.ShopConfig
import com.mredrock.cyxbs.shop.pages.detail.ui.DetailActivity
import com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel.ShopViewModel
import kotlinx.android.synthetic.main.shop_fragment_shop.*
import kotlinx.android.synthetic.main.shop_recycle_item_good.*

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
        iniData()
        initView()
    }

    private fun iniData(){
        viewModel.initData()
    }

    private fun initView(){

        goodsRvAdapter = DataBindingAdapter(viewLifecycleOwner,viewModel)
                .addDataBinding(DataBindingAdapter.MyDataBinding<ShopRecycleItemTitleGoodBinding>(
                        R.layout.shop_recycle_item_title_good,0,1,
                        bindData = { _, binding ->
                            binding?.title = "装饰"
                        }))
                .addDataBinding(DataBindingAdapter.MyDataBinding<ShopRecycleItemGoodBinding>(
                        R.layout.shop_recycle_item_good,1,viewModel.getDecorationCount(),
                        bindData = { position, binding ->
                            binding?.apply {
                                this.viewModel = viewModel
                                this.position = position - 1
                                this.type = ShopConfig.SHOP_GOOD_TYPE_DECORATION
                                Glide.with(this@ShopFragment).load(viewModel.getGoodData(type).value?.get(position - 1)?.imgUrl)
                                        .into(shop_item_iv_desc)
                            }
                        },
                        onItemClick = { _, binding ->
                            context?.let { it1 ->
                                binding?.let {
                                    DetailActivity.activityStart(it1, binding.shopItemTvTitle.text.toString(),
                                            ShopConfig.SHOP_GOOD_TYPE_DECORATION, binding.shopItemIvDesc)
                                }
                            }
                        }))
                .addDataBinding(DataBindingAdapter.MyDataBinding<ShopRecycleItemTitleGoodBinding>(
                        R.layout.shop_recycle_item_title_good,2,1,
                        bindData = { _, binding ->
                            binding?.title = "邮货"
                        }))
                .addDataBinding(DataBindingAdapter.MyDataBinding<ShopRecycleItemGoodBinding>(
                        R.layout.shop_recycle_item_good,3,viewModel.getStampGoodCount(),
                        bindData = {
                            position,binding -> binding?.apply {
                        this.viewModel = viewModel
                        this.position = position - viewModel.getDecorationCount() - 2
                        this.type = ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD
                        Glide.with(this@ShopFragment).load(viewModel.getGoodData(type).value?.get(position - viewModel.getDecorationCount() - 2)?.imgUrl)
                                .into(shop_item_iv_desc)
                            }
                        },
                        onItemClick = { _, binding ->
                            context?.let { it1 ->
                                binding?.let {
                                    DetailActivity.activityStart(it1, binding.shopItemTvTitle.text.toString(),
                                            ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD, binding.shopItemIvDesc)
                                }
                            }
                        }))

        stampStartPosition = viewModel.getDecorationCount() + 1

        shop_shop_rv_goods.apply {
            adapter = goodsRvAdapter

            layoutManager = GridLayoutManager(this.context,2).also {
                it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
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
}