package com.mredrock.cyxbs.shop.pages.detail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.common.component.CommonDialogFragment
import com.mredrock.cyxbs.common.ui.BaseViewModelActivity
import com.mredrock.cyxbs.shop.GoodType
import com.mredrock.cyxbs.shop.bean.Decoration
import com.mredrock.cyxbs.shop.bean.StampGood
import com.mredrock.cyxbs.shop.pages.detail.viewmodel.DetailViewModel
import com.mredrock.cyxbs.shop.widget.ShopDialog
import kotlinx.android.synthetic.main.shop_activity_detail.*
import kotlinx.android.synthetic.main.shop_dialog_detail_exchange.view.*
import java.io.Serializable

class DetailActivity : BaseViewModelActivity<DetailViewModel>() {
    private lateinit var goodData : Serializable
    private lateinit var goodType: GoodType
    private lateinit var title: String

    companion object{
        const val DIALOG_TYPE_FIRST_SURE = 0
        const val DIALOG_TYPE_SUCCESS = 1
        const val DIALOG_TYPE_STAMP_SHORTAGE = 2
        const val DIALOG_TYPE_COUNT_SHORTAGE = 3
        fun activityStart(context: Context,title: String,type: GoodType){
            context.startActivity(Intent(context, DetailActivity::class.java)
                    .apply {
                        putExtra("title",title)
                        putExtra("type",type)
                    })
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goodType = intent.getSerializableExtra("type") as GoodType
        title = intent.getStringExtra("title")
        initGoodData()
        initView()
        initListener()
        initObserve()

    }

    private fun initGoodData() {
        if (goodType == GoodType.TYPE_DECORATION) {
            viewModel.getDecorationData(title)
        }else {
            viewModel.getStampGoodData(title)

        }
    }

    private fun initObserve(){
        viewModel.decorResp.observe(this, Observer {
            if (it.status) {
                goodData = it.decoration
            }
        })

        viewModel.stampGoodResp.observe(this, Observer {
            if (it.status) {
                goodData = it.stamp_good
            }
        })

        viewModel.exResp.observe(this, Observer {
            if (it.status) {
                showSureDialog(DIALOG_TYPE_SUCCESS)
                updateStampCount()
            }
        })
    }

    private fun initView(){
        if (goodData is Decoration){
            (goodData as Decoration).apply {
                shop_detail_tv_name.text = title
                shop_detail_tv_left_count.text = "库存量：${left_Count}"
                shop_detail_tv_title.text = "装饰详情"
                shop_detail_tv_valid_time.text = "有效期：${period}天"
                shop_detail_tv_description.text = desc
                shop_detail_tv_price.text = "price"
            }
        }else if (goodData is StampGood){
            (goodData as StampGood).apply {
                shop_detail_tv_name.text = title
                shop_detail_tv_left_count.text = "库存量：${left_count}"
                shop_detail_tv_title.text = "邮货详情"
                shop_detail_tv_description.text = desc
            }
        }
        updateStampCount()
    }

    private fun initListener(){
        shop_detail_tv_exchange.setOnClickListener {
            showSureDialog(DIALOG_TYPE_FIRST_SURE)
        }
    }

    private fun showSureDialog(dialogType: Int) {
        var content = ""
        var onDeny : (() -> Unit)? = {}
        var onPositive : (() -> Unit)? = {}
        content = when(dialogType) {
            DIALOG_TYPE_FIRST_SURE -> if (goodType == GoodType.TYPE_DECORATION) {
                "确认要用${(goodData as Decoration).price}邮票兑换PM名片吗"
            } else {
                "确认要用${(goodData as StampGood).price}邮票兑换PM名片吗"
            }
            DIALOG_TYPE_COUNT_SHORTAGE -> "啊哦！手慢了！下次再来吧!"
            DIALOG_TYPE_STAMP_SHORTAGE -> "邮票数量不足！"
            DIALOG_TYPE_SUCCESS ->  "兑换成功"
            else -> ""
        }
        if (dialogType == DIALOG_TYPE_FIRST_SURE) {
            onPositive = {
                viewModel.apply {
                    if (goodType == GoodType.TYPE_DECORATION) {
                        (goodData as Decoration).apply {
                            when {
                                left_Count -> showSureDialog(DIALOG_TYPE_COUNT_SHORTAGE)
                                price > -> showSureDialog(DIALOG_TYPE_STAMP_SHORTAGE)
                                else -> exGood((goodData as Decoration).title)
                            }
                        }
                    } else {
                        (goodData as StampGood).apply {
                            when {
                                left_count -> showSureDialog(DIALOG_TYPE_COUNT_SHORTAGE)
                                price > -> showSureDialog(DIALOG_TYPE_STAMP_SHORTAGE)
                                else -> exGood((goodData as StampGood).title)
                            }
                        }
                    }
                }
            }
        }
        ShopDialog.show(this,content, onDeny, onPositive)

    }
}