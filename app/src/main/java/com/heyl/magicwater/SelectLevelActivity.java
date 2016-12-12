package com.heyl.magicwater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.heyl.magicwater.adapter.LevelAdapter;
import com.heyl.magicwater.base.BaseActivity;
import com.heyl.magicwater.comman.Content;
import com.heyl.magicwater.model.EasyData;
import com.heyl.magicwater.model.GameDataModel;
import com.heyl.magicwater.util.SPCash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectLevelActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private List<GameDataModel> gameData = new ArrayList<>();
    private LevelAdapter levelAdapter;
    private String grade;
    private int level;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_select_level);

        Bundle bundle = getIntent().getExtras();
        grade = bundle.getString("grade");
        GridView gv = (GridView) findViewById(R.id.gv_level);
        levelAdapter = new LevelAdapter(this, gameData,grade);
        gv.setAdapter(levelAdapter);
        gv.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        //如果是容易
        if (grade.equals(Content.GRADE_ONE)) {
            level = SPCash.getData("success_one", -1);
        }
        gameData.clear();
        levelAdapter.initData();
        initData();
        super.onResume();
    }

    private void initData() {
        //容易
        if (grade.equals(Content.GRADE_ONE)) {
            gameData.addAll(EasyData.getEasyData());
            levelAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position > level + 1) {
            return;
        }
        clickMusic();
        Bundle bundle = new Bundle();
        bundle.putSerializable("gamedata", (Serializable) gameData.get(position));
        bundle.putString("grade",grade);
        bundle.putInt("level",position);
        goOther(GameActivity.class, bundle);
    }
}
