package com.mredrock.cyxbs.shop.pages.stampdetail.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aefottt.module_shop.R
import com.aefottt.module_shop.databinding.ShopRecycleItemDetailGetBinding
import com.mredrock.cyxbs.common.ui.BaseViewModelFragment
import com.mredrock.cyxbs.shop.adapter.DataBindingAdapter
import com.mredrock.cyxbs.shop.pages.stampdetail.viewmodel.GetRecordViewModel
import kotlinx.android.synthetic.main.shop_fragment_get.*

/**
 * 邮票明细 ——获取记录
 */
class GetRecordFragment : BaseViewModelFragment<GetRecordViewModel>(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.shop_fragment_get,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initData()
        initRecycler()
    }

    private fun initRecycler() {
        shop_get_rv.apply {
            adapter = DataBindingAdapter(viewLifecycleOwner, viewModel)
                    .addDataBinding(DataBindingAdapter.MyDataBinding<ShopRecycleItemDetailGetBinding>(
                            R.layout.shop_recycle_item_detail_get, 0,viewModel.getRecordDataSize(),
                    bindData = {
                        position, dataBinding -> dataBinding?.apply {
                            this.viewModel = viewModel
                            this.position = position
                        }
                    }))

            layoutManager = LinearLayoutManager(this.context)

            layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(context,R.anim.shop_loding_in_stamp_detail_rv))
        }
    }
}