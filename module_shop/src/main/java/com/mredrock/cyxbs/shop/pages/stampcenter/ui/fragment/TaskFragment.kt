package com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.common.ui.BaseViewModelFragment
import com.mredrock.cyxbs.shop.pages.stampcenter.deprecated.ShopTaskAdapterPrimary
import com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.shop_fragment_task.*

class TaskFragment: BaseViewModelFragment<TaskViewModel>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shop_fragment_task,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
    }
    private fun initData(){
        viewModel.initData()
    }

    private fun initView() {
        shop_task_rv_tasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ShopTaskAdapterPrimary(this@TaskFragment, viewModel,R.layout.shop_recycle_item_task)
            layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(context,R.anim.shop_loading_in_shop_rv))
        }
    }

}