package com.chengzhen.foodbusiness.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chengzhen.foodbusiness.R;
import com.chengzhen.foodbusiness.base.BaseActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.ruffian.library.widget.REditText;
import com.ruffian.library.widget.RRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordManagerActivity extends BaseActivity {

    @BindView(R.id.ret_company_name)
    REditText mRetCompanyName;
    @BindView(R.id.rrl_date)
    RRelativeLayout mRrlDate;
    @BindView(R.id.ret_license_key)
    REditText mRetLicenseKey;
    @BindView(R.id.tv_query)
    TextView mTvQuery;

    @Override
    protected boolean hasTitleBar() {
        return true;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_record_manager;
    }

    @Override
    protected void init() {

        //状态栏颜色，不写默认透明色
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .init();

        setCenterTitle("备案管理");
    }

    @Override
    protected void getData() {

       mRrlDate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


           }
       });

       mTvQuery.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               startActivity(RecordManagerSearchActivity.class);
           }
       });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                KeyboardUtils.hideSoftInput(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // Return whether touch the view.
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationOnScreen(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom);
        }
        return false;
    }
}
