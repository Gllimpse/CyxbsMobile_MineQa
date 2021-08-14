package com.mredrock.cyxbs.shop.pages.detail.adapter

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aefottt.module_shop.R
import com.github.chrisbanes.photoview.PhotoView
import com.mredrock.cyxbs.common.utils.extensions.setImageFromUrl

class BannerPagerAdapter(private val picUrls: ArrayList<String>?) :
    RecyclerView.Adapter<BannerPagerAdapter.BannerVpAdapter>() {

    // 单击图片的事件
    var photoTapClick: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerVpAdapter {
        return BannerVpAdapter(LayoutInflater.from(parent.context)
            .inflate(R.layout.shop_viewpager_item_image_pic, parent, false))
    }

    override fun onBindViewHolder(holder: BannerVpAdapter, position: Int) {
        val url = picUrls?.get(position)
        val pv : PhotoView = holder.itemView as PhotoView
        pv.apply {
            setImageFromUrl(url)
            setOnPhotoTapListener{ _, _, _ ->
                photoTapClick?.invoke()
            }
        }
    }

    override fun getItemCount() = picUrls?.size ?: 0

    class BannerVpAdapter(itemView: View) : RecyclerView.ViewHolder(itemView)
}