package com.snail.labaffinity.test

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.snail.labaffinity.databinding.ActivityRenestTestBinding

class RecyclerViewNestTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRenestTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            }

        }

    }


}