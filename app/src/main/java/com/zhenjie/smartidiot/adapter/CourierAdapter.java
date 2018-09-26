package com.zhenjie.smartidiot.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhenjie.smartidiot.R;
import com.zhenjie.smartidiot.entity.ExpressData;

import java.util.List;

public class CourierAdapter extends BaseAdapter{

    private Context mContext;
    private List<ExpressData> mList;
    private LayoutInflater inflater;
    private ExpressData data;
    private ViewHolder viewHolder = null;

    public CourierAdapter(Context context,List<ExpressData> list){
        mContext = context;
        mList = list;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_courier,null);
            viewHolder.tvRemark = convertView.findViewById(R.id.tv_remark);
            viewHolder.tvZone = convertView.findViewById(R.id.tv_zone);
            viewHolder.tvDatetime = convertView.findViewById(R.id.tv_datetime);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        data = mList.get(position);
        viewHolder.tvRemark.setText(data.getRemark());
        viewHolder.tvDatetime.setText(data.getDateTime());
        viewHolder.tvZone.setText(data.getZone());
        return convertView;
    }

    class ViewHolder{
        private TextView tvRemark;
        private TextView tvZone;
        private TextView tvDatetime;
    }
}
