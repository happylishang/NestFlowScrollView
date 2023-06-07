package com.snail.labaffinity.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.OverScroller
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat

//垂直滚动的ScrollView嵌套recyvleview
//FrameLayout对于自定垂直的布局很方便
//自定义父类其实也要拦截底部的，不然不好处理fling，或者说，也是类似的拦截，尤其衔接的问题，
// target越界了，先放的第二任衔接的不是原来的target如何处理衔接问题呢，没有子target可给了
//很少有万能的自定义View，看场景实现吧
class NestUpDownTwoPartsScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes), NestedScrollingParent3 {

    lateinit var upView: View
    lateinit var bottomView: View
    lateinit var helper: NestedScrollingParentHelper
    var maxScrollHeight = 0
    var totalHeight = 0
    val TAG = "NestRecycleViewScrollView"
    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        helper.onStopNestedScroll(target)
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray,
    ) {

    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
    ) {
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        overScrollerNest.abortAnimation()
        scrollInner(dy)
        consumed[1] = dy
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        upView = getChildAt(0)
        bottomView = getChildAt(1)
        helper = NestedScrollingParentHelper(this)
        overScrollerNest = OverScroller(context)
        if (childCount != 2)
            throw java.lang.RuntimeException("必须两个View，上面是NestScrollView，下面是RecycleView")


    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var top = t
        var bottom = b
        for (i in 0 until childCount) {
            getChildAt(i).layout(l, top, r, bottom)
            top += getChildAt(i).measuredHeight
            bottom += getChildAt(i).measuredHeight
            totalHeight += getChildAt(i).measuredHeight
        }
        maxScrollHeight = totalHeight - measuredHeight
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean,
    ): Boolean {
        return super.onNestedFling(target, velocityX, velocityY, consumed)
    }


    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        mLastOverScrollerValue = 0
        overScrollerNest.fling(
            0, 0, velocityX.toInt(),
            velocityY.toInt(), 0, 0, -totalHeight * 10, totalHeight * 10
        )
        //  这里必须加上，不然可能无法触发
        invalidate()
        return true
    }

    var mLastOverScrollerValue = 0
    override fun computeScroll() {
        super.computeScroll()
        if (overScrollerNest.computeScrollOffset()) {
            scrollInner(overScrollerNest.currY - mLastOverScrollerValue)
            mLastOverScrollerValue = overScrollerNest.currY
            invalidate()
        }
    }

    private lateinit var overScrollerNest: OverScroller

    override fun computeVerticalScrollRange(): Int {
        return totalHeight
    }

    private fun scrollInner(dy: Int) {
        var pConsume: Int = 0
        var cConsume: Int = 0
        if (dy > 0) {
            if (scrollY in 1 until maxScrollHeight) {
                pConsume = dy.coerceAtMost(maxScrollHeight - scrollY)
                scrollBy(0, pConsume)
                cConsume = dy - pConsume
                if (bottomView.canScrollVertically(cConsume)) {
                    bottomView.scrollBy(0, cConsume)
                }
            } else if (scrollY == 0) {
                bottomView.scrollTo(0, 0)
                if (upView.canScrollVertically(dy)) {
                    upView.scrollBy(0, dy)
                } else {
                    if (canScrollVertically(dy)) {
                        scrollBy(0, dy)
                    }
                }
            } else if (scrollY == maxScrollHeight) {
                if (bottomView.canScrollVertically(dy)) {
                    bottomView.scrollBy(0, dy)
                } else {
                    if (canScrollVertically(dy)) {
                        scrollBy(0, dy)
                    }
                }
            }
        } else {
            if (scrollY in 1 until maxScrollHeight) {
                pConsume = Math.max(dy, -scrollY)
                scrollBy(0, pConsume)
                cConsume = dy - pConsume
                if (bottomView.canScrollVertically(cConsume)) {
                    bottomView.scrollBy(0, cConsume)
                }
            } else if (scrollY == maxScrollHeight) {
                if (bottomView.canScrollVertically(dy)) {
                    bottomView.scrollBy(0, dy)
                } else {
                    if (canScrollVertically(dy)) {
                        scrollBy(0, dy)
                    }
                }
            } else {
                if (upView.canScrollVertically(dy)) {
                    upView.scrollBy(0, dy)
                }
                bottomView.scrollTo(0, 0)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        if (ev?.action == MotionEvent.ACTION_DOWN)
            overScrollerNest.abortAnimation()
        return super.dispatchTouchEvent(ev)
    }

}