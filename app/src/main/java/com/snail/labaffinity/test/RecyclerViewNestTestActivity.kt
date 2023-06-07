package com.snail.labaffinity.test

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.snail.labaffinity.databinding.ActivityRenestTestBinding
import com.snail.labaffinity.utils.LogUtils
import com.snail.labaffinity.utils.ToastUtil
import com.snail.labaffinity.utils.ViewHelper

class RecyclerViewNestTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRenestTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        for (i in 0..30) {
            ViewHelper.addButton(binding.lvContainer, "position  $i") {
                ToastUtil.show("position  $i")
            }
        }


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = object : RecyclerView.Adapter<ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val button = Button(this@RecyclerViewNestTestActivity)
                // 即使不需要实现，抽象类也需要结构体
                return object : ViewHolder(button) {}
            }

            override fun getItemCount(): Int {
                return 100
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                //  强制类型转换
                ((holder.itemView) as Button).text = "$position"
                LogUtils.v("position $position")
                ((holder.itemView) as Button).setOnClickListener { ToastUtil.show("position $position") }
            }

        }

    }


}