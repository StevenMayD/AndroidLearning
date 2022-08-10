package com.example.androidlearning;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/* RecyclerView的Adapter继承自androidx包的RecyclerView的Adapter
*  并且这里有红线报错：自定义的MyViewHolder需要继承自RecyclerView.ViewHolder （鼠标聚焦在红线，可以展示提示，点击可以自动构建方法）
* */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    private List<Bean> listData;
    private Context context;

    public RecycleViewAdapter(List<Bean> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 拿到item条目的布局：              用外界场景中list_item渲染一个View 得到参数view
        View view = View.inflate(context, R.layout.list_item, null);
        // RecyclerView里 是通过将view传递给MyViewHolder处理view，来返回
        return new MyViewHolder(view);
    }

    @Override
    // 绑定数据
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv.setText(listData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    // ViewHolder类中填写子控件
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
