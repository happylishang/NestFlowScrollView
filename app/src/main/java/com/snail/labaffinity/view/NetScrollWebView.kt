package com.snail.labaffinity.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.VelocityTracker
import android.webkit.WebView
import androidx.core.view.ViewCompat
import kotlin.math.abs

class NetScrollWebView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : WebView(context, attrs) {

    private val mTouchSlop = android.view.ViewConfiguration.get(context).scaledTouchSlop

    init {
        isNestedScrollingEnabled = true
        settings.javaScriptEnabled = true
    }

    private var mLastY: Float = 0f
    private var dragIng: Boolean = false
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_MOVE -> {
                if (abs(ev.rawY - mLastY) > mTouchSlop) {
                    dragIng = true
                } else {
                    super.dispatchTouchEvent(ev)
                }
                if (dragIng) {
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                    dispatchNestedPreScroll(
                        0, (mLastY - ev.rawY).toInt(), mScrollConsumed, null
                    )
                    mLastY = ev.rawY
                }
            }

            MotionEvent.ACTION_DOWN -> {
                dragIng = false
                super.dispatchTouchEvent(ev)
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)
                mLastY = ev.rawY
            }

            else -> super.dispatchTouchEvent(ev)
        }
        return true
    }

    // 强制自己不消费move
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (dragIng || ev?.action == MotionEvent.ACTION_MOVE)
            return false
        return super.onTouchEvent(ev)
    }

    private val mScrollConsumed = IntArray(2)
}