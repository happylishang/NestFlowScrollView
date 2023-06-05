package com.snail.labaffinity.utils

import android.view.ViewGroup
import android.widget.Button

object ViewHelper {

      fun addButton(viewGroup: ViewGroup, content: String, block: (() -> Unit )?) {
        val button = Button(viewGroup.context)
        button.text = content
        button.setOnClickListener { block?.invoke() }
        viewGroup.addView(button)
    }
}