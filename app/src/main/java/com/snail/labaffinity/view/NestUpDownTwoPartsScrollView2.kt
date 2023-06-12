package com.snail.labaffinity.view

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import android.widget.OverScroller
import kotlin.math.abs
import kotlin.math.roundToInt

//垂直滚动的ScrollView嵌套recyvleview
//FrameLayout对于自定垂直的布局很方便
//自定义父类其实也要拦截底部的，不然不好处理fling，或者说，也是类似的拦截，尤其衔接的问题，
// target越界了，先放的第二任衔接的不是原来的target如何处理衔接问题呢，没有子target可给了
//很少有万能的自定义View，看场景实现吧
class NestUpDownTwoPartsScrollView2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {


    private val gestureDetector: GestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float,
            ): Boolean {
                //     交给父类统一处理比较好，上下两层获取的fling速度不一致
                mLastOverScrollerValue = 0
                overScrollerNest.fling(
                    0, 0, velocityX.toInt(),
                    -velocityY.toInt(), 0, 0, -totalHeight * 10, totalHeight * 10
                )
                //  这里必须加上，不然可能无法触发
                invalidate()
                return false
            }
        })



    lateinit var upView: View
    lateinit var bottomView: View
    var maxScrollHeight = 0
    var totalHeight = 0
    val TAG = "NestRecycleViewScrollView"


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
        var bottom = b
        for (i in 0 until childCount) {
            getChildAt(i).layout(l, top, r, bottom)
            top += getChildAt(i).measuredHeight
            bottom += getChildAt(i).measuredHeight
            totalHeight += getChildAt(i).measuredHeight
        }
        maxScrollHeight = totalHeight - measuredHeight
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        //  获取的fling速度有差异，原因不详
        return true
    }

    var mLastOverScrollerValue = 0
    override fun computeScroll() {
        super.computeScroll()
        if (overScrollerNest.computeScrollOffset()) {
//            Log.v("lishang", "compu " + overScrollerNest.currY + " " + overScrollerNest.currVelocity)
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
                scrollBy(0, dy)
                cConsume = dy - pConsume
                if (bottomView.canScrollVertically(cConsume) && cConsume != 0) {
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
            } else if (scrollY >= maxScrollHeight) {
                scrollTo(0, maxScrollHeight)
                if (bottomView.canScrollVertically(dy)) {
                    bottomView.scrollBy(0, dy)
                } else {
                    overScrollerNest.abortAnimation()
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
        invalidate()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev != null) {
            gestureDetector.onTouchEvent(ev)
        }
        if (ev?.action == MotionEvent.ACTION_DOWN)
            overScrollerNest.abortAnimation()

        if (ev?.action == MotionEvent.ACTION_MOVE && mBeDraging) {
            scrollInner((mLastY - ev.rawY).roundToInt())
            mLastY = ev.rawY
        }
        return super.dispatchTouchEvent(ev)
    }

    private var mLastY: Float = 0f
    private var mDownY: Float = 0f
    private var mDownX: Float = 0f
    private var mBeDraging: Boolean = false
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mLastY = event.rawY
                mDownY = event.rawY
                mDownX = event.rawX
                mBeDraging = false
            }
            MotionEvent.ACTION_MOVE -> if (abs(event.rawY - mDownY) >  ViewConfiguration.get(
                    context
                ).scaledTouchSlop) {
                mBeDraging = true
                return true
            } else {
                mLastY = event.rawY
            }
            else -> {}
        }
        return super.onInterceptTouchEvent(event)
    }


}