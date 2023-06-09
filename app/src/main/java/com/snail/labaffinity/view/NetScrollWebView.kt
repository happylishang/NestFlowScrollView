package com.snail.labaffinity.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.webkit.WebView
import androidx.core.view.NestedScrollingChild3
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat

class NetScrollWebView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : WebView(context, attrs), NestedScrollingChild3 {

    private lateinit var mVelocityTracker: VelocityTracker
    private val chileNestHelper: NestedScrollingChildHelper = NestedScrollingChildHelper(this)
    private val mTouchSlop = android.view.ViewConfiguration.get(context).scaledTouchSlop

    init {
        chileNestHelper.isNestedScrollingEnabled = true
        settings.javaScriptEnabled = true
        mVelocityTracker = VelocityTracker.obtain()
        mVelocityTracker.clear()

    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        return chileNestHelper.startNestedScroll(axes, type)
    }


    private var mLastY: Float = 0f
    private var dragIng: Boolean = false
    private var pointId: Int = 0
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        super.dispatchTouchEvent(ev)
        when (ev?.action) {
            MotionEvent.ACTION_MOVE -> {

                if (Math.abs(ev.rawY - mLastY) > mTouchSlop) {
                    dragIng = true
                }
                if (dragIng) {
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                    dispatchNestedPreScroll(
                        0, (mLastY - ev.rawY).toInt(), mScrollConsumed, null,
                        ViewCompat.TYPE_TOUCH
                    )
//                    Log.v("lishang", "po " + (mLastY - ev.rawY))
                    mLastY = ev.rawY
                }

            }

            MotionEvent.ACTION_DOWN -> {
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_TOUCH)
                mLastY = ev.rawY
                dragIng = false
                pointId = ev.getPointerId(0)
                mVelocityTracker.clear()

            }

        }
        mVelocityTracker.addMovement(ev)

        return true
    }

    //    强制自己不消费
    override fun onTouchEvent(ev: MotionEvent?): Boolean {

        if (dragIng || ev?.action == MotionEvent.ACTION_MOVE)
            return false

        return super.onTouchEvent(ev)
    }



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