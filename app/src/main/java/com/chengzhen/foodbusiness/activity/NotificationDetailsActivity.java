package com.chengzhen.foodbusiness.activity;


import com.chengzhen.foodbusiness.R;
import com.chengzhen.foodbusiness.base.BaseActivity;
import com.gyf.immersionbar.ImmersionBar;

public class NotificationDetailsActivity extends BaseActivity {

    @Override
    protected boolean hasTitleBar() {
        return true;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_notification_details;
    }

    @Override
    protected void init() {

        //状态栏颜色，不写默认透明色
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .init();

        setCenterTitle("通知详情");
    }

    @Override
    protected void getData() {

    }
}
