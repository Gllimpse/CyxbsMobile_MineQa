package com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment

import android.annotation.SuppressLint
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
import com.aefottt.module_shop.databinding.ShopRecycleItemTaskBinding
import com.aefottt.module_shop.databinding.ShopRecycleItemTitleTaskBinding
import com.mredrock.cyxbs.common.ui.BaseViewModelFragment
import com.mredrock.cyxbs.shop.adapter.DataBindingAdapter
import com.mredrock.cyxbs.shop.config.ShopConfig
import com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel.ShopViewModel
import kotlinx.android.synthetic.main.shop_fragment_task.*

class TaskFragment: BaseViewModelFragment<ShopViewModel>() {
    private lateinit var taskAdapter : DataBindingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shop_fragment_task,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initData()
        initView()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initView() {

        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {isSuccess ->
            if (isSuccess) {
                taskAdapter = DataBindingAdapter(viewLifecycleOwner)
                        .addDataBinding(DataBindingAdapter.MyDataBinding<ShopRecycleItemTaskBinding>(
                                resId = R.layout.shop_recycle_item_task,
                                itemOrder = 0,
                                itemSize = viewModel.getTodayTaskCount(),
                                bindData = { position, binding ->
                                    binding?.let {
                                        it.task = viewModel.getTaskData(ShopConfig.SHOP_TASK_TYPE_TODAY,position)
                                        it.shopItemTaskTvGo.apply {
                                            if (viewModel.getTaskData(ShopConfig.SHOP_TASK_TYPE_TODAY,position).curProgress == viewModel.getTaskData(ShopConfig.SHOP_TASK_TYPE_TODAY,position).maxProgress){
                                                text = "已完成"
                                                background = context.getDrawable(R.drawable.shop_bg_recycle_item_btn_go_finished)
                                                isClickable = false
                                            }
                                        }
                                        it.shopItemTaskProgressbar.apply {
                                            post {
                                                setProgressCompat(viewModel.getTaskData(ShopConfig.SHOP_TASK_TYPE_TODAY,position).curProgress,true)
                                            }
                                            progress = viewModel.getTaskData(ShopConfig.SHOP_TASK_TYPE_TODAY,position).curProgress
                                        }
                                    }
                                }))
                        .addDataBinding(DataBindingAdapter.MyDataBinding<ShopRecycleItemTitleTaskBinding>(
                                resId = R.layout.shop_recycle_item_title_task,
                                itemOrder = 1,
                                itemSize = 1))
                        .addDataBinding(DataBindingAdapter.MyDataBinding<ShopRecycleItemTaskBinding>(
                                resId = R.layout.shop_recycle_item_task,
                                itemOrder = 2,
                                itemSize = viewModel.getMoreTaskCount(),
                                bindData = { position, binding ->
                                    binding?.let {
                                        val taskData = viewModel.getTaskData(ShopConfig.SHOP_TASK_TYPE_MORE,position)
                                        it.task = taskData
                                        it.shopItemTaskTvGo.apply {
                                            if (taskData.curProgress == taskData.maxProgress){
                                                text = "已完成"
                                                background = context.getDrawable(R.drawable.shop_bg_recycle_item_btn_go_finished)
                                                isClickable = false
                                            }
                                        }
                                        it.shopItemTaskProgressbar.apply {
                                            post {
                                                setProgressCompat(viewModel.getTaskData(ShopConfig.SHOP_TASK_TYPE_MORE,position).curProgress,true)
                                            }
                                        }
                                    }
                                }))
                shop_task_rv_tasks.apply {

                    adapter = taskAdapter

                    layoutManager = LinearLayoutManager(context)

                    layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(context, R.anim.shop_loading_in_shop_rv))
                }
            }})
    }
}