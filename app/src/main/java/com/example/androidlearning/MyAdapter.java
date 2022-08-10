package com.example.androidlearning;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    // 添加需要适配的属性
    private List<Bean> listData;
    private Context context;

    // 添加了属性 需要重写构建方法： Generator快捷键（Ctrl+Enter）Construction， Context意为场景，即外界调用MyAdapter方
    public MyAdapter(List<Bean> data, Context context) {
        this.listData = data;
        this.context = context;
    }

    // Generator快捷键（Ctrl+Enter）： Implement Methods 自动生成构造方法
    @Override
    // 获取数据条数
    public int getCount() {
        return listData.size(); // 返回data条数
    }

    @Override
    // 获取item项对象
    public Object getItem(int i) {
        return null;
    }

    @Override
    // 获取item项id
    public long getItemId(int i) {
        return i;   // 返回 参数i
    }

    @Override
    // 返回每一个item条目对象（类似在cellForRowAtIndexPath方法中处理UITableViewCell），先处理item的布局和数据设值加载 再将其返回item条目对象给外界调用方
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        // 复用机制：如果不存在view，才创建它
        if (view == null) {
            viewHolder = new ViewHolder();
            // 拿到item条目的布局，从外界场景中渲染list_item， 得到参数view
            view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
            // 将viewHolder的textView和 item项R.id的textView绑定（这样不会为每一个view调用耗时的findViewById了）
            viewHolder.textView = view.findViewById(R.id.tv);
            view.setTag(viewHolder); // 将viewHolder暂存到view的tag属性 （安卓的视图类自带一个tag属性，可以存放Object任意类型对象）
        } else  {
            viewHolder = (ViewHolder) view.getTag(); // 取出view的tag属性对象
        }
        // 拿到item上的textview （findViewById需要耗时 使用ViewHolder类来进行优化）
//        TextView textView = view.findViewById(R.id.tv);

        // 赋值viewHolder的textview（即view的textview）为listdata数组第i条数据（Bean的name属性）
        viewHolder.textView.setText(listData.get(i).getName());
        Log.e("正在加载", "getView第"+i+"条数据"); // 控制台打印
        return view;
    }

    // ViewHolder类中填写子控件（View持有内容类：listView的item项有几个子控件 这里就填写一个子控件）
    private final class ViewHolder {
        TextView textView;
    }
}
