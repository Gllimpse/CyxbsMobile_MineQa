package com.mredrock.cyxbs.shop.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.aefottt.module_shop.R

class NestedHeaderBehavior constructor(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<View>(context, attrs){

    /**
     * 设置HeaderView的依赖布局
     */
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency.id == R.id.shop_main_ll_bottom
    }

    /**
     * 但依赖布局发生变化时会调用该函数
     */
    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        child.translationY = dependency.translationY * 0.7f
//        val value = 1 + dependency.translationY / (child.height * 0.6f)
//        child.scaleX = value
//        child.scaleY = value
        child.alpha = 1 + dependency.translationY / (child.height * 0.9f)
        parent.rootView.findViewById<View>(R.id.shop_main_ll_my_stamps)
            .alpha = -dependency.translationY / (child.height * 1.2f)
        return true
    }
}