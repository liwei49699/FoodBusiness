package com.chengzhen.foodbusiness.fragment;

import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chengzhen.foodbusiness.R;
import com.chengzhen.foodbusiness.activity.ChangePasswordActivity;
import com.chengzhen.foodbusiness.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonInfoFragment extends BaseFragment {

    @BindView(R.id.rl_top)
    RelativeLayout mRlTop;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_person_info;
    }

    @Override
    protected void initView() {

//        int statusBarHeight = BarUtils.getStatusBarHeight();
//
//        int originHeight = ConvertUtils.dp2px(30);
//        if(statusBarHeight > originHeight) {
//
//            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRlTop.getLayoutParams();
//            layoutParams.topMargin = statusBarHeight + 5;
//
//            mRlTop.setLayoutParams(layoutParams);
//        }
    }

    @Override
    protected void getData() {

    }

    @OnClick({R.id.iv_back, R.id.rl_change_password, R.id.rl_system_setting, R.id.rl_use_help,
            R.id.rl_version_info,R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.iv_back:
//                finish();
//                break;
            case R.id.rl_change_password:
                startActivity(ChangePasswordActivity.class);
                break;
            case R.id.rl_system_setting:
                break;
            case R.id.rl_use_help:
                break;
            case R.id.rl_version_info:
                break;
            case R.id.tv_logout:
                ToastUtils.showShort("退出登录");
                break;
        }
    }
}
