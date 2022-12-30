package com.example.androidlearning.view.activity.mediastudy;

import android.hardware.Camera;
import android.media.MediaRecorder;
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
 * 多媒体技术的学习：录制视频界面
 * */
public class MediaRecordActivity extends AppCompatActivity implements View.OnClickListener {
    private TextureView textureView; // 视频采集预览界面
    private Button btnOpt;
    private MediaRecorder mediaRecorder;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_record);

        textureView = findViewById(R.id.textureView);
        btnOpt = findViewById(R.id.btn_opt);
        btnOpt.setOnClickListener(this); // 要求MediaRecordActivity遵循接口 View.OnClickListener
    }

    @Override
    public void onClick(View view) {
        CharSequence text = btnOpt.getText();
        if (TextUtils.equals(text, "开始录制")) {
            btnOpt.setText("结束录制");

            camera = Camera.open(); //  打开摄像头
            camera.setDisplayOrientation(90);
            camera.unlock();

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setCamera(camera); // 配置摄像头
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);        // 设置音频源：麦克风
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);     // 设置视频源：摄像头
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);   // 指定视频文件格式：MP4
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);      // 设置音频编码
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);     // 设置视频编码
            /**
             * 注意安卓录制视频时 默认拍摄预览的画面是逆时针旋转了90度，这里需要做修正
             * 包括修正：
             *  1、输出的录制视频画面：设置setOrientationHint(90)
             *  2、录制视频时的预览画面：需要使用Camera对象 给摄像头设置旋转90度 setDisplayOrientation(90)
             * */
            mediaRecorder.setOrientationHint(90); // 输出的录制视频画面 旋转90度
            // 设置视频输出文件：指定输出路径 为app私有目录：文件管理-我的手机-Android-data-com.example.androidlearning-files- a.mp4
            mediaRecorder.setOutputFile(new File(getExternalFilesDir(""), "a.mp4").getAbsolutePath());
            mediaRecorder.setVideoSize(640, 480); // 设置视频文件尺寸
//            mediaRecorder.setVideoFrameRate(30); // 设置帧率：每秒呈现的画面张数
            mediaRecorder.setPreviewDisplay(new Surface(textureView.getSurfaceTexture())); // 设置摄像头预览画布
            try {
                mediaRecorder.prepare(); // 进入准备阶段
                mediaRecorder.start(); // 进入录制状态
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            btnOpt.setText("开始录制");

            mediaRecorder.stop(); // 停止录制
            mediaRecorder.release(); // 释放视频录制器 资源
            camera.stopPreview(); // 关闭摄像头
            camera.release(); // 释放摄像头 资源
        }
    }
}