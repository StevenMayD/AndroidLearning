package com.example.androidlearning.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidlearning.R;

import java.util.ArrayList;
import java.util.List;

/* ViewPager2的适配数据的类 拓展自RecyclerView的Adapter
*   且 RecyclerView.Adapter需要传一个 拓展自ViewHolder的泛型类： public abstract static class Adapter<VH extends ViewHolder>
    所以传入 自定义的ViewPagerViewHolder
* */
public class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewPagerViewHolder> {
    // 添加一个属性 用于保存viewpager各个滑动页面数据的数组
    private List<String> titleList = new ArrayList<>();
    private List<Integer> colorsList = new ArrayList<>();

    // 添加新的构造方法
    public ViewPager2Adapter(List<String> titleList, List<Integer> colorsList) {
        this.titleList = titleList;
        this.colorsList = colorsList;
    }

    @NonNull
    @Override
    // 这里需要返回一个RecyclerView.ViewHolder类，我们使用一个自定义的子类ViewPagerViewHolder
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Adapter数据适配类 和 界面layout_view_pager2_item进行绑定：这里adapter适配的是 自定义创建的layout_view_pager2_item.xml界面 (像MainActivity中loadViewPage初次使用ViewPager一样)
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext()); // 拿到上下文的布局渲染器
        View itemView = layoutInflater.inflate(R.layout.layout_view_pager2_item, parent, false); // 渲染layout_view_pager2_item界面
        return new ViewPagerViewHolder(itemView); // 传参itemView 并返回ViewHolder
    }

    @Override
    // 绑定滑动视图上每一个界面和数据 （这里重写方法 RecyclerView.ViewHolder类型参数 也需要给成ViewPagerViewHolder类型参数）
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
        // 给ViewHolder属性赋值（既然是ViewPager绑定的holder，滚动时就是给不同的滚动页面进行赋值）
        holder.mTv.setText(titleList.get(position));
        // 给layout设置背景色（由于添加颜色数据时colorList.add(R.color.teal_200) 是添加资源id，这里就应该使用setBackgroundResource，而不是setBackgroundColor）
        holder.mContainer.setBackgroundResource(colorsList.get(position));
    }

    @Override
    // viewpager滚动页面个数
    public int getItemCount() {
        return 5;
    }

    // 定义一个内部类ViewHolder：用于处理数据和界面（从而实现adapter的数据适配功能）
    class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        // 定义的属性与界面layout_view_pager2.xml上的控件一一对应
        TextView mTv;
        RelativeLayout mContainer;

        // ViewHolder的构建需要传参一个界面进来进行绑定（这个界面就是自定义的界面layout_view_pager2_item.xm）
        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);

            mContainer = itemView.findViewById(R.id.viewPager2container);
            mTv = itemView.findViewById(R.id.viewPager2TextView);
        }
    }
}
