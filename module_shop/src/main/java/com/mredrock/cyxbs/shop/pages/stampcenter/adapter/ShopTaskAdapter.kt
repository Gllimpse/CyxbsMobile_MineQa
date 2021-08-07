package com.mredrock.cyxbs.shop.pages.stampcenter.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aefottt.module_shop.R
import com.aefottt.module_shop.databinding.ShopRecycleItemTaskBinding
import com.mredrock.cyxbs.shop.config.ShopConfig
import com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment.TaskFragment
import com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel.TaskViewModel

class ShopTaskAdapter(val fragment: TaskFragment,private val viewModel : TaskViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ShopConfig.TASK_ITEM_TYPE_TASK) {
            val dataBinding = DataBindingUtil.inflate<ShopRecycleItemTaskBinding>(
                    LayoutInflater.from(parent.context), R.layout.shop_recycle_item_task, parent, false)
            dataBinding.lifecycleOwner = fragment
            TaskViewHolder(dataBinding)
        }else TitleViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(getItemViewType(position) == ShopConfig.TASK_ITEM_TYPE_TASK) {
                if (getDataType(position) == ShopConfig.TASK_TYPE_TODAY) {
                    (holder as TaskViewHolder).bindData(position, getDataType(position), viewModel)
                }else{
                    (holder as TaskViewHolder).bindData(position - viewModel.getTodayTaskSize() - 1, getDataType(position), viewModel)
                }
        }
    }

    override fun getItemCount(): Int {
        viewModel.apply {
            return getMoreTaskSize() + getTodayTaskSize() + 1
        }
    }

    private fun getDataType(position: Int): Int{
        Log.e("TaskAdapter", "today:"+viewModel.getTodayTaskData().toString()+" more:"+viewModel.getMoreTaskSize().toString())
        return if (position < viewModel.getTodayTaskSize()) ShopConfig.TASK_TYPE_TODAY
        else ShopConfig.TASK_TYPE_MORE
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == (viewModel.getTodayTaskSize())) ShopConfig.TASK_ITEM_TYPE_TITLE
        else ShopConfig.TASK_ITEM_TYPE_TASK
    }

    class TaskViewHolder(private val binding: ShopRecycleItemTaskBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindData(position: Int,type: Int,viewModel: TaskViewModel){
            binding.apply {
                this.viewModel = viewModel
                this.position = position
                this.type = type
            }
        }
    }

    class TitleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.shop_recycle_item_title_task,parent,false))
}