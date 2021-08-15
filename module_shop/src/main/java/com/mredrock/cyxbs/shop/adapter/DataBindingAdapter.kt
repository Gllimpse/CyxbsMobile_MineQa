package com.mredrock.cyxbs.shop.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.lang.IndexOutOfBoundsException
import java.lang.NullPointerException

/**
 * 基于RecyclerView多ViewHolder需求且实现起来很麻烦的现状和现有架构统一使用了DataBinding的情况，封装了一个结合DataBinding，viewModel/LiveData的Adapter
 * 简化多ViewHolder场景Adapter的使用
 */
class DataBindingAdapter (private val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<DataBindingAdapter.DataBindingViewHolder>(){

    /**
     * <Item展示顺序 —— MyDataBinding对象>哈希表
     */
     private val orderBindingMap = HashMap<Int, UpClass>()

    /**
     * <Item展示顺序 —— 该种Item数量>哈希表
     */
     private val orderSizeMap = HashMap<Int,Int>()

    /**
     * Item种类数
     */
     var maxOrder = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        /**
         * 根据viewType获取对应对象来创建dataBinding实例和viewHolder，设置点击事件
         */
        val binding = orderBindingMap[viewType]
        val dataBinding = binding?.createDataBinding(parent,viewType).apply {
//                this?.lifecycleOwner = lifecycleOwner
        }
        val holder = DataBindingViewHolder(binding,dataBinding)
        holder.itemView.setOnClickListener {
            binding?.onItemClick(getIndex(holder.layoutPosition),dataBinding)
        }
        return holder
    }


    /**
     * 根据position获取当前order对应数据源的索引值
     */
    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        holder.bindData(getIndex(position))
    }

    private fun getIndex(position: Int): Int{
        var sum = 0
        var index  = 0
        for (order in 0 until maxOrder) {
            if (position < sum + (orderSizeMap[order]
                            ?: throw Exception("can not find key in the orderSizeMap"))) {
                index = position - sum
                break
            } else sum += (orderSizeMap[order]
                    ?: throw Exception("can not find key in the orderSizeMap"))
        }
        return index
    }

    /**
     * 根据position获取该position的item类型order，viewType对应order，在onCreateViewHolder通过viewType获取orderBindingMap的对应子类，
     */
    override fun getItemViewType(position: Int): Int {
        var sum = 0
        for (order in 0 until maxOrder){
            if (position < sum + (orderSizeMap[order] ?: throw Exception("can not find key in the orderSizeMap"))){
                return order
            }else sum += orderSizeMap[order] ?:0
        }
        throw IndexOutOfBoundsException("getItemViewType")
    }

    override fun getItemCount(): Int {
        var sum =0
        for (order in 0 until maxOrder){
            Log.e("demo2",order.toString())
            sum += orderSizeMap[order] ?: throw Exception("can not find key in the orderSizeMap")
        }
        return sum
    }

    class DataBindingViewHolder(private val inner: UpClass?, private val dataBinding: ViewDataBinding?)
        : RecyclerView.ViewHolder(dataBinding?.root ?: throw NullPointerException("dataBinding is null !")) {
        fun bindData(position: Int){
            inner?.bindFunc(position,dataBinding)
        }
    }

    /**
     * 因为要使用多种Item，对应就要使用多个DataBinding，而通过DataBindingUtil创建具体的dataBinding需要对应的泛型。
     * 首先要知道，一个对象能且只能储存一个泛型，一个泛型的作用域也只在这个对象中。因为我们只会创建一个adapter对象，
     * 如果在Adapter类处声明泛型，只能储存一个泛型，这不满足我们的需求，所以不可以在Adapter类处直接声明泛型。
     * 因此我们需要新建一个类，在实例化这个类的对象时指定泛型，因为作用域，只能在类中指定所有与该泛型相关的操作（结果是把
     * adapter的大部分操作转移到Activity/Fragment中，可能有违DataBinding给view层解耦的初衷，需要优化）
     * 比如onCreateViewHolder/onBindViewHolder中的操作，操作具体控件,点击事件等。这些对象就储存在orderBinding哈希表中，
     */
    class MyDataBinding<T : ViewDataBinding>(@androidx.annotation.LayoutRes private val resId: Int,
                                             itemOrder: Int, val itemSize: Int,
                                             private val bindData: ((Int, T?) -> Unit)? = null,
                                             private val onItemClick: ((Int,T?) -> Unit)? = null) : UpClass() {

        override val order = itemOrder
        override fun bindFunc(position: Int, dataBinding: ViewDataBinding?) =
                bindData?.invoke(position, dataBinding as T?) ?: Unit
        override fun createDataBinding(parent: ViewGroup,viewType: Int): T =
                DataBindingUtil.inflate(LayoutInflater.from(parent.context),resId,parent,false)
        override fun onItemClick(position: Int,dataBinding: ViewDataBinding?) = onItemClick?.invoke(position, dataBinding as T?) ?: Unit
    }

    /**
     * 因为实例化哈希表时要指明类型和泛型，但是我们的对象储存了多个泛型，不能指明单个泛型，声明为ViewDataBinding就会导致泛型擦除，也不可以。
     * 因此我们给我们对象所在的类创建一个父类，将这个父类作为哈希表声明的类型（对象存储的类型）就能避开泛型擦除。
     */
    abstract class UpClass{
        abstract val order: Int
        abstract fun bindFunc(position: Int,dataBinding: ViewDataBinding?)
        abstract fun createDataBinding(parent: ViewGroup,viewType: Int): ViewDataBinding
        abstract fun onItemClick(position: Int,dataBinding: ViewDataBinding?)

    }

    /**
     * 增加一种Item只需要实例化一个MyDataBinding对象，然后调用该方法添加，该方法返回this，实现简单的链式调用
     */
    fun addDataBinding(binding: UpClass): DataBindingAdapter {
        orderBindingMap[binding.order] = binding
        orderSizeMap[binding.order] = (binding as MyDataBinding<*>).itemSize
        maxOrder++
        return this
    }

}