package com.example.androidlearning.view.activity.mediastudy;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 多媒体技术的学习：SoundPoolRecyclerView的Adapter数据配置
 * */
public class SoundPoolRecyclerViewAdapter extends RecyclerView.Adapter<SoundPoolRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {
    private final List<SoundPoolActivity.Sound> data; // 传入输入的元素类型为 Sound类
    private final Context context;
    private final RecyclerView recyclerView;
    private OnItemClickListener listener;

    public SoundPoolRecyclerViewAdapter(List<SoundPoolActivity.Sound> data, RecyclerView recyclerView, Context context) {
        this.data = data;
        this.recyclerView = recyclerView;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 手动布局
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 50;
        layoutParams.leftMargin = 20;
        textView.setLayoutParams(layoutParams);
        textView.setOnClickListener(this); // TextView实现点击事件
        return new MyViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // data中为Sound类
        ((TextView) holder.itemView).setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // 实现TextView点击事件
    @Override
    public void onClick(View view) {
        if (listener != null) {
            // 得到点击的ListView的下标
            listener.onItemClick(recyclerView.getChildAdapterPosition(view));
        }
    }
    // 定义一个公开方法用于传入监听项
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    // 定义一个接口（监听点击项）
    interface OnItemClickListener {
        void onItemClick(int childAdapterPosition);
    }

    // 定义一个ViewHolder子类
    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
