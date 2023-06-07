package com.snail.labaffinity.view

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.widget.ScrollView
import androidx.core.view.NestedScrollingChild3
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat

/**如何自定义NestScroll的自View
 * Touch处理仍旧无法省略，必须计算滚动距离，dispatchTouchEvent或者onTouch都行，dispatchTouchEvent好一些
 * GestureDetector用于传递fling
 * NestedScrollingChildHelper用于处理拖动传递
 * 不过好处是跟父控件都可以省略intercept之类的逻辑
 * ACTION_MOVE
 * */
class MyNestScrollScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ScrollView(context, attrs, defStyleAttr, defStyleRes), NestedScrollingChild3 {
    private val chileNestHelper: NestedScrollingChildHelper = NestedScrollingChildHelper(this)

    init {
        chileNestHelper.isNestedScrollingEnabled = true
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        return chileNestHelper.startNestedScroll(axes, type)
    }


    var mLastY: Float = 0f
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        super.dispatchTouchEvent(ev)
        ev?.let { gestureDetector.onTouchEvent(it) }
        when (ev?.action) {
            MotionEvent.ACTION_MOVE -> {
                dispatchNestedPreScroll(
                    0, (mLastY - ev?.y!!).toInt(), mScrollConsumed, null,
                    ViewCompat.TYPE_TOUCH
                )
                mLastY = ev?.y!!
            }

            MotionEvent.ACTION_DOWN -> {
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_TOUCH)
                mLastY = ev?.y!!
            }


        }
        return true
    }

    //    自己不消费
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
    private val gestureDetector: GestureDetector =
        GestureDetector(context, object : SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                return chileNestHelper.dispatchNestedPreFling(velocityX, -velocityY)
            }
        })



    private val mScrollConsumed = IntArray(2)
    override fun stopNestedScroll(type: Int) {
        chileNestHelper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        return chileNestHelper.hasNestedScrollingParent(type)
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int,
        consumed: IntArray,
    ) {
        chileNestHelper.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type,
            consumed
        )
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int,
    ): Boolean {
        return chileNestHelper.dispatchNestedScroll(
            dxConsumed,
            dxConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int,
    ): Boolean {
        return chileNestHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }
}