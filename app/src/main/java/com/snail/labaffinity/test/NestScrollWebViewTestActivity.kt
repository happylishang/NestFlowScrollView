package com.snail.labaffinity.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.snail.labaffinity.databinding.ActivityNestscrollWebviewTestBinding
import com.snail.labaffinity.utils.ToastUtil
import com.snail.labaffinity.utils.ViewHelper

class NestScrollWebViewTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNestscrollWebviewTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        for (i in 0..30) {
            ViewHelper.addButton(binding.lvContainer, "position  $i") {
                ToastUtil.show("position  $i")
            }
        }
        binding.webview.loadUrl("https://juejin.cn/post/6844903761060577294")
    }

}