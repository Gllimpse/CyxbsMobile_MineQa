package com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.common.ui.BaseViewModelFragment
import com.mredrock.cyxbs.shop.pages.stampcenter.deprecated.ShopGoodAdapterPrimary
import com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel.ShopViewModel
import kotlinx.android.synthetic.main.shop_fragment_shop.*

class ShopFragment: BaseViewModelFragment<ShopViewModel>() {
    //Adapter
    private lateinit var goodsRvAdapter: ShopGoodAdapterPrimary
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
        initObserve()
        initView()
    }

    private fun iniData(){
        viewModel.initData()
    }

    private fun initView(){

        goodsRvAdapter = ShopGoodAdapterPrimary(context)

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

            layoutParams = LinearLayout.LayoutParams(layoutParams).apply {
                marginStart = 30
            }

            layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(context,R.anim.shop_loading_in_shop_rv))

        }
    }

    private fun initObserve(){
        viewModel.allStampGoodData.observe(viewLifecycleOwner, Observer {
            if (it.status) {
                goodsRvAdapter.setStampGoodData(it.allStampGoodResp)
            }
        })

        viewModel.allDecorationData.observe(viewLifecycleOwner, Observer {
            if (it.status) {
                goodsRvAdapter.setDecorationData(it.allDecoration)
            }
            stampStartPosition = it.allDecoration.size
        })
    }

}