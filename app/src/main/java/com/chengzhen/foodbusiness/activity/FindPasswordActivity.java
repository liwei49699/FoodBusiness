package com.chengzhen.foodbusiness.activity;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chengzhen.foodbusiness.R;
import com.chengzhen.foodbusiness.base.BaseActivity;
import com.chengzhen.foodbusiness.util.Constant;
import com.chengzhen.foodbusiness.util.VerifyCountDownUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.ruffian.library.widget.RTextView;
import com.rxjava.rxlife.RxLife;


import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;

public class FindPasswordActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.rtv_obtain_code)
    RTextView mRtvObtainCode;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;
    @BindView(R.id.cl_root)
    ConstraintLayout mClRoot;
    private String mPhoneStr;
    private String mCodeStr;
    private String mPasswordStr;
    private VerifyCountDownUtils mVerifyCountDownUtils;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_find_password;
    }

    @Override
    protected void init() {

        //状态栏颜色，不写默认透明色
        ImmersionBar.with(this)
                .transparentStatusBar()  //透明状态栏，不写默认透明色
                .statusBarDarkFont(false)
                .init();

        mVerifyCountDownUtils = new VerifyCountDownUtils(mEtPhone,mRtvObtainCode, 10 * 1000, 1000);
    }

    @Override
    protected void getData() {

        mIvBack.setOnClickListener(v -> finish());
        mTvConfirm.setOnClickListener(v -> startFindPassword());
        mClRoot.setOnClickListener(v -> KeyboardUtils.hideSoftInput(mContext));
        mRtvObtainCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                ToastUtils.showShort("获取验证码");
                String phone = mEtPhone.getText().toString();
                boolean isMobile = RegexUtils.isMobileExact(phone);
                if(isMobile) {
                    mVerifyCountDownUtils.start();
                } else {
                    ToastUtils.showShort("请输入正确的手机号码");
                }
            }
        });
    }

    private void startFindPassword() {

        boolean judge = judge();
        if(judge) {

            findPassword();
            KeyboardUtils.hideSoftInput(this);
        }
    }

    private void findPassword() {

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

        if (TextUtils.isEmpty(mPhoneStr)) {
            ToastUtils.showShort("请输入手机号码");
            return false;
        }

        if (TextUtils.isEmpty(mCodeStr)) {
            ToastUtils.showShort("请输入手机验证码");
            return false;
        }
        if (TextUtils.isEmpty(mPasswordStr)) {
            ToastUtils.showShort("请输入新密码");
            return false;
        }
        return true;
    }

    private void obtainInputData() {

        if (mEtPhone.getText() != null) {
            mPhoneStr = mEtPhone.getText().toString();
        }

        if(mEtCode.getText() != null) {
            mCodeStr = mEtCode.getText().toString();
        }

        if(mEtPassword.getText() != null) {
            mPasswordStr = mEtPassword.getText().toString();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVerifyCountDownUtils.cancel();
    }
}
