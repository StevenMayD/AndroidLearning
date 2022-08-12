package com.example.androidlearning.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.androidlearning.R;
import com.example.androidlearning.bean.Bean;

/* RecyclerView的Adapter继承自androidx包的RecyclerView的Adapter
*  并且这里有红线报错：自定义的MyViewHolder需要继承自RecyclerView.ViewHolder （鼠标聚焦在红线，可以展示提示，点击可以自动构建方法）
* */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    // 给RecycleViewAdapter类 声明两个属性
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

    // 定义一个类：ViewHolder类中填写子控件
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);

            // 给item项 设置一个点击方法的监听：当点击时item时，触发onClick方法
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        // 让属性(接口类型的一个属性)调用接口中的方法
                        itemClickListener.itemClick(getBindingAdapterPosition()); // getBindingAdapterPosition()为系统方法：获取此时item所处的位置号
                    }
                }
            });
        }
    }

    // 声明一个接口类型的对象（给RecycleViewAdapter类声明一个属性）
    private RecycleItemClickListener itemClickListener;

    // 给外界设置一个处理点击事件的方法
    public void  setRecycleItemClickLinstener(RecycleItemClickListener linstener) {
        // itemClickListener属性赋值成外界的传值（这样内部点击时 外界可响应处理）
        itemClickListener = linstener;
    }

    // 定义一个接口：由于RecyclerView系统没有点击事件，需要自定义点击方法 给外界调用
    public interface RecycleItemClickListener {
        // 接口中定义一个方法：处理点击第position个item的事件 （由RecycleViewAdapter调用时 去实现方法内容）
        void itemClick(int position);
    }
}
