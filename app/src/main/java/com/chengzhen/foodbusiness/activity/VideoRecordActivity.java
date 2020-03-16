package com.chengzhen.foodbusiness.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chengzhen.foodbusiness.R;
import com.chengzhen.foodbusiness.base.BaseActivity;
import com.zhaoss.weixinrecorded.activity.RecordedActivity;

import butterknife.BindView;

public class VideoRecordActivity extends BaseActivity {

    public static final int VIDEO_REQUEST_CODE = 0X0001;
    @BindView(R.id.tv_path)
    TextView mTvPath;
    @BindView(R.id.textureView)
    TextureView mTextureView;
    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;
    private MediaPlayer mMediaPlayer;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_video_record;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void getData() {

    }

    public void startRecord(View view) {

        Intent intent = new Intent(this, RecordedActivity.class);
        startActivityForResult(intent, VIDEO_REQUEST_CODE);

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void playVideo(String videoPath){

        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(videoPath);
            mMediaPlayer.setSurface(new Surface(mTextureView.getSurfaceTexture()));
            mMediaPlayer.setLooping(true);
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();

                    float ratio = mp.getVideoWidth()*1f/mp.getVideoHeight();
                    int width = mTextureView.getWidth();
                    ViewGroup.LayoutParams layoutParams = mTextureView.getLayoutParams();
                    layoutParams.height = (int) (width/ratio);
                    mTextureView.setLayoutParams(layoutParams);
                }
            });
            mMediaPlayer.prepareAsync();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == VIDEO_REQUEST_CODE) {
                mTextureView.setVisibility(View.GONE);
                mIvPhoto.setVisibility(View.GONE);
                int dataType = data.getIntExtra(RecordedActivity.INTENT_DATA_TYPE, RecordedActivity.RESULT_TYPE_VIDEO);
                if (dataType == RecordedActivity.RESULT_TYPE_VIDEO) {
                    String videoPath = data.getStringExtra(RecordedActivity.INTENT_PATH);
                    mTvPath.setText("视频地址: " + videoPath);
                    mTextureView.setVisibility(View.VISIBLE);
                    playVideo(videoPath);
                } else if (dataType == RecordedActivity.RESULT_TYPE_PHOTO) {
                    String photoPath = data.getStringExtra(RecordedActivity.INTENT_PATH);
                    mTvPath.setText("图片地址: " + photoPath);
                    mIvPhoto.setVisibility(View.VISIBLE);
                    mIvPhoto.setImageBitmap(BitmapFactory.decodeFile(photoPath));
                }
            }
        }
    }
}
