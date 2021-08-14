package com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
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

    private fun initView() {

        taskAdapter = DataBindingAdapter(viewLifecycleOwner,viewModel)
                .addDataBinding(DataBindingAdapter.MyDataBinding<ShopRecycleItemTaskBinding>(
                        R.layout.shop_recycle_item_task,0,viewModel.getTodayTaskCount(),
                        bindData = { position,binding ->
                            binding?.apply {
                                this.viewModel = viewModel
                                this.position = position
                                this.type = ShopConfig.SHOP_TASK_TYPE_TODAY
                            }
                        }))
                .addDataBinding(DataBindingAdapter.MyDataBinding<ShopRecycleItemTitleTaskBinding>(
                        R.layout.shop_recycle_item_title_task,1,1))
                .addDataBinding(DataBindingAdapter.MyDataBinding<ShopRecycleItemTaskBinding>(
                        R.layout.shop_recycle_item_task,2,viewModel.getMoreTaskCount(),
                        bindData = { position,binding ->
                            binding?.apply {
                                this.viewModel = viewModel
                                this.position = position - viewModel.getTodayTaskCount() - 1
                                this.type = ShopConfig.SHOP_TASK_TYPE_TODAY
                            }
                        }))

        shop_task_rv_tasks.apply {

            adapter = taskAdapter

            layoutManager = LinearLayoutManager(context)

            layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(context,R.anim.shop_loading_in_shop_rv))
        }
    }

}