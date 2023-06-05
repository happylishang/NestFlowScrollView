package com.snail.labaffinity.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.snail.labaffinity.R

/**
 * Author: lishang
 * Data: 17/1/5 下午2:10
 * Des:
 * version:
 */
class MyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.activity_main, container, false)

        return root
    }

    companion object {
        fun newInstance(msg: String?): Fragment {
            val fragment: Fragment = MyFragment()
            val bundle = Bundle()
            bundle.putString("msg", msg)
            fragment.arguments = bundle
            return fragment
        }
    }
}