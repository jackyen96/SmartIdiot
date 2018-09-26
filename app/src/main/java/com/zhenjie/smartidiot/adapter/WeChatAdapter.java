package com.zhenjie.smartidiot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhenjie.smartidiot.R;
import com.zhenjie.smartidiot.entity.WeChatData;

import java.util.List;

/**
 * 文件名: WeChatAdapter
 * 创建者: Jack Yan
 * 创建日期: 2018/9/26 6:39 PM
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：微信精选adapter
 */
public class WeChatAdapter extends BaseAdapter {


    private Context mContext;
    private LayoutInflater inflater;
    private List<WeChatData> mList;
    private WeChatData data;

    public WeChatAdapter(Context context,List<WeChatData> list){
        mContext = context;
        mList = list;
        inflater = LayoutInflater.from(mContext);
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
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.wechat_item,null);
            viewHolder.ivImage = convertView.findViewById(R.id.iv_image);
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
            viewHolder.tvSource = convertView.findViewById(R.id.tv_source);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        data = mList.get(position);
        viewHolder.tvTitle.setText(data.getTitle());
        viewHolder.tvSource.setText(data.getSource());
        return convertView;
    }

    class ViewHolder{
        private ImageView ivImage;
        private TextView tvTitle;
        private TextView tvSource;
    }
}
