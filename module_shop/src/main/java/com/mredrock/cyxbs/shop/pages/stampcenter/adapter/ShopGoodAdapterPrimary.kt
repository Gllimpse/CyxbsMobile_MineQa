package com.mredrock.cyxbs.shop.pages.stampcenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.common.utils.extensions.setOnSingleClickListener
import com.mredrock.cyxbs.shop.bean.Decoration
import com.mredrock.cyxbs.shop.bean.StampGood
import com.mredrock.cyxbs.shop.config.ShopConfig
import com.mredrock.cyxbs.shop.pages.detail.ui.DetailActivity
import kotlinx.android.synthetic.main.shop_recycle_item_good.view.*
import kotlinx.android.synthetic.main.shop_recycle_item_title_good.view.*
import java.io.Serializable

class ShopGoodAdapterPrimary(private val c: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var decorationList: List<Decoration>
    private lateinit var stampGoodList: List<StampGood>
    fun setDecorationData(decorationList: List<Decoration>){
        this.decorationList = decorationList
    }


    fun setStampGoodData(stampGoodList: List<StampGood>){
        this.stampGoodList = stampGoodList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0 || viewType == decorationList.size) {
            TitleHolder(parent)
        }else GoodHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.shop_recycle_item_good, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0){
            (holder as TitleHolder).bindData("装饰")
        } else if (position > 0 && position < decorationList.size){
            val goodHolder = holder as GoodHolder
            goodHolder.title.text = decorationList[position].title
            goodHolder.leftCount.text = "库存量："+decorationList[position].left_Count.toString()
            goodHolder.price.text = decorationList[position].price.toString()
            goodHolder.itemView.apply {
                setOnSingleClickListener {
                    DetailActivity.activityStart(c?:context, shop_item_tv_title.text.toString(),
                        ShopConfig.SHOP_GOOD_TYPE_DECORATION, goodHolder.pic)
                }
            }
        } else if (position == decorationList.size){
            (holder as TitleHolder).bindData("邮货")
        } else {
            val goodHolder = holder as GoodHolder
            goodHolder.title.text = stampGoodList[position - decorationList.size].title
            goodHolder.leftCount.text = "库存量："+stampGoodList[position - decorationList.size].left_count.toString()
            goodHolder.price.text = stampGoodList[position - decorationList.size].price.toString()
            goodHolder.itemView.apply {
                setOnSingleClickListener {
                    DetailActivity.activityStart(c?:context,shop_item_tv_title.text.toString(),
                        ShopConfig.SHOP_GOOD_TYPE_STAMP_GOOD, goodHolder.pic)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return decorationList.size + stampGoodList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class GoodHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pic: ImageView = itemView.findViewById(R.id.shop_item_iv_desc)
        val title: TextView = itemView.findViewById(R.id.shop_item_tv_title)
        val leftCount: TextView = itemView.findViewById(R.id.shop_item_tv_left_count)
        val price: TextView = itemView.findViewById(R.id.shop_item_tv_price)
    }

    class TitleHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.shop_recycle_item_title_good,parent,false))
    {
        fun bindData(title: String) {
            itemView.shop_item_title_title.text = title
        }
    }
}