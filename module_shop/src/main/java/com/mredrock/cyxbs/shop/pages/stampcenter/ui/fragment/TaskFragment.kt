package com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.aefottt.module_shop.R
import com.aefottt.module_shop.databinding.ShopRecycleItemTaskBinding
import com.mredrock.cyxbs.common.ui.BaseFragment
import com.mredrock.cyxbs.common.ui.BaseViewModelFragment
import com.mredrock.cyxbs.shop.bean.MoreTask
import com.mredrock.cyxbs.shop.bean.TodayTask
import com.mredrock.cyxbs.shop.pages.stampcenter.adapter.ShopTaskAdapter
import com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.shop_fragment_task.*
import kotlin.random.Random

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
        val todayTask = TodayTask(Random(System.currentTimeMillis()).nextInt().toString(),1,false,true,10,"233",5)
        val todayList = MutableList(2){todayTask}
        viewModel._todayTaskData.value = todayList
        val moreTask = MoreTask(Random(System.currentTimeMillis()).nextInt().toString(),1,false,true,10,"233",5)
        val moreList = MutableList(2){moreTask}
        viewModel._moreTaskData.value = moreList
        initData()
        initRecycler()
    }
    private fun initData(){
//        viewModel.apply {
//            getTodayTaskData()
//            getMoreTaskData()
//        }
    }

    private fun initRecycler() {
        shop_task_rv_tasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ShopTaskAdapter(this@TaskFragment, viewModel)
        }
    }

}