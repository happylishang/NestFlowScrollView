package com.snail.labaffinity.activity;

import android.os.Bundle;

import com.snail.labaffinity.R;

import cn.campusapp.router.annotation.RouterMap;

/**
 * Author: hzlishang
 * Data: 16/10/12 上午9:56
 * Des:
 * version:
 */

@RouterMap({"activity://second"})
public class SecondActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
