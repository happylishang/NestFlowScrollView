package com.snail.labaffinity.view

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import android.widget.OverScroller
import androidx.core.view.ViewCompat
import kotlin.math.abs
import kotlin.math.roundToInt

//垂直滚动的ScrollView嵌套recyvleview
//FrameLayout对于自定垂直的布局很方便
//自定义父类其实也要拦截底部的，不然不好处理fling，或者说，也是类似的拦截，尤其衔接的问题，
// target越界了，先放的第二任衔接的不是原来的target如何处理衔接问题呢，没有子target可给了
//很少有万能的自定义View，看场景实现吧
class NestHeaderRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {


    lateinit var upView: View
    lateinit var bottomView: View
    var maxScrollHeight = 0
    var totalHeight = 0
    val TAG = "NestRecycleViewScrollView"

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        overScrollerNest.abortAnimation()
        consumed[1] = scrollInner(dy)
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        if (dyUnconsumed < 0) {
            if (canScrollVertically(dyUnconsumed)) {
                scrollBy(0, dyUnconsumed)
            }
        }
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        upView = getChildAt(0)
        bottomView = getChildAt(1)
        overScrollerNest = OverScroller(context)
        if (childCount != 2)
            throw java.lang.RuntimeException("必须两个View，上面是NestScrollView，下面是RecycleView")
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var top = t
        var bottom = 0
        for (i in 0 until childCount) {
            getChildAt(i).layout(l, top, r, bottom + getChildAt(i).measuredHeight)
            top += getChildAt(i).measuredHeight
            bottom += getChildAt(i).measuredHeight
            totalHeight += getChildAt(i).measuredHeight
        }
        maxScrollHeight = totalHeight - measuredHeight
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

    private fun scrollInner(dy: Int): Int {
        var pConsume: Int = 0
        var cConsume: Int = 0
        if (dy > 0) {
            if (canScrollVertically(dy)) {
                pConsume = dy.coerceAtMost(maxScrollHeight - scrollY)
                scrollBy(0, pConsume)
            }
        } else {
            if (canScrollVertically(dy)) {
                pConsume = Math.max(-scrollY, dy)
                scrollBy(0, pConsume)
            }
        }
        invalidate()
        return pConsume
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        if (ev?.action == MotionEvent.ACTION_DOWN)
            overScrollerNest.abortAnimation()
        return super.dispatchTouchEvent(ev)
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {

        overScrollerNest.fling(
            0, 0, velocityX.toInt(),
            -velocityY.toInt(), 0, 0, -totalHeight * 10, totalHeight * 10
        )
        invalidate()
        return false
    }
}