package com.snail.labaffinity.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.webkit.WebView
import androidx.core.view.NestedScrollingChild3
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat

class NetScrollWebView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : WebView(context, attrs), NestedScrollingChild3 {

    private val chileNestHelper: NestedScrollingChildHelper = NestedScrollingChildHelper(this)

    init {
        chileNestHelper.isNestedScrollingEnabled = true
        settings.javaScriptEnabled = true
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
                    0, (mLastY - ev.rawY).toInt(), mScrollConsumed, null,
                    ViewCompat.TYPE_TOUCH
                )
                Log.v("lishang" ,"po "+(mLastY- ev.y!!))
                mLastY = ev.rawY

            }
            MotionEvent.ACTION_DOWN -> {
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_TOUCH)
                mLastY = ev.rawY
            }


        }
        return true
    }

    //    强制自己不消费
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    private val gestureDetector: GestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float,
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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}