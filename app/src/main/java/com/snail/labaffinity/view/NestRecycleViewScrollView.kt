package com.snail.labaffinity.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.ViewCompat

//垂直滚动的ScrollView嵌套recyvleview
//FrameLayout对于自定垂直的布局很方便
class NestRecycleViewScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes), NestedScrollingParent3 {

    val TAG = "NestRecycleViewScrollView"
    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        Log.v(TAG, "onStartNestedScroll " +(axes == ViewCompat.SCROLL_AXIS_VERTICAL))
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        Log.v(TAG, "onNestedScrollAccepted")
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        Log.v(TAG, "onStopNestedScroll")
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        Log.v(TAG, "onNestedScroll")
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        Log.v(TAG, "onNestedScroll")

    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        Log.v(TAG, "onNestedPreScroll")
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var top = t
        var bottom = b
        for (i in 0 until childCount) {
            getChildAt(i).layout(l, top, r, bottom)
            top += getChildAt(i).measuredHeight
            bottom += getChildAt(i).measuredHeight
        }
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return super.onNestedPreFling(target, velocityX, velocityY)
    }
}