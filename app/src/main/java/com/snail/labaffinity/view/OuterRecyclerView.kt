package com.snail.labaffinity.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import java.lang.RuntimeException

/**
 * @author 自己继承自己的典范，拦截后，多余的交给自己？有点类似于自己做自己的父布局
 **/
class OuterRecyclerView(context: Context, attributeSet: AttributeSet) :
    RecyclerView(context, attributeSet) {

    private val mParentScrollConsumed = IntArray(2)

    private var mCurrentFling = 0
    private val overScroller = OverScroller(context) {
        val t = it - 1.0f
        t * t * t * t * t + 1.0f
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.actionMasked == MotionEvent.ACTION_CANCEL) {
            Log.e("fang", "cancel")
        }

        if (ev?.actionMasked == MotionEvent.ACTION_DOWN) {
            if (!overScroller.isFinished) {
                overScroller.abortAnimation()
            }
        }
        val ns = super.dispatchTouchEvent(ev)
        Log.e("fang", "dispatchTouchEvent-->$ns")
        return ns
    }

    /**
     * if (actionMasked == MotionEvent.ACTION_DOWN
     * || mFirstTouchTarget != null) {
     * final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
     * if (!disallowIntercept) {
     *      intercepted = onInterceptTouchEvent(ev);
     *      ev.setAction(action); // restore action in case it was changed
     *   } else {
     *      intercepted = false;
     *   }
     * }
     *
     * onInterceptTouchEvent 执行的条件 actionMasked == MotionEvent.ACTION_DOWN 或者 mFirstTouchTarget != null
     * mFirstTouchTarget != null 的意思是 有child可以处理事件
     * */
    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(e)
    }

    /**
     * =======================================================
     * NestedScrollingChild2 start
     * =======================================================
     * */
    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        var consumedSelf = false
        if (type == ViewCompat.TYPE_TOUCH) {
            // up
            if (dy > 0) {
                //  已经到顶了
                if (!canScrollVertically(1)) {
                    val target = fetchBottomNestedScrollChild()
                    target?.apply {
                        this.scrollBy(0, dy)

                        consumed?.let {
                            it[1] = dy
                        }

                        consumedSelf = true
                    }
                }
            }
            // down 其实还是整体的parent控制，而不是底层控制
            if (dy < 0) {
                val target = fetchBottomNestedScrollChild()
                target?.apply {
                    if (this.canScrollVertically(-1)) {
                        this.scrollBy(0, dy)
                        //   消耗完，不给底层机会
                        consumed?.let {
                            it[1] = dy
                        }

                        consumedSelf = true
                    }
                }
            }
        }

        // Now let our nested parent consume the leftovers
        val parentScrollConsumed = mParentScrollConsumed
        val parentConsumed = super.dispatchNestedPreScroll(
            dx,
            dy - (consumed?.get(1) ?: 0),
            parentScrollConsumed,
            offsetInWindow,
            type
        )
        consumed?.let {
            consumed[1] += parentScrollConsumed[1]
        }
        return consumedSelf || parentConsumed
    }

    /**
     * fling 回调是一次性的 无法同时分发到两个View
     * 只能自己托管fling
     * */
    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        fling(velocityY)
        return true
    }
    /**
     * =======================================================
     * NestedScrollingChild2 end
     * =======================================================
     * */

    /**
     * 托管fling
     * 利用OverScroller
     * */
    private fun fling(velocityY: Float) {
        mCurrentFling = 0
        overScroller.fling(0, 0, 0, velocityY.toInt(), 0, 0, Int.MIN_VALUE, Int.MAX_VALUE)
        invalidate()
    }


    interface IBottomNestedScrollChild {
        fun getTargetScrollView(): View?
    }

    //  底部必须添加约束
    private fun fetchBottomNestedScrollChild(): View? {
        val view = getChildAt(childCount - 1)
        if (view is IBottomNestedScrollChild) {
            return (view as IBottomNestedScrollChild).getTargetScrollView()
        }else{
            throw RuntimeException("必须实现IBottomNestedScrollChild，并返回目标滚动View")
        }
        return null
    }

    override fun computeScroll() {
        if (overScroller.computeScrollOffset()) {
            val current = overScroller.currY
            val dy = current - mCurrentFling
            mCurrentFling = current

            val target = fetchBottomNestedScrollChild()
            if (dy > 0) {
                if (canScrollVertically(1)) {
                    scrollBy(0, dy)
                } else {
                    if (target?.canScrollVertically(1) == true) {
                        target.scrollBy(0, dy)
                    } else {
                        if (!overScroller.isFinished) {
                            overScroller.abortAnimation()
                        }
                    }
                }
            }
            if (dy < 0) {
                if (target?.canScrollVertically(-1) == true) {
                    target.scrollBy(0, dy)
                } else {
                    if (canScrollVertically(-1)) {
                        scrollBy(0, dy)
                    } else {
                        if (!overScroller.isFinished) {
                            overScroller.abortAnimation()
                        }
                    }
                }
            }
            invalidate()
        }
        super.computeScroll()
    }
}