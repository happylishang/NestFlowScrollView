package com.snail.labaffinity.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
import androidx.core.view.NestedScrollingChild3
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat

class MyScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ScrollView(context, attrs, defStyleAttr, defStyleRes), NestedScrollingChild3 {
    private val chileNestHelper: NestedScrollingChildHelper = NestedScrollingChildHelper(this)

    init {
        chileNestHelper.isNestedScrollingEnabled=true
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {

        return chileNestHelper.startNestedScroll(axes,type)
    }


    var mLastY: Float = 0f
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        super.dispatchTouchEvent(ev)
        when (ev?.action) {
            MotionEvent.ACTION_MOVE -> {
                dispatchNestedPreScroll(0, (mLastY-ev?.y!!  ).toInt(), mScrollConsumed, null,
                    ViewCompat.TYPE_NON_TOUCH)
                mLastY = ev?.y!!
            }
            MotionEvent.ACTION_DOWN -> {
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_NON_TOUCH)
                mLastY = ev?.y!!
            }

        }
        return true
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
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
        return chileNestHelper.dispatchNestedScroll(dxConsumed,dxConsumed,dxUnconsumed,dyUnconsumed,offsetInWindow,type)

    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int,
    ): Boolean {
        return chileNestHelper.dispatchNestedPreScroll(dx,dy,consumed,offsetInWindow,type)
    }
}