package com.mredrock.cyxbs.shop.pages.stampcenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.room.PrimaryKey
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel

 abstract class DataBindingAdapter <T: ViewDataBinding>(
         private val lifecycleOwner: LifecycleOwner,private val viewModel: BaseViewModel,@LayoutRes private val bindingLayoutId: Int) :
         RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    abstract inner class DataBindingViewHolder(dataBinding: T) : RecyclerView.ViewHolder(dataBinding.root){
        abstract fun bindData(position: Int,viewModel: BaseViewModel,@Nullable dataType: Int)
    }

     abstract inner class CommonViewHolder(parent: ViewGroup, @LayoutRes resId: Int) : RecyclerView.ViewHolder(
             LayoutInflater.from(parent.context).inflate(resId,parent,false)
     ){
         abstract fun bindData(position: Int,viewModel: BaseViewModel,@Nullable dataType: Int)
     }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
         val dataBinding = DataBindingUtil.inflate<T>(LayoutInflater.from(parent.context),bindingLayoutId,parent,false)
         dataBinding.lifecycleOwner = lifecycleOwner
         return createViewHolder(dataBinding,viewType,parent)
     }

     override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
         if (holder is DataBindingAdapter<*>.DataBindingViewHolder) {
             holder.bindData(position, viewModel, getDataType(position))
         }else if (holder is DataBindingAdapter<*>.CommonViewHolder){
             holder.bindData(position, viewModel, getDataType(position))
         }
     }

     abstract fun createViewHolder(dataBinding : T, viewType: Int,parent: ViewGroup) : RecyclerView.ViewHolder

     abstract fun getDataType(position: Int) : Int


 }