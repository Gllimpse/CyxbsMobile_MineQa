package com.mredrock.cyxbs.shop.pages.stampdetail.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aefottt.module_shop.R
import com.aefottt.module_shop.databinding.ShopRecycleItemDetailGoodBinding
import com.mredrock.cyxbs.common.ui.BaseViewModelFragment
import com.mredrock.cyxbs.shop.adapter.DataBindingAdapter
import com.mredrock.cyxbs.shop.pages.stampdetail.ui.activity.ExDetailActivity
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
        initView()
    }

    private fun initView(){
        viewModel.getExRecordData()
        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            shop_exchange_rv.apply {
                adapter = DataBindingAdapter(viewLifecycleOwner, viewModel)
                        .addDataBinding(DataBindingAdapter.MyDataBinding<ShopRecycleItemDetailGoodBinding>(
                                R.layout.shop_recycle_item_detail_good, 0, viewModel.exRecordDataSize(),
                                bindData = { position, dataBinding ->
                                    dataBinding?.let {
                                        it.viewModel = viewModel
                                        it.position = position
                                    }
                                },
                                onItemClick = { position, _ ->
                                    viewModel.getExRecordData(position)?.let {
                                        ExDetailActivity.activityStart(context, it)
                                    }
                                }))

                layoutManager = LinearLayoutManager(this.context)

                layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(context, R.anim.shop_loding_in_stamp_detail_rv))

            }})
    }
}