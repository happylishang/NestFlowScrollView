package com.snail.labaffinity.test

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.ListAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.snail.labaffinity.R
import com.snail.labaffinity.databinding.ActivityNestRecycleviewInnerwebviewBinding
import com.snail.labaffinity.databinding.ActivityNestscrollWebviewTestBinding
import com.snail.labaffinity.utils.LogUtils
import com.snail.labaffinity.utils.ToastUtil
import com.snail.labaffinity.utils.ViewHelper
import com.snail.labaffinity.view.NetRecycleViewWebView

class NestRecycleViewInnerWebviewTestActivity : AppCompatActivity() {
    val list = mutableListOf<ItemVo>()
    var webview: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNestRecycleviewInnerwebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val adapter =
            object :
                androidx.recyclerview.widget.ListAdapter<ItemVo, ViewHolder>(object :
                    DiffUtil.ItemCallback<ItemVo>() {
                    override fun areItemsTheSame(oldItem: ItemVo, newItem: ItemVo): Boolean {
                        return oldItem == newItem
                    }

                    override fun areContentsTheSame(oldItem: ItemVo, newItem: ItemVo): Boolean {
                        return oldItem == newItem
                    }
                }) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                    if (viewType == itemCount - 1) {
                        webview =
                            layoutInflater.inflate(R.layout.item_webview, parent, false).findViewById(R.id.webview)
                        webview?.loadUrl("https://juejin.cn/post/6844903761060577294")

                        return object :
                            ViewHolder(webview!!) {}
                    }



                    val button = Button(this@NestRecycleViewInnerWebviewTestActivity)
                    return object : ViewHolder(button) {}

                }

                override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                    if (position != itemCount - 1) {
                        ((holder.itemView) as Button).text = "$position"
                        LogUtils.v("position $position")
                        ((holder.itemView) as Button).setOnClickListener { ToastUtil.show("position $position") }
                    }
                }

                override fun getItemCount(): Int {
                    return super.getItemCount()
                }

                override fun getItemViewType(position: Int): Int {
                    return list[position].type
                }

            }
        binding.rcv.layoutManager = LinearLayoutManager(this)
        binding.rcv.adapter = adapter
        for (i in 0..30) {
            list.add(ItemVo("position $i", i, i))
        }
        adapter.submitList(list)


//        binding.webview.loadUrl("https://juejin.cn/post/6844903761060577294")
    }

}