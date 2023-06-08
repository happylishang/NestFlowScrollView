package com.snail.labaffinity.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.webkit.WebView

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


}