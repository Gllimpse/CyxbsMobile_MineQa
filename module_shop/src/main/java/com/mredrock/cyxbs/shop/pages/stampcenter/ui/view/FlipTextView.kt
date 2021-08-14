package com.mredrock.cyxbs.shop.pages.stampcenter.ui.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.mredrock.cyxbs.common.utils.extensions.sp

/**
 * @class FlipTextView
 * @author YYQF
 * @data 2021/8/13
 * @description 文字垂直滑动
 **/
class FlipTextView(context: Context, attributes: AttributeSet) : View(context,attributes) {
    /**
     * 数字画笔
     */
    private val mPaint = Paint().apply {
        color = Color.WHITE
        textAlign = Paint.Align.LEFT
        textSize = context.sp(48).toFloat()
        typeface = Typeface.createFromAsset(context.assets, "fonts/shop_font_banner_number.ttf")
    }

    /**
     * 动画持续时间，默认600ms
     */
    private var flipDuration = 600
    /**
     * FontMetrics
     */
    private val fm = mPaint.fontMetrics

    /**
     * 单个文字宽度/间距
     */
    private val textWidth = mPaint.measureText("0")

    /**
     * 上下相邻文字距离
     */
    private val textHeight = fm.descent - fm.ascent

//    /**
//     * 显示高度
//     */
//    private val showTextHeight = fm.bottom - fm.top
//
//    /**
//     * 文字总宽度/显示宽度
//     */
//    private val totalTextWidth = mPaint.measureText("000000")

    /**
     * 6位数字的当前偏移量
     */
    private val lastOffSet = Array(6){0f}

    /**
     * 6位数字的即将设置的偏移量
     */
    private val currOffSet = Array(6){0f}


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /**
         * 从上到下依次绘制数字0-9，相邻数字间距为descent - ascent，从左到右重复6次
         */
        repeat(6) {
            for (i in 0..9) {
                canvas.drawText(i.toString(),
                        it * textWidth,
                        height / 2 + (fm.bottom - fm.top) / 2 - fm.bottom + i * textHeight + currOffSet[it],
                        mPaint
                )
            }
        }
    }

    fun setCurrNum(currNum : Int){
        /**
         * 根据数字获取字符数组
         */
        val textArray = currNum.toString().toCharArray()
        repeat(6){

            /**
             * 偏移量为数字大小 * 文字高度 * -1
             */
            currOffSet[it] = (textArray[it].toString().toInt()) * -textHeight
            ValueAnimator.ofFloat(lastOffSet[it],currOffSet[it])
                    .apply {
                        addUpdateListener { _->
                            this@FlipTextView.currOffSet[it] = animatedValue as Float
                            invalidate()
                        }
                        duration = flipDuration.toLong()
                        interpolator = DecelerateInterpolator()
                        start()
                    }
            /**
             * 储存数字
             */
            lastOffSet[it] = currOffSet[it]
        }
    }

    fun setDuration(duration: Int){
        flipDuration = duration
    }
}