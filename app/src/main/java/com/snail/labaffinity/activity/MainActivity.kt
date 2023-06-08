package com.snail.labaffinity.activity

import android.content.Intent
import android.os.Bundle
import com.snail.labaffinity.databinding.ActivityMainBinding
import com.snail.labaffinity.test.CustomNestScrollRecyclerViewTestActivity
import com.snail.labaffinity.test.NestRecycleViewInnerWebviewTestActivity
import com.snail.labaffinity.test.NestScrollRecyclerViewTestActivity
import com.snail.labaffinity.test.NestScrollWebViewTestActivity
import com.snail.labaffinity.utils.ViewHelper

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewHelper.addButton(binding.contentMain.container, "RecyclerViewNestTestActivity") {
            startActivity(Intent(this@MainActivity, NestScrollRecyclerViewTestActivity::class.java))
        }
        ViewHelper.addButton(binding.contentMain.container, "自定义CustomNestScrollRecyclerView") {
            startActivity(Intent(this@MainActivity, CustomNestScrollRecyclerViewTestActivity::class.java))
        }
        ViewHelper.addButton(binding.contentMain.container, "NestScrollWebViewTestActivity") {
            startActivity(Intent(this@MainActivity, NestScrollWebViewTestActivity::class.java))
        }
        ViewHelper.addButton(binding.contentMain.container, "NestRecycleViewInnerWebviewTestActivity") {
            startActivity(Intent(this@MainActivity, NestRecycleViewInnerWebviewTestActivity::class.java))
        }



    }


}