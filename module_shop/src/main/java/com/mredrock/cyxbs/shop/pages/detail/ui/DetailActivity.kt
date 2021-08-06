package com.mredrock.cyxbs.shop.pages.detail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.api.account.IAccountService
import com.mredrock.cyxbs.common.component.CommonDialogFragment
import com.mredrock.cyxbs.common.service.ServiceManager
import com.mredrock.cyxbs.common.ui.BaseViewModelActivity
import com.mredrock.cyxbs.shop.GoodType
import com.mredrock.cyxbs.shop.bean.Decoration
import com.mredrock.cyxbs.shop.bean.StampGood
import com.mredrock.cyxbs.shop.config.ShopConfig
import com.mredrock.cyxbs.shop.pages.detail.viewmodel.DetailViewModel
import com.mredrock.cyxbs.shop.widget.ShopDialog
import kotlinx.android.synthetic.main.shop_activity_detail.*
import kotlinx.android.synthetic.main.shop_dialog_detail_exchange.view.*
import java.io.Serializable

class DetailActivity : BaseViewModelActivity<DetailViewModel>() {
    //所选商品
    private lateinit var goodData : Serializable
    //所选商品类型
    private var goodType = 233
    //所选商品名称
    private lateinit var title: String

    companion object{
        //Dialog类型
        const val DIALOG_TYPE_FIRST_SURE = 0
        const val DIALOG_TYPE_SUCCESS = 1
        const val DIALOG_TYPE_STAMP_SHORTAGE = 2
        const val DIALOG_TYPE_COUNT_SHORTAGE = 3
        fun activityStart(context: Context,title: String,type: Int){
            context.startActivity(Intent(context, DetailActivity::class.java)
                    .apply {
                        putExtra("title",title)
                        putExtra("type",type)
                    })
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_activity_detail)
        goodType = intent.getIntExtra("type",233)
        title = intent.getStringExtra("title")
        initGoodData()
        initView()
        initListener()
        initObserve()

    }

    private fun initGoodData() {
        goodData = Decoration("title",15,"带上这个名片，你就是这条街最亮的仔带上这个名片，你就是这条街最亮的仔",233,666, MutableList(2){""})
//        if (goodType == GoodType.TYPE_DECORATION) {
//            viewModel.getDecorationData(title)
//        }else {
//            viewModel.getStampGoodData(title)
//
//        }
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
            }else if (it.data == "物品存货不够"){
                showSureDialog(DIALOG_TYPE_COUNT_SHORTAGE)
            }
        })
    }

    private fun updateStampCount(): Int{
        ServiceManager.getService(IAccountService::class.java)
                .getUserService().getIntegral().apply {
                    shop_detail_tv_stamp_count.text = toString()
                    return this
                }
    }

    private fun initView(){
        if (goodData is Decoration){
            (goodData as Decoration).apply {
                shop_detail_tv_name.text = title
                shop_detail_tv_left_count.text = "库存量：${left_Count}"
                shop_detail_tv_title.text = "装饰详情"
                shop_detail_tv_valid_time.text = "有效期：${period}天"
                shop_detail_tv_description.text = desc
                shop_detail_tv_price.text = price.toString()
            }
        }else if (goodData is StampGood){
            (goodData as StampGood).apply {
                shop_detail_tv_name.text = title
                shop_detail_tv_left_count.text = "库存量：${left_count}"
                shop_detail_tv_title.text = "邮货详情"
                shop_detail_tv_description.text = desc
                shop_detail_tv_price.text = price.toString()

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
            DIALOG_TYPE_FIRST_SURE -> if (goodType == ShopConfig.GOOD_TYPE_DECORATION) {
                "确认要用${(goodData as Decoration).price}邮票兑换${(goodData as Decoration).title}吗"
            } else {
                "确认要用${(goodData as StampGood).price}邮票兑换${(goodData as Decoration).title}吗"
            }
            DIALOG_TYPE_COUNT_SHORTAGE -> "啊哦！手慢了！下次再来吧!"
            DIALOG_TYPE_STAMP_SHORTAGE -> "邮票数量不足！"
            DIALOG_TYPE_SUCCESS ->  "兑换成功"
            else -> ""
        }
        if (dialogType == DIALOG_TYPE_FIRST_SURE) {
            onPositive = {
                viewModel.apply {
                    if (goodType == ShopConfig.GOOD_TYPE_DECORATION) {
                        (goodData as Decoration).apply {
                            when {
                                price > updateStampCount()-> showSureDialog(DIALOG_TYPE_STAMP_SHORTAGE)
                                else -> exGood((goodData as Decoration).title)
                            }
                        }
                    } else {
                        (goodData as StampGood).apply {
                            when {
                                price > updateStampCount()-> showSureDialog(DIALOG_TYPE_STAMP_SHORTAGE)
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