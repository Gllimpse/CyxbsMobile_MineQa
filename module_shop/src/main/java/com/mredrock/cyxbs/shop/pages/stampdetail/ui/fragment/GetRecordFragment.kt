package com.mredrock.cyxbs.shop.pages.stampdetail.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.common.ui.BaseViewModelFragment
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel
import com.mredrock.cyxbs.shop.pages.stampdetail.adapter.ExRecordAdapter
import com.mredrock.cyxbs.shop.pages.stampdetail.viewmodel.ExRecordViewModel
import kotlinx.android.synthetic.main.shop_fragment_exchange.*
import kotlinx.android.synthetic.main.shop_fragment_get.*

class GetRecordFragment : BaseViewModelFragment<ExRecordViewModel>(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.shop_fragment_get,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler(){
        shop_get_rv.apply {
            adapter = ExRecordAdapter(viewLifecycleOwner,viewModel, R.layout.shop_recycle_item_detail_get)
            layoutManager = LinearLayoutManager(this.context)
        }
    }
}