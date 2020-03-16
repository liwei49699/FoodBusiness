package com.chengzhen.foodbusiness.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.AudioRecord;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chengzhen.foodbusiness.R;
import com.chengzhen.foodbusiness.base.BaseActivity;
import com.chengzhen.foodbusiness.manager.GlideApp;
import com.chengzhen.foodbusiness.util.CustomGlideEngine;
import com.gyf.immersionbar.ImmersionBar;
import com.nanchen.compresshelper.CompressHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.EnumSet;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

import static com.zhihu.matisse.MimeType.BMP;
import static com.zhihu.matisse.MimeType.JPEG;
import static com.zhihu.matisse.MimeType.PNG;
import static com.zhihu.matisse.MimeType.WEBP;

public class LocalCheckDetailsActivity extends BaseActivity {

    //照片
    private static final int REQUEST_CODE_CHOOSE = 0X0002;
    @BindView(R.id.rv_tst)
    RecyclerView mRvTst;
    private ImageAdapter mImageAdapter;

    @Override
    protected boolean hasTitleBar() {
        return true;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_local_check_details;
    }

    @Override
    protected void init() {

        //状态栏颜色，不写默认透明色
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .init();

        setCenterTitle("现场核查");
    }

    @Override
    protected void getData() {

        mImageAdapter = new ImageAdapter();
        mRvTst.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        mRvTst.setAdapter(mImageAdapter);
    }

    class ImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public ImageAdapter() {
            super(R.layout.item_test);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, String item) {

            ImageView ivTest = helper.getView(R.id.iv_test);
            GlideApp.with(mContext).load(item).into(ivTest);
        }
    }

    @OnClick({R.id.btn_camera, R.id.btn_audio_record, R.id.btn_video})
    public void onViewClicked(View view) {

        RxPermissions rxPermissions = new RxPermissions(this);
        Disposable subscribe = rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO)
                .subscribe(aBoolean -> {

                    if (aBoolean) {
                        switch (view.getId()) {
                            case R.id.btn_camera:
                                operatePhoto();
                                break;
                            case R.id.btn_audio_record:
                                operateAudio();
                                break;
                            case R.id.btn_video:
                                operateVideo();
                                break;
                        }
                    } else {
                        ToastUtils.showShort(getString(R.string.permission_hint));
                    }
                });
    }

    private void operateVideo() {

        startActivity(VideoRecordActivity.class);
    }

    private void operateAudio() {

        startActivity(AudioRecordActivity.class);
    }

    private void operatePhoto() {

        Matisse.from(this)
                .choose(EnumSet.of(JPEG, PNG, BMP, WEBP), false)
                .theme(R.style.Matisse_Dracula)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.chengzhen.foodbusiness.fileprovider", "check"))
                .countable(false)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .maxSelectable(9)
                .originalEnable(false)
                .maxOriginalSize(10)
                .imageEngine(new CustomGlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {

            List<String> stringList = Matisse.obtainPathResult(data);

            for (int i = 0; i < stringList.size(); i++) {

                String s = stringList.get(i);
                String compressImg = compressImg(TimeUtils.getNowMills() + "", s);
                stringList.set(i,compressImg);

            }
            for (String s : stringList) {

                String size = FileUtils.getSize(s);
                LogUtils.dTag("--TAG--0" + s, size+ "--");

            }

            mImageAdapter.addData(stringList);
//            LogUtils.dTag("--TAG--1", Matisse.obtainPathResult(data) + "");
//            LogUtils.dTag("--TAG--2", Matisse.obtainResult(data) + "");
//            LogUtils.dTag("--TAG--3", Matisse.obtainOriginalState(data) + "");
        }
    }

    private String compressImg(String yourFileName,String oldFile){

        new CompressHelper.Builder(this)
                .setMaxWidth(720)  // 默认最大宽度为720
                .setMaxHeight(960) // 默认最大高度为960
                .setQuality(80)    // 默认压缩质量为80
                .setFileName(yourFileName) // 设置你需要修改的文件名
                .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                .build()
                .compressToFile(new File(oldFile));

        return Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + yourFileName + ".jpeg";
    }
}
