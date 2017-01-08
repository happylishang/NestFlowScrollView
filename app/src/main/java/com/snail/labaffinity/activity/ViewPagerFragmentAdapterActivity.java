package com.snail.labaffinity.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.snail.labaffinity.R;
import com.snail.labaffinity.adapter.MyFragmentPagerAdapter;
import com.snail.labaffinity.utils.LogUtils;

import butterknife.BindView;
import cn.campusapp.router.annotation.RouterMap;

/**
 * Author: hzlishang
 * Data: 16/10/12 上午9:56
 * Des:
 * version:
 */

@RouterMap({"activity://second"})
public class ViewPagerFragmentAdapterActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        LogUtils.v("B  onCreate");
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        LogUtils.v("B  onPostCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.v("B  onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.v("B  onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.v("B  onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.v("B  onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.v("B  onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.v("B  onDestroy");
    }
}
