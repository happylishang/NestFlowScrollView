package com.snail.labaffinity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import com.snail.labaffinity.R;
import com.snail.labaffinity.service.BackGroundService;
import com.snail.labaffinity.utils.LogUtils;

import butterknife.OnClick;
import cn.campusapp.router.Router;
import cn.campusapp.router.route.ActivityRoute;

public class MainActivity extends BaseActivity {

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, BackGroundService.class);
//        startService(intent);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        LogUtils.v("A onCreate");
    }


    @OnClick(R.id.first)
    void first() {
        ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://second");
        activityRoute.open();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        LogUtils.v("A onPostCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.v("A onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.v("A onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.v("A onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.v("A onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.v("A onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.v("A onDestroy");
    }
}
