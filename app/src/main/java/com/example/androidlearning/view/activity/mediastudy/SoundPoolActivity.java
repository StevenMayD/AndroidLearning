package com.example.androidlearning.view.activity.mediastudy;

import android.media.SoundPool;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidlearning.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 多媒体技术的学习：SoundPool播放音效
 * */
public class SoundPoolActivity extends AppCompatActivity implements SoundPoolRecyclerViewAdapter.OnItemClickListener {
    private SoundPool soundPool; // 音效播放组件(音效池)

    // 写一个java bean类管理Sound音效类
    static class Sound {
        String name;
        int soundId;

        public Sound(String name, int soundId) {
            this.name = name;
            this.soundId = soundId;
        }

        public String getName() {
            return name;
        }

        public int getSoundId() {
            return soundId;
        }
    }

    // 放置音效数据列表
    List<Sound> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_pool);

        // SoundPool组件 播放音效
        soundPool = new SoundPool.Builder().setMaxStreams(3).build();
        // 创建Sound音频类
        Sound sound1 = new Sound("bgm1", soundPool.load(this, R.raw.bgm1, 1));
        Sound sound2 = new Sound("bgm2", soundPool.load(this, R.raw.bgm2, 1));
        Sound sound3 = new Sound("bgm3", soundPool.load(this, R.raw.bgm3, 1));
        // 添加音频对象
        data = new ArrayList<>();
        data.add(sound1);
        data.add(sound2);
        data.add(sound3);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);

        SoundPoolRecyclerViewAdapter adapter = new SoundPoolRecyclerViewAdapter(data, recyclerView, this);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int childAdapterPosition) {
        // 获取点击项数据
        Sound sound = data.get(childAdapterPosition);
        // SoundPool播放音效（音效id，左声道音量，右声道音量，优先级，是否循环播放，速率）
        soundPool.play(sound.getSoundId(), 0.5f, 0.5f, 1, 0, 1.0f);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 当前页面销毁时 释放音效
        for (Sound datum : data) {
            soundPool.unload(datum.getSoundId()); // 卸载音效
        }
        soundPool.release(); // 释放音效
    }
}