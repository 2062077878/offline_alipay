package com.wu.alipay.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wu.alipay.R;

import java.util.List;

/**
 * 键盘界面Adapter
 * Created by Administrator on 2016/8/11.
 */
public class PasswordKeyboardAdapter extends BaseAdapter{
    private Context context;
    private List<String> values;

    public PasswordKeyboardAdapter(Context context,List<String> values){
        this.context = context;
        this.values = values;
    }
    @Override
    public int getCount() {
        if(values !=null){
            return values.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(values!=null){
            return values.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup viewGroup) {
        ViewHolder viewHolder =null;
        if(converView ==null){
            converView = LayoutInflater.from(context).inflate(R.layout.item_password_keyboard, null);
            viewHolder = new ViewHolder();
            viewHolder.itemBg = (LinearLayout)converView.findViewById(R.id.ll_item_keyboard_bg);
            viewHolder.tvValue = (TextView)converView.findViewById(R.id.tv_item_keyboard_number);

            converView.setTag(viewHolder);
        }else {
            viewHolder =(ViewHolder)converView.getTag();
        }
        viewHolder.tvValue.setText(values.get(position));
        if(position ==9||position==11){
            viewHolder.itemBg.setBackgroundColor(context.getResources().getColor(R.color.input_key_202020));
            viewHolder.tvValue.setTextColor(context.getResources().getColor(R.color.gray_ff888888));
        }
        return converView;
    }

    class ViewHolder{
        LinearLayout itemBg;
        TextView tvValue;
    }
}
