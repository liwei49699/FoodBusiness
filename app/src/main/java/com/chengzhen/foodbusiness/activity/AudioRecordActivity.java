package com.chengzhen.foodbusiness.activity;

import android.graphics.drawable.AnimationDrawable;
import android.media.AudioFormat;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.chengzhen.foodbusiness.R;
import com.chengzhen.foodbusiness.base.BaseActivity;
import com.chengzhen.foodbusiness.view.AudioView;
import com.zlw.main.recorderlib.RecordManager;
import com.zlw.main.recorderlib.recorder.RecordConfig;
import com.zlw.main.recorderlib.recorder.RecordHelper;
import com.zlw.main.recorderlib.recorder.listener.RecordFftDataListener;
import com.zlw.main.recorderlib.recorder.listener.RecordResultListener;
import com.zlw.main.recorderlib.recorder.listener.RecordSoundSizeListener;
import com.zlw.main.recorderlib.recorder.listener.RecordStateListener;
import com.zlw.main.recorderlib.utils.Logger;

import java.io.File;
import java.util.Locale;

import butterknife.BindView;

public class AudioRecordActivity extends BaseActivity {

    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.btn_pause)
    Button mBtnPause;
    @BindView(R.id.btn_finish)
    Button mBtnFinish;
    @BindView(R.id.btn_open)
    Button mBtnOpen;
    @BindView(R.id.btn_status)
    Button mBtnStatus;
    @BindView(R.id.btn_size)
    Button mBtnSize;
    @BindView(R.id.audioView)
    AudioView audioView;
    @BindView(R.id.iv_animal)
    ImageView mIvAnimal;
    private RecordManager mRecordManager;

    private File mAudioFile;
    private MediaPlayer mMediaPlayer;
    private AnimationDrawable mAnimationDrawable;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_audio_record;
    }

    @Override
    protected void init() {

        mMediaPlayer = new MediaPlayer();

        mIvAnimal.setImageResource(R.drawable.animal_audio_play);
        mAnimationDrawable = (AnimationDrawable) mIvAnimal.getDrawable();

        mRecordManager = RecordManager.getInstance();
        mRecordManager.changeFormat(RecordConfig.RecordFormat.WAV);
        mRecordManager.changeRecordConfig(mRecordManager.getRecordConfig().setSampleRate(16000));
        mRecordManager.changeRecordConfig(mRecordManager.getRecordConfig().setEncodingConfig(AudioFormat.ENCODING_PCM_8BIT));

        String recordDir = String.format(Locale.getDefault(), "%s/Record/com.chengzhen.foodbusiness/",
                Environment.getExternalStorageDirectory().getAbsolutePath());
        mRecordManager.changeRecordDir(recordDir);
//        audioView.setStyle(AudioView.ShowStyle.STYLE_HOLLOW_LUMP,AudioView.ShowStyle.STYLE_HOLLOW_LUMP);

        mRecordManager.setRecordStateListener(new RecordStateListener() {
            @Override
            public void onStateChange(RecordHelper.RecordState state) {

                Logger.d("--TAG--", "onStateChange %s", state.name());

                switch (state) {
                    case PAUSE:
                        mBtnStatus.setText("状态：暂停中");
                        break;
                    case IDLE:
                        mBtnStatus.setText("状态：空闲中");
                        break;
                    case RECORDING:
                        mBtnStatus.setText("状态：录音中");
                        break;
                    case STOP:
                        mBtnStatus.setText("状态：停止");
                        break;
                    case FINISH:
                        mBtnStatus.setText("状态：录音结束");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onError(String error) {
                Logger.d("--TAG--", "onError %s", error);
            }
        });
        mRecordManager.setRecordSoundSizeListener(new RecordSoundSizeListener() {
            @Override
            public void onSoundSize(int soundSize) {
                mBtnSize.setText(String.format(Locale.getDefault(), "声音大小：%s db", soundSize));
            }
        });
        mRecordManager.setRecordResultListener(new RecordResultListener() {
            @Override
            public void onResult(File result) {
                Toast.makeText(mContext, "录音文件： " + result.getAbsolutePath(), Toast.LENGTH_SHORT).show();

                String absolutePath = result.getAbsolutePath();

                LogUtils.d("--TAG--",absolutePath);

                mAudioFile = result;
            }
        });
        mRecordManager.setRecordFftDataListener(new RecordFftDataListener() {
            @Override
            public void onFftData(byte[] data) {
                audioView.setWaveData(data);
            }
        });

    }

    @Override
    protected void getData() {

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startRecord();
            }
        });

        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopRecord();
            }
        });

        mBtnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(mAudioFile != null) {
//                    playAudioFile(mAudioFile);
//                }
                audioPlay(mAudioFile.getAbsolutePath());
            }
        });
    }

    private void audioPlay(String audioPath){
        
        if(mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(audioPath);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            //使用定时器显示进度
//            int duration = mMediaPlayer.getDuration();
//            mMediaPlayer.getCurrentPosition();

            mIvAnimal.setVisibility(View.VISIBLE);
            mAnimationDrawable.start();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //停止 回显第一帧
                    mIvAnimal.setVisibility(View.GONE);
                    mAnimationDrawable.stop();
                    mAnimationDrawable.selectDrawable(0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void audioStop(){

        if(mMediaPlayer != null){
            if(mMediaPlayer.isPlaying()){
                mMediaPlayer.stop();
                mAnimationDrawable.stop();
                mAnimationDrawable.selectDrawable(0);
                mIvAnimal.setVisibility(View.GONE);
            }
            mMediaPlayer.release();
        }
    }

    private void playAudioFile(final File file) {

        Log.d(TAG, "playAudioFile: " + file.getAbsolutePath());
        MediaPlayer mediaPlayer;
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setLooping(false);
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    Log.d(TAG, "Play local sound onError: " + i + ", " + i1);
                    return true;
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "playAudioFile: ", e);
        }
    }

    private void startRecord() {

        mRecordManager.start();
        mBtnStart.setEnabled(false);
        mBtnFinish.setEnabled(true);
    }

    private void stopRecord() {

        mRecordManager.stop();
        mBtnStart.setEnabled(true);
        mBtnFinish.setEnabled(false);
    }
    

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRecordManager.stop();
        audioStop();
    }
}
