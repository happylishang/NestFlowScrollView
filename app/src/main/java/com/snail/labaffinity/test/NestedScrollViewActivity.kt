package com.snail.labaffinity.test

import android.os.Bundle
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.snail.labaffinity.databinding.ActivityNestscrollTestBinding
import com.snail.labaffinity.utils.ViewHelper

class NestedScrollViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNestscrollTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNestscrollTestBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.nestedScrollView.isNestedScrollingEnabled = true

        for (i in 1..30) {
            ViewHelper.addButton(binding.linearLayout1, "first$i", null)
        }
        val wev = WebView(this)
//        nestedScrollView是全展开的，webview会被加载到最后，这个对于底部有推荐的并不是很友好
//        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 900)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        binding.linearLayout2.addView(wev, params)
        wev.loadUrl("https://juejin.cn/post/6844904184911773709")
//        for (i in 1..30) {
//            ViewHelper.addButton(binding.linearLayout2, "second$i", null)
//        }
    }
}