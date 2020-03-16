package com.chengzhen.foodbusiness.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chengzhen.foodbusiness.R;
import com.gyf.immersionbar.ImmersionBar;
import com.king.zxing.CaptureActivity;
import com.king.zxing.camera.FrontLightMode;

public class CustomCaptureActivity extends CaptureActivity {

    private boolean isContinuousScan;
    @Override
    public int getLayoutId() {

        return R.layout.custom_capture_activity;
    }

    @Override
    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);

        //状态栏颜色，不写默认透明色
        ImmersionBar.with(this)
                .transparentStatusBar()  //透明状态栏，不写默认透明色
                .statusBarDarkFont(false)
                .init();

        RelativeLayout rlTop = findViewById(R.id.rl_top);

        int statusBarHeight = BarUtils.getStatusBarHeight();

        int originHeight = ConvertUtils.dp2px(30);
        if(statusBarHeight > originHeight) {

            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) rlTop.getLayoutParams();
            layoutParams.topMargin = statusBarHeight + 5;

            rlTop.setLayoutParams(layoutParams);
        }

        TextView tvTitle = findViewById(R.id.tv_capture_title);
        tvTitle.setText(getIntent().getStringExtra("key_title"));
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        isContinuousScan = getIntent().getBooleanExtra("key_continuous_scan",false);
        //获取CaptureHelper，里面有扫码相关的配置设置
        getCaptureHelper().playBeep(false)//播放音效
                .vibrate(true)//震动
                .supportVerticalCode(true)//支持扫垂直条码，建议有此需求时才使用。
//                .decodeFormats(DecodeFormatManager.QR_CODE_FORMATS)//设置只识别二维码会提升速度
//                .framingRectRatio(0.9f)//设置识别区域比例，范围建议在0.625 ~ 1.0之间。非全屏识别时才有效
//                .framingRectVerticalOffset(0)//设置识别区域垂直方向偏移量，非全屏识别时才有效
//                .framingRectHorizontalOffset(0)//设置识别区域水平方向偏移量，非全屏识别时才有效
                .frontLightMode(FrontLightMode.AUTO)//设置闪光灯模式
                .tooDarkLux(45f)//设置光线太暗时，自动触发开启闪光灯的照度值
                .brightEnoughLux(100f)//设置光线足够明亮时，自动触发关闭闪光灯的照度值
                .continuousScan(isContinuousScan)//是否连扫
                .supportLuminanceInvert(true);//是否支持识别反色码（黑白反色的码），增加识别率
    }


    /**
     * 扫码结果回调
     * @param result 扫码结果
     * @return
     */
    @Override
    public boolean onResultCallback(String result) {
        if(isContinuousScan){//连续扫码时，直接弹出结果
            Toast.makeText(this,result, Toast.LENGTH_SHORT).show();
        }

        return super.onResultCallback(result);
    }
}