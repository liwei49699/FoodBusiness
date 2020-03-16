package com.chengzhen.foodbusiness.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chengzhen.foodbusiness.MainActivity;
import com.chengzhen.foodbusiness.R;
import com.chengzhen.foodbusiness.base.BaseActivity;
import com.chengzhen.foodbusiness.util.Constant;
import com.gyf.immersionbar.ImmersionBar;


import butterknife.BindView;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_user)
    EditText mEtUser;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.cb_remember_password)
    CheckBox mCbRememberPassword;
    @BindView(R.id.tv_remember_password)
    TextView mTvRememberPassword;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_find_password)
    TextView mTvFindPassword;
    private String mUserStr;
    private String mPasswordStr;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {

        //状态栏颜色，不写默认透明色
        ImmersionBar.with(this)
                .transparentStatusBar()  //透明状态栏，不写默认透明色
                .statusBarDarkFont(false)
                .init();
    }

    @Override
    protected void getData() {

        String account = SPUtils.getInstance().getString(Constant.ACCOUNT_USER,"");

        boolean rememberPassword = SPUtils.getInstance().getBoolean(Constant.REMEMBER_PASSWORD, false);
        if(rememberPassword) {
            String password = SPUtils.getInstance().getString(Constant.ACCOUNT_PASSWORD, "");
            if(!TextUtils.isEmpty(password)) {
                mEtPassword.setText(password);
                mEtPassword.setSelection(password.length());
            }
        }

        if(!TextUtils.isEmpty(account)) {
            mEtUser.setText(account);
            mEtUser.setSelection(account.length());
        }

        mTvLogin.setOnClickListener(v -> startLogin());
        mTvFindPassword.setOnClickListener(v -> startActivity(FindPasswordActivity.class));
        mTvRememberPassword.setOnClickListener(v -> mCbRememberPassword.setChecked(!mCbRememberPassword.isChecked()));
    }

    private void startLogin() {

        boolean judge = judge();
        if(judge) {

            login();
            KeyboardUtils.hideSoftInput(this);
        } else {
            startActivity(MainActivity.class);
        }
    }

    private void login() {

        showProDialogHint();
        //http://192.168.18.30:8080/lpro_lgb/service/api/ApiUserInfo/login?name=lisi&password=123456
        String usr = Constant.URL + "/ApiUserInfo/login";

//        RxHttp.postForm(usr)
//                .add("name",accountStr)
//                .add("password",passwordStr)
//                .asObject(LoginResponse.class)
//                .observeOn(AndroidSchedulers.mainThread())
//                .as(RxLife.as(this))
//                .subscribe(loginResponse -> {
//
//                    int error = loginResponse.getCode();
//                    if(error == 0) {
//                        LoginResponse.DataBean data = loginResponse.getData();
//                        LoginResponse.DataBean.UserInfoBean userInfo = data.getUserInfo();
//                        mAppPreferences.put(Constant.ACCOUNT,userInfo.getName());
//                        mAppPreferences.put(Constant.LOGIN_SIGN,true);
//                        mAppPreferences.put(Constant.Token,data.getToken());
//                        mAppPreferences.put(Constant.USER_ID,userInfo.getId() + "");
//                        startActivity(MainActivity.class);
//                        finish();
//
//                        String userJson = GsonUtils.toJson(userInfo);
//                        mAppPreferences.put(Constant.JSON_USER,userJson);
//
//                        TagAliasOperatorHelper.onTagAliasAction(mContext,3,userInfo.getName() + "");
//
//                    } else {
//                        ToastUtils.showShort(loginResponse.getMsg());
//                        hideProDialogHint();
//                    }
//                    hideProDialogHint();
//                }, throwable -> {
//                    //出错
//                    ToastUtils.showShort("访问服务器错误");
//                    hideProDialogHint();
//                });
    }

    private boolean judge() {

        obtainInputData();

        if (TextUtils.isEmpty(mUserStr)) {

            ToastUtils.showShort("请输入用户名");
            return false;
        }

        if (TextUtils.isEmpty(mPasswordStr)) {

            ToastUtils.showShort("请输入密码");
            return false;
        }
        return true;
    }

    private void obtainInputData() {

        if (mEtUser.getText() != null) {
            mUserStr = mEtUser.getText().toString();
        }

        if(mEtPassword.getText() != null) {
            mPasswordStr = mEtPassword.getText().toString();
        }
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
