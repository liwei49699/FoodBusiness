package com.chengzhen.foodbusiness.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chengzhen.foodbusiness.R;
import com.chengzhen.foodbusiness.base.BaseActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.king.zxing.CaptureActivity;
import com.king.zxing.Intents;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PermissionFilesActivity extends BaseActivity {

    private RxPermissions mRxPermissions;
    private Disposable mSubscribe;

    @Override
    protected boolean hasTitleBar() {
        return true;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_permission_files;
    }

    @Override
    protected void init() {

        //状态栏颜色，不写默认透明色
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .init();

        setCenterTitle("企业备案详细信息");

        mRxPermissions = new RxPermissions(this);
    }

    @Override
    protected void getData() {

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

    @OnClick({R.id.iv_scan_qr, R.id.tv_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_scan_qr:

                scanQR();
                break;
            case R.id.tv_query:
                break;
        }
    }

    private void scanQR() {

        mSubscribe = mRxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(aBoolean -> {

                    if (aBoolean) {
                        startScan();
                    } else {
                        ToastUtils.showShort(getString(R.string.permission_hint));
                    }
                });
    }

    /**
     * 扫码
     */
    private void startScan(){

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this,R.anim.in,R.anim.out);
        Intent intent = new Intent(this, CustomCaptureActivity.class);
        intent.putExtra("key_title","二维码扫描");
        //连续扫描
        intent.putExtra("key_continuous_scan",false);
        ActivityCompat.startActivityForResult(this,intent,REQUEST_CODE_SCAN,optionsCompat.toBundle());
    }

    public static final int REQUEST_CODE_SCAN = 0X01;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data!=null){
            switch (requestCode){
                case REQUEST_CODE_SCAN:
                    String result = data.getStringExtra(Intents.Scan.RESULT);
                    Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mSubscribe != null && !mSubscribe.isDisposed()) {
            mSubscribe.dispose();
        }
    }
}
