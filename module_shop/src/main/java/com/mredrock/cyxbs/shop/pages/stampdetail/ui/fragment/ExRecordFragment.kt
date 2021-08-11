package com.mredrock.cyxbs.shop.pages.stampdetail.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aefottt.module_shop.R
import com.aefottt.module_shop.databinding.ShopRecycleItemDetailGoodBinding
import com.aefottt.module_shop.databinding.ShopTestItemBinding
import com.mredrock.cyxbs.common.ui.BaseViewModelFragment
import com.mredrock.cyxbs.shop.pages.stampcenter.adapter.DataBindingAdapter
import com.mredrock.cyxbs.shop.pages.stampdetail.viewmodel.ExRecordViewModel
import kotlinx.android.synthetic.main.shop_fragment_exchange.*

/**
 * 邮票明细 —— 兑换记录
 */
class ExRecordFragment : BaseViewModelFragment<ExRecordViewModel>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.shop_fragment_exchange,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initRecycler()
    }

    private fun initData(){
        val testData = MutableList(10){"卷卷鼠标垫"}
        viewModel._ExRecordData.value = testData

    }

    private fun initRecycler(){
        shop_exchange_rv.apply {
//            adapter = ExRecordAdapter(viewLifecycleOwner,viewModel,R.layout.shop_recycle_item_detail_good)
            adapter = DataBindingAdapter(viewLifecycleOwner,viewModel)
                    .addDataBinding(DataBindingAdapter.MyDataBinding<ShopRecycleItemDetailGoodBinding>(
                            R.layout.shop_recycle_item_detail_good, 0,viewModel.exRecordDataSize()) { position, dataBinding, viewModel ->
                        dataBinding?.apply {
                                this.viewModel = viewModel as ExRecordViewModel
                                this.position = position
                        }
                    })
                    .addDataBinding(DataBindingAdapter.MyDataBinding<ShopTestItemBinding>(
                            R.layout.shop_test_item,1,viewModel.exRecordDataSize()){ position,dataBinding,viewModel->
                        dataBinding?.apply {
                            this.viewModel = viewModel as ExRecordViewModel
                            this.position = position
                        }
                    })

            layoutManager = LinearLayoutManager(this.context)
            layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(context,R.anim.shop_loding_in_stamp_detail_rv))
        }
    }
}