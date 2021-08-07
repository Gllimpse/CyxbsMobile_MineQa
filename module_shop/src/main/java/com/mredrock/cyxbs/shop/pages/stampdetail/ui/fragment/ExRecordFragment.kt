package com.mredrock.cyxbs.shop.pages.stampdetail.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.common.ui.BaseViewModelFragment
import com.mredrock.cyxbs.shop.pages.stampdetail.adapter.ExRecordAdapter
import com.mredrock.cyxbs.shop.pages.stampdetail.viewmodel.ExRecordViewModel
import kotlinx.android.synthetic.main.shop_fragment_exchange.*

class ExRecordFragment : BaseViewModelFragment<ExRecordViewModel>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.shop_fragment_exchange,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initRecycler()
    }

    private fun initData(){
        val testData = MutableList(10){"卷卷鼠标垫"}
        viewModel._ExRecordData.value = testData
    }

    private fun initRecycler(){
        shop_exchange_rv.apply {
            adapter = ExRecordAdapter(viewLifecycleOwner,viewModel,R.layout.shop_recycle_item_detail_good)
            layoutManager = LinearLayoutManager(this.context)
        }
    }
}