package com.mredrock.cyxbs.shop.pages.stampdetail.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.aefottt.module_shop.databinding.ShopRecycleItemDetailGetBinding
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel
import com.mredrock.cyxbs.shop.pages.stampcenter.adapter.PrimaryDataBindingAdapter
import com.mredrock.cyxbs.shop.pages.stampdetail.viewmodel.GetRecordViewModel

class GetRecordAdapterPrimary(lifecycleOwner: LifecycleOwner, private val viewModel: BaseViewModel, @LayoutRes resId: Int)
    : PrimaryDataBindingAdapter<ShopRecycleItemDetailGetBinding>(lifecycleOwner,viewModel,resId) {

    override fun createViewHolder(dataBinding: ShopRecycleItemDetailGetBinding, viewType: Int, parent: ViewGroup): RecyclerView.ViewHolder {
        return ExRecordViewHolder(dataBinding)
    }

    override fun getDataType(position: Int): Int {
        return 0
    }

    override fun getItemCount(): Int {
        return (viewModel as GetRecordViewModel).getRecordDataSize()
    }

    inner class ExRecordViewHolder(private val dataBinding: ShopRecycleItemDetailGetBinding) : DataBindingViewHolder(dataBinding) {
        override fun bindData(position: Int, viewModel: BaseViewModel, dataType: Int) {
            dataBinding.apply {
                this.viewModel = viewModel as GetRecordViewModel
                this.position = position
            }
        }
    }

}