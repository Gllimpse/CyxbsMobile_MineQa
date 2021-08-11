package com.mredrock.cyxbs.shop.pages.detail.adapter

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aefottt.module_shop.R
import com.github.chrisbanes.photoview.PhotoView

class ImagePagerAdapter(private val picUrls: List<String>?) : RecyclerView.Adapter<ImagePagerAdapter.ImageVpHolder>() {

    // 保存图片的点击事件
    var savePicClick: ((Bitmap, String) -> Unit)? = null
    // 单击图片的事件
    var photoTapClick: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageVpHolder {
        return ImageVpHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.shop_viewpager_item_image_pic, parent, false))
    }

    override fun onBindViewHolder(holder: ImageVpHolder, position: Int) {
        val url = picUrls?.get(position)
        val pv : PhotoView = holder.itemView as PhotoView
        pv.apply {
//            setImageFromUrl(url)
            setImageResource(R.drawable.shop_ic_shop_good)
            setOnLongClickListener {
                val drawable = this.drawable
                if (drawable is BitmapDrawable){
                    val bitmap = (this.drawable as BitmapDrawable).bitmap
                    if (!url.isNullOrEmpty()){
                        savePicClick?.invoke(bitmap, url)
                    }
                }
                true
            }
            setOnPhotoTapListener{ _, _, _ ->
                photoTapClick?.invoke()
            }
        }
    }

    override fun getItemCount() = picUrls?.size ?: 0

    class ImageVpHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}