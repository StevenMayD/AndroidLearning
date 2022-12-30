package com.example.androidlearning.view.activity.mediastudy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;

import java.io.File;


/**
 * 多媒体技术的学习：VideoView播放视频界面
 * */
public class VideoViewActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        VideoView videoView = findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(this);
        mediaController.setPrevNextListeners(this, this); // 监听点击 上一视频/下一视频 按钮
        videoView.setMediaController(mediaController); // 设置视频控制器
        videoView.setVideoPath(new File(getExternalFilesDir(""), "a.mp4").getAbsolutePath()); // 设置播放资源
        videoView.start(); // videoView播放视频
    }

    // 点击上一曲/下一曲的监听
    @Override
    public void onClick(View view) {
        Log.d("VideoViewActivity", "VideoView onClick");
    }
}