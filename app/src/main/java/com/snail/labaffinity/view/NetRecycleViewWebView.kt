package com.snail.labaffinity.view

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import androidx.core.view.NestedScrollingChild3
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat

/**
 * Author: snail
 * Data: 2023/6/8.
 * Des:
 * version:
 */
class NetRecycleViewWebView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : WebView(context, attrs), NestParentRecyclerView.IBottomNestedScrollChild {

    override fun getTargetScrollView(): View? {
        return this
    }
    init {
        settings.javaScriptEnabled = true
    }
}