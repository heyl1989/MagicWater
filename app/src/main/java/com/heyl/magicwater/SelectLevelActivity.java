package com.heyl.magicwater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.heyl.magicwater.adapter.LevelAdapter;
import com.heyl.magicwater.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class SelectLevelActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void initView() {
        setContentView(R.layout.activity_select_level);

        GridView gv = (GridView) findViewById(R.id.gv_level);
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        LevelAdapter levelAdapter = new LevelAdapter(this,list);
        gv.setAdapter(levelAdapter);
        gv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        goOther(GameActivity.class);
    }
}
