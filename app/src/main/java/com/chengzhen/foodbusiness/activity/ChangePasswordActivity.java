package com.chengzhen.foodbusiness.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chengzhen.foodbusiness.R;
import com.chengzhen.foodbusiness.base.BaseActivity;
import com.gyf.immersionbar.ImmersionBar;


import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.et_password_old)
    EditText mEtPasswordOld;
    @BindView(R.id.et_password_new)
    EditText mEtPasswordNew;
    @BindView(R.id.et_password_again)
    EditText mEtPasswordAgain;
    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;
    private String mPasswordOldStr;
    private String mPasswordNewStr;
    private String mPasswordAgainStr;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_change_password;
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

        mIvBack.setOnClickListener(v -> finish());
        mTvConfirm.setOnClickListener(v -> startChangePassword());
    }

    private void startChangePassword() {

        boolean judge = judge();
        if(judge) {

            changePassword();
            KeyboardUtils.hideSoftInput(this);
        }
    }

    private void changePassword() {

        //        String token = mAppPreferences.getString(Constant.Token, "");
//
//        showProDialogHint();
//        //http://192.168.18.30:8080/lpro_lgb/service/api/ApiUserInfo/resetPwd?oldPwd=123456&newPwd=1234567
//        String url = Constant.URL + "/ApiUserInfo/resetPwd";
//        RxHttp.postForm(url)
//                .addHeader("access-token",token)
//                .add("oldPwd",mOldPasswordStr)
//                .add("newPwd",mNewPasswordStr)
//                .asObject(ResetPwdResponse.class)
//                .observeOn(AndroidSchedulers.mainThread())
//                .as(RxLife.as(this))
//                .subscribe(resetPwdResponse -> {
//
//                    boolean success = CodeUtils.judgeSuccessNormal(resetPwdResponse, mContext);
//                    if(success) {
//                        finish();
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

        if (TextUtils.isEmpty(mPasswordOldStr)) {
            ToastUtils.showShort("请输入现在密码");
            return false;
        }

        if (TextUtils.isEmpty(mPasswordNewStr)) {
            ToastUtils.showShort("请输入新密码");
            return false;
        }
        if (TextUtils.isEmpty(mPasswordAgainStr)) {
            ToastUtils.showShort("请确认新密码");
            return false;
        }
        return true;
    }

    private void obtainInputData() {

        if (mEtPasswordOld.getText() != null) {
            mPasswordOldStr = mEtPasswordOld.getText().toString();
        }

        if(mEtPasswordNew.getText() != null) {
            mPasswordNewStr = mEtPasswordNew.getText().toString();
        }

        if(mEtPasswordAgain.getText() != null) {
            mPasswordAgainStr = mEtPasswordAgain.getText().toString();
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
