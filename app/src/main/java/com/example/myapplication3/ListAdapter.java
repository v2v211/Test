package com.example.myapplication3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context mContext; // 保存上下文引用
    private List<costList> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public ListAdapter(Context context, List<costList> list) {
        mContext = context; // 保存上下文引用
        mList = list;
        // 通过外部传来的Context初始化LayoutInflater对象
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            // 如果convertView为空，说明当前没有可重用的视图，需要创建新的视图
            convertView = mLayoutInflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
            viewHolder.tvDate = convertView.findViewById(R.id.tv_date);
            viewHolder.tvMoney = convertView.findViewById(R.id.tv_money);
            convertView.setTag(viewHolder);
        } else {
            // 如果convertView不为空，说明有可重用的视图，直接从tag中获取viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }
        costList item = mList.get(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });
        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.tvDate.setText(item.getDate());
        viewHolder.tvMoney.setText(item.getMoney());

        return convertView;
    }



    // 内部类ViewHolder，用于保存item中的控件实例，避免重复调用findViewById
    static class ViewHolder {
        TextView tvTitle;
        TextView tvDate;
        TextView tvMoney;
    }
}
