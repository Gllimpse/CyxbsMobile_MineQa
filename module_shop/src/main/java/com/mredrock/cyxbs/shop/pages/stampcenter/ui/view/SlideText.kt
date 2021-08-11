package com.mredrock.cyxbs.shop.pages.stampcenter.ui.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.mredrock.cyxbs.common.utils.extensions.sp

/**
 * @author Qt
 * @date 2021/8/10
 * You are universe.
**/
class SlideText(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var progress: Float = 0f
        set(value) {
            invalidate()
            field = value
        }

    /**
     * 是否绘制，在调用 @startSlide() 方法后才开始绘制
     */
    private var isDraw: Boolean = false

    /**
     * 保存字符数组
     */
    private lateinit var textArr: CharArray

    /**
     * 字符的平均宽度
     */
    private var textMaxWidth: Float = 0f

    /**
     * 字符高度
     */
    private var textHeight: Float = 0f

    /**
     * 数字间X的间隔
     */
    private val textGapX = 5
    /**
     * 数字间Y的间隔
     */
    private val textGapY = 5

    /**
     * 画笔
     */
    private val paint: Paint = Paint().apply {
        color = Color.WHITE
        textAlign = Paint.Align.LEFT
        textSize = context.sp(48).toFloat()
        typeface = Typeface.createFromAsset(context.assets, "fonts/shop_font_banner_number.ttf")
    }

    private val fm: Paint.FontMetrics = paint.fontMetrics

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var h = MeasureSpec.getSize(heightMeasureSpec)
        when(MeasureSpec.getMode(heightMeasureSpec)){
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> {
                textHeight = fm.descent - fm.ascent
                h = textHeight.toInt()
            }
            MeasureSpec.EXACTLY -> {

            }
        }
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!isDraw) return
        var x = 0f
        repeat(textArr.size) {
            val cEnd = Integer.valueOf(textArr[it].toString())
            if (cEnd in 0..4) {
                for (cCur in 0..cEnd) {
                    canvas.drawText(
                        cCur.toString(),
                        x,
                        textHeight * (cCur + 1) + getPivot(cCur, cEnd) - textGapY,
                        paint
                    )
                }
            } else if (cEnd in 5..9) {
                canvas.drawText("0", x, textHeight + textHeight * (10 - cEnd) * progress, paint)
                for (cCur in 9 downTo cEnd) {
                    canvas.drawText(
                        cCur.toString(),
                        x,
                        textHeight * (cCur - 9) + getPivot(cCur, cEnd) - textGapY,
                        paint
                    )
                }
            }
            x += textMaxWidth + textGapX
        }
    }

    /**
     * 计算当前数字所需的偏移量
     * @cCur 当前数字
     * @cEnd 最终数字
     */
    private fun getPivot(cCur: Int, cEnd: Int): Float {
        return if (cCur in 0..4) {
            -textHeight * cEnd * progress
        } else {
            textHeight * (10 - cEnd) * progress
        }
    }

    /**
     * 开始滑动数字
     */
    fun startSlide(duration: Long) {
        isDraw = true
        ObjectAnimator.ofFloat(this, "progress", 0f, 1.0f).apply {
            setDuration(duration)
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    /**
     * 对外暴露的方法，setText后开始绘制数字
     */
    fun setText(t: String) {
        // 获取字符串
        textArr = t.toCharArray()
        // 计算每个数字的宽度
        for (text in textArr) {
            val textWidth = paint.measureText(text.toString())
            if (textWidth > textMaxWidth) textMaxWidth = textWidth
        }
    }

}