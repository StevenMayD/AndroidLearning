package com.example.androidlearning.view.activity.mediastudy;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;

/**
 * 多媒体技术的学习：录制视频、播放视频、播放音效
 * */
public class MediaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        // Android6.0+需要进行动态权限申请：界面申请权限（AndroidManifest.xml中还需要给app添加相应权限）
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 100);
    }

    // 录制视频
    public void record(View view) {
        startActivity(new Intent(this, MediaRecordActivity.class));
    }

    // MediaPlayer播放视频
    public void mediaPlay(View view) {
        startActivity(new Intent(this, VideoPlayActivity.class));
    }

    // VideoView播放视频
    public void videoView(View view) {
        startActivity(new Intent(this, VideoViewActivity.class));
    }


    // 播放音效
    public void playAudio(View view) {
        startActivity(new Intent(this, SoundPoolActivity.class));
    }
}