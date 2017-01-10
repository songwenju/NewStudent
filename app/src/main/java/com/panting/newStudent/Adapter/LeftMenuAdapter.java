package com.panting.newStudent.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.panting.newStudent.R;
import com.panting.newStudent.common.AppConstants;

import java.util.HashMap;
import java.util.List;

/**
 * 左侧菜单对应的viewHolder
 */
public class LeftMenuAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<String> mLeftPageIcons;
    private int mWindowHeight;
    private int mWindowWidth;


    /**
     * 点击事件的监听接口
     */
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public LeftMenuAdapter(Context context, List<String> leftPageIcons, HashMap<String, Integer> windowSize) {
        mContext = context;
        mLeftPageIcons = leftPageIcons;
        mWindowHeight = windowSize.get(AppConstants.WINDOW_HEIGHT);
        mWindowWidth = windowSize.get(AppConstants.WINDOW_WIDTH);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public class NormalViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView iconName;

        public NormalViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.img_icon);
            iconName = (TextView) itemView.findViewById(R.id.tv_icon_name);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) icon.getLayoutParams();
            params.height = mWindowHeight / 9;
            icon.setLayoutParams(params);
            params = (LinearLayout.LayoutParams) iconName.getLayoutParams();
            params.width = mWindowWidth - icon.getWidth();
            iconName.setLayoutParams(params);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //在该方法中我们创建一个ViewHolder并返回，ViewHolder必须有一个带有View的构造函数，
        //这个View就是我们Item的根布局，在这里我们使用自定义Item的布局；
        View item = View.inflate(mContext, R.layout.item_left, null);
        return new NormalViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //将数据与界面进行绑定的操作
        NormalViewHolder normalHolder = (NormalViewHolder) holder;
//        normalHolder.icon.setImageResource(mLeftPageIcons.get(position).icon);
        normalHolder.iconName.setText("学校信息");

        if (mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //获取数据的数量
        return mLeftPageIcons == null ? 0 : mLeftPageIcons.size();
    }
}
