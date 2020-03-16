package com.chengzhen.foodbusiness.util;

import android.os.CountDownTimer;
import android.widget.EditText;

import com.blankj.utilcode.util.RegexUtils;
import com.ruffian.library.widget.RTextView;

/**
 * Created by li on 2018/8/22.
 * WeChat 18571658038
 * author LiWei
 */
public class VerifyCountDownUtils extends CountDownTimer {

    private EditText mEtPhone;
    private RTextView mTextView;

    public VerifyCountDownUtils(EditText etPhone, RTextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        mEtPhone = etPhone;
        this.mTextView = textView;
    }


    @Override
    public void onTick(long millisUntilFinished) {

        mTextView.setEnabled(false); //设置不可点击
        mTextView.setText((millisUntilFinished / 1000 + 1) + "s后重新获取");  //设置倒计时时间
//        mTextView.setTextColor(Color.WHITE);
//        mTextView.setBackground(AppApplication.sApp.getResources().getDrawable(R.drawable.bg_tv_verify_unable));
    }

    @Override
    public void onFinish() {

        mTextView.setText("获取验证码");

        boolean isPhone = RegexUtils.isMobileExact(mEtPhone.getText().toString());
        if(isPhone) {
            mTextView.setEnabled(true);//重新获得点击
        } else {
            mTextView.setEnabled(false);
        }

//        mTextView.setBackgroundColor(Color.parseColor("#ff9800"));  //还原背景色
//        mTextView.setBackground(AppApplication.sApp.getResources().getDrawable(R.drawable.bg_tv_verify));

    }
}
