package com.mredrock.cyxbs.shop.pages.stampdetail.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.aefottt.module_shop.databinding.ShopRecycleItemDetailGoodBinding
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel
import com.mredrock.cyxbs.shop.pages.stampcenter.adapter.PrimaryDataBindingAdapter
import com.mredrock.cyxbs.shop.pages.stampdetail.viewmodel.ExRecordViewModel

class ExRecordAdapterPrimary(lifecycleOwner: LifecycleOwner, private val viewModel: BaseViewModel, @LayoutRes resId: Int)
    : PrimaryDataBindingAdapter<ShopRecycleItemDetailGoodBinding>(lifecycleOwner,viewModel,resId) {

    override fun createViewHolder(dataBinding: ShopRecycleItemDetailGoodBinding, viewType: Int, parent: ViewGroup): RecyclerView.ViewHolder {
        return ExRecordViewHolder(dataBinding)
    }

    override fun getDataType(position: Int): Int {
        return 0
    }

    override fun getItemCount(): Int {
        return (viewModel as ExRecordViewModel).exRecordDataSize()
    }

    inner class ExRecordViewHolder(private val dataBinding: ShopRecycleItemDetailGoodBinding) : DataBindingViewHolder(dataBinding) {
        override fun bindData(position: Int, viewModel: BaseViewModel, dataType: Int) {
            dataBinding.apply {
                this.viewModel = viewModel as ExRecordViewModel
                this.position = position
            }
        }
    }

}