package com.mredrock.cyxbs.shop.pages.stampdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.aefottt.module_shop.R
import com.aefottt.module_shop.databinding.ShopRecycleItemDetailGoodBinding
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel
import com.mredrock.cyxbs.shop.pages.stampcenter.adapter.DataBindingAdapter
import com.mredrock.cyxbs.shop.pages.stampdetail.viewmodel.ExRecordViewModel

class ExRecordAdapter(lifecycleOwner: LifecycleOwner,private val viewModel: BaseViewModel,@LayoutRes resId: Int)
    : DataBindingAdapter<ShopRecycleItemDetailGoodBinding>(lifecycleOwner,viewModel,resId) {

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