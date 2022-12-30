package com.example.androidlearning.view.activity.mediastudy;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;

import java.io.File;
import java.io.IOException;

/**
 * 多媒体技术的学习：播放视频界面
 * */
public class VideoPlayActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private TextureView textureView; // 视频采集预览界面
    private Button btnOpt;
    private MediaPlayer mediaPlayer; // 视频播放器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        textureView = findViewById(R.id.textureView);
        btnOpt = findViewById(R.id.btn_opt);
        btnOpt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        CharSequence text = btnOpt.getText();
        if (TextUtils.equals(text, "开始播放")) {
            btnOpt.setText("结束播放");

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);    // 设置 视频播放准备状态 的监听
            mediaPlayer.setOnCompletionListener(this);  // 设置 视频播放完成状态 的监听
//            mediaPlayer.setLooping(true); // 设置循环播放

            try {
                mediaPlayer.setDataSource(new File(getExternalFilesDir(""), "a.mp4").getAbsolutePath()); // 指定播放的视频资源
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.setSurface(new Surface(textureView.getSurfaceTexture())); // 设置视频播放的画布（否则只有声音 没有画面）
            mediaPlayer.prepareAsync(); // 视频播放准备（异步准备）
            mediaPlayer.getDuration(); // 获取视频时长
            mediaPlayer.getVideoWidth(); // 获取视频尺寸
        } else {
            btnOpt.setText("开始播放");

            mediaPlayer.pause(); // 暂停播放
            mediaPlayer.stop(); // 停止播放
            mediaPlayer.release(); // 释放资源 结束mediaPlayer生命周期
        }
    }

    // 监听播放器的准备状态
    @Override
    public void onPrepared(MediaPlayer mediaPlayer2) {
        mediaPlayer.start(); // 播放视频（监听到视频播放器准备好了）
    }

    // 监听播放器的播放完成状态
    @Override
    public void onCompletion(MediaPlayer mediaPlayer2) {
        btnOpt.setText("开始播放");

        mediaPlayer.release();
    }
}