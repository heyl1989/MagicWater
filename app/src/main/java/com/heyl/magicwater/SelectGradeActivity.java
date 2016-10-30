package com.heyl.magicwater;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.heyl.magicwater.base.BaseActivity;

public class SelectGradeActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void initView() {
        setContentView(R.layout.activity_select_grade);

        ImageView levelOne = (ImageView)findViewById(R.id.img_level_one);
        levelOne.setOnClickListener(this);
        ImageView levelTwo = (ImageView)findViewById(R.id.img_level_two);
        levelTwo.setOnClickListener(this);
        ImageView levelThree = (ImageView)findViewById(R.id.img_level_three);
        levelThree.setOnClickListener(this);
        ImageView levelFour = (ImageView)findViewById(R.id.img_level_four);
        levelFour.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.img_level_one:
                bundle.putString("grade","one");
                goOther(SelectLevelActivity.class,bundle);
                break;
            case R.id.img_level_two:
                bundle.putString("grade","two");
                goOther(SelectLevelActivity.class,bundle);
                break;
            case R.id.img_level_three:
                bundle.putString("grade","three");
                goOther(SelectLevelActivity.class,bundle);
                break;
            case R.id.img_level_four:
                bundle.putString("grade","four");
                goOther(SelectLevelActivity.class,bundle);
                break;
        }


    }
}
