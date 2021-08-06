package com.mredrock.cyxbs.shop.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ProgressBar(context: Context?,attrs: AttributeSet?) : View(context,attrs) {
    private var currentProgress = 0
    private var maxProgress = 0
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val r = height / 2f
        val rectWidth = (width - 2 * r) * currentProgress / maxProgress
        val mPaint = Paint()
        mPaint.color = Color.parseColor("#7D8AFF")
        mPaint.style = Paint.Style.FILL
        canvas?.apply {
            drawCircle(r,r,r,mPaint)
            drawRect(r,height * 1f,r + rectWidth,0f,mPaint)
            drawCircle(r + rectWidth,height / 2f,r,mPaint)
        }
    }

    fun setMaxProgress(mProgress: Int){
        maxProgress = mProgress
    }

    fun setCurrentProgress(cProgress: Int){
        currentProgress = cProgress
    }
}