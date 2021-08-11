package com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.common.ui.BaseFragment
import com.mredrock.cyxbs.common.ui.BaseViewModelFragment
import com.mredrock.cyxbs.shop.bean.Decoration
import com.mredrock.cyxbs.shop.bean.StampGood
import com.mredrock.cyxbs.shop.pages.stampcenter.adapter.ShopGoodAdapter
import com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel.ShopViewModel
import kotlinx.android.synthetic.main.shop_fragment_shop.*
import okhttp3.internal.wait

class ShopFragment: BaseViewModelFragment<ShopViewModel>() {
    private lateinit var goodsRvAdapter: ShopGoodAdapter
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
        initRecycler()
        initObserve()
    }

    private fun initRecycler(){
        goodsRvAdapter = ShopGoodAdapter(context)
        val fakeDecoration = Decoration("title",15,"描述",233,666, MutableList(2){""})
        val fakeStampGood = StampGood("title",MutableList(2){""},15,233,"描述")
        val decorationData = MutableList(20){fakeDecoration}
        val stampGoodData = MutableList(20){fakeStampGood}
        stampStartPosition = stampGoodData.size
//        viewModel.apply {
//            getAllDecoration()
//            getAllStampGood()
//        }
        shop_shop_rv_goods.apply {
            adapter = goodsRvAdapter.apply {
                setDecorationData(decorationData)
                setStampGoodData(stampGoodData)
            }
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