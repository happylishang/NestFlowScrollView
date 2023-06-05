package com.snail.labaffinity.activity

import android.content.Intent
import android.os.Bundle
import com.snail.labaffinity.databinding.ActivityMainBinding
import com.snail.labaffinity.test.NestedScrollViewActivity
import com.snail.labaffinity.test.RecyclerViewNestTestActivity
import com.snail.labaffinity.utils.ViewHelper

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewHelper.addButton(binding.contentMain.container, "NestedScrollViewActivity") {
            startActivity(Intent(this@MainActivity, NestedScrollViewActivity::class.java))
        }

        ViewHelper.addButton(binding.contentMain.container, "RecyclerViewNestTestActivity") {
            startActivity(Intent(this@MainActivity, RecyclerViewNestTestActivity::class.java))
        }
    }


}