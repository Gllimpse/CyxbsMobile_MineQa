package com.mredrock.cyxbs.shop.pages.detail.adapter

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.aefottt.module_shop.R
import com.github.chrisbanes.photoview.PhotoView
import com.mredrock.cyxbs.common.utils.extensions.setImageFromUrl

class BannerPagerAdapter(private val picUrls: List<String>?) : PagerAdapter() {

    // 保存图片的点击事件
    var savePicClick: ((Bitmap, String) -> Unit)? = null
    // 单击图片的事件
    var photoTapClick: (() -> Unit)? = null

    override fun getCount() = picUrls?.size ?: 0

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.shop_viewpager_item_pic, container, false)
        val photoView = view.findViewById<PhotoView>(R.id.shop_detail_pv_banner)
        val url = picUrls?.get(position)
        photoView.setImageFromUrl(url)
        photoView.setOnLongClickListener {
            val drawable = photoView.drawable
            if (drawable is BitmapDrawable){
                val bitmap = (photoView.drawable as BitmapDrawable).bitmap
                if (!url.isNullOrEmpty()){
                    savePicClick?.invoke(bitmap, url)
                }
            }
            true
        }
        photoView.setOnPhotoTapListener { _, _, _ ->
            photoTapClick?.invoke()
        }
        return view
    }
}