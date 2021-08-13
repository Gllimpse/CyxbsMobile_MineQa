package com.mredrock.cyxbs.shop.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.OverScroller
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.aefottt.module_shop.R
import com.google.android.material.tabs.TabLayout
import com.mredrock.cyxbs.shop.config.ShopConfig

class NestedContentBehavior(context: Context, attrs: AttributeSet):
    CoordinatorLayout.Behavior<View>(context, attrs) {
    // 头布局的高度
    private var headerHeight = 0
    // 内容View（这里对于NestedScrollView）
    private lateinit var contentView: View
    // 滑动计算器
    private var scroller : OverScroller? = null
    // 滑动监听
    private val scrollRunnable = object : Runnable{
        override fun run() {
            scroller?.let {
                if (it.computeScrollOffset()){ // 如果已经计算过滑动偏移值
                    contentView.translationY = it.currY.toFloat()
                    // 让View在下一次绘制时执行定义好的Runnable
                    ViewCompat.postOnAnimation(contentView, this)
                }

            }
        }
    }

    /**
     * 放置contentView的布局
     */
    override fun onLayoutChild(
        parent: CoordinatorLayout, child: View, layoutDirection: Int
    ): Boolean {
        contentView = child
        // 让父布局按照标准方式解析
        parent.onLayoutChild(child, layoutDirection)
        // 获取Banner的高度
        headerHeight = parent.findViewById<View>(R.id.shop_main_cl_banner).measuredHeight
        // 设置contentView的Top值，让其排在HeaderView的下面
        ViewCompat.offsetTopAndBottom(child, headerHeight)
        return true // 表示我们自己完成了解析
    }

    /**
     * 开始滑动前询问是否需要滑动
     */
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View,
        axes: Int, type: Int
    ): Boolean {
        // 只要垂直滑动才需要处理
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    /**
     * 在childView消费滑动距离前先让parentView消费
     */
    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int
    ) {
        // 此时contentView还未滑动，父布局先滑动
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        // 防止在自动滑动期间用户再次滑动产生卡顿现象
        stopAutoScroll()
        val arr: IntArray = intArrayOf(0, 0)
        child.getLocationOnScreen(arr)
//        Log.d("Tag","(NestedContentBehavior.kt:77)->${arr[0]} ${arr[1]} ${ShopConfig.SHOP_CHILD_TOP}")
        if (type == ViewCompat.TYPE_NON_TOUCH && arr[1] == ShopConfig.SHOP_CHILD_TOP){
            if (target is RecyclerView) target.stopScroll()
        }
        if (dy > 0){ // 只处理手指上滑
            val transY = child.translationY - dy
            if (transY >= -headerHeight){
                // 未贴顶或者刚好贴顶，父布局消费滑动距离，contentView上移
                consumed[1] = dy
                child.translationY = transY
            }else {
                // 滑动超出HeaderView高度，只消费距离顶部的剩余距离
                consumed[1] = headerHeight + child.translationY.toInt()
                child.translationY = -headerHeight.toFloat()
            }
        }
    }

    /**
     * 父布局消费完后，子布局对剩下未消费部分进行消费
     */
    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: View, target: View,
        dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int,
        type: Int, consumed: IntArray
    ) {
        super.onNestedScroll(
            coordinatorLayout, child, target,
            dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
            type, consumed
        )
        // 防止在自动滑动期间用户再次滑动产生卡顿现象
        stopAutoScroll()
        if (dyUnconsumed < 0){ // 只处理手指下滑
            val transY = child.translationY - dyUnconsumed
            if (transY <= 0){
                child.translationY = transY
            }else{
                child.translationY = 0f
            }
        }
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View, target: View, type: Int
    ) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type)
        if (child.translationY >= 0f || child.translationY <= -headerHeight){
            // 如果完全折叠或者完全展开
            return
        }
        stopAutoScroll()
        if (child.translationY <= -headerHeight * 0.5f){
            // 滑动距离超过一半，自动滑倒顶部
            startAutoScroll(child.translationY.toInt(), -headerHeight, 600)
        }else{
            // 滑动距离未超过一半，返回原位置
            startAutoScroll(child.translationY.toInt(), 0, 500)
        }
    }

    /**
     * 如果用户滑动到一半，需要自动滑动到顶部或者初始位置，增加用户体验
     */
    private fun startAutoScroll(current: Int, target: Int, duration: Int){
        if (scroller == null){
            scroller = OverScroller(contentView.context)
        }
        scroller?.let {
            if (it.isFinished){
                contentView.removeCallbacks(scrollRunnable)
                it.startScroll(0, current, 0, target - current, duration)
                ViewCompat.postOnAnimation(contentView, scrollRunnable)
            }
        }
    }

    /**
     * 停止自动滑动
     */
    private fun stopAutoScroll(){
        scroller?.let {
            if (!it.isFinished){
                it.abortAnimation()
                contentView.removeCallbacks(scrollRunnable)
            }
        }
    }

}