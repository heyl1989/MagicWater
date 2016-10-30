package com.heyl.magicwater.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.heyl.magicwater.R;
import com.heyl.magicwater.base.MyBaseAdapter;
import java.util.List;

/**
 * Created by heyl on 2016/10/25.
 */
public class LevelAdapter extends MyBaseAdapter{

    public LevelAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(null == convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_level,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.imgLevel.setImageResource(R.drawable.level_lock);
        return convertView;
    }

    class ViewHolder{
        ImageView imgLevel;
        public ViewHolder(View convertView) {
            imgLevel = (ImageView)convertView.findViewById(R.id.img_level);
        }
    }
}
