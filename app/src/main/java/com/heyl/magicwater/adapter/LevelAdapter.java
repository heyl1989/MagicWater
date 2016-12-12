package com.heyl.magicwater.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heyl.magicwater.R;
import com.heyl.magicwater.base.MyBaseAdapter;
import com.heyl.magicwater.comman.Content;
import com.heyl.magicwater.util.SPCash;

import java.util.List;

/**
 * Created by heyl on 2016/10/25.
 */
public class LevelAdapter extends MyBaseAdapter {

    private String grade;
    private int level;
    private String gradeLevel;

    public LevelAdapter(Context context, List list, String grade) {
        super(context, list);
        this.grade = grade;
        initData();
    }

    /**
     * 判断关卡等级
     * 判断是否过关了，未过关和未解锁
     */
    public void initData() {
        //如果是容易
        if (grade.equals(Content.GRADE_ONE)) {
            level = SPCash.getData("success_one", -1);
            gradeLevel = "success_one_star_";
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_level, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //如果已过关数小于等于position，判断放的星星
        if (position <= level) {
            switch (SPCash.getData(gradeLevel + "" + position, 0)) {
                case 1:
                    viewHolder.imgLevel.setImageResource(R.drawable.level_one_star);
                    break;
                case 2:
                    viewHolder.imgLevel.setImageResource(R.drawable.levlel_two_star);
                    break;
                case 3:
                    viewHolder.imgLevel.setImageResource(R.drawable.level_three_star);
                    break;
            }
        }
        //要过的关
        if (position == level + 1) {
            viewHolder.imgLevel.setImageResource(R.drawable.level_start);
        }
        //锁定的关卡
        if (position > level + 1) {
            viewHolder.imgLevel.setImageResource(R.drawable.level_lock);
        }
        viewHolder.tvLevel.setText(position+1+"");
        return convertView;
    }

    class ViewHolder {
        ImageView imgLevel;
        TextView tvLevel;

        public ViewHolder(View convertView) {
            imgLevel = (ImageView) convertView.findViewById(R.id.img_level);
            tvLevel = (TextView) convertView.findViewById(R.id.tv_level);
        }
    }
}
