package com.heyl.magicwater.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyl on 2016/11/11.
 */

public class EasyData {

    private static List<GameDataModel> gamedata = new ArrayList<>();

    public static List<GameDataModel> getEasyData(){
        gamedata.clear();
        gamedata.add(new GameDataModel(0,4,3,2,1,4));
        gamedata.add(new GameDataModel(15,3,10,6,2,10));
        gamedata.add(new GameDataModel(0,3,8,4,2,8));
        gamedata.add(new GameDataModel(19,10,6,8,7,19));
        gamedata.add(new GameDataModel(0,5,8,4,2,5));
        gamedata.add(new GameDataModel(8,9,4,3,5,8));
        gamedata.add(new GameDataModel(0,7,10,6,4,10));
        gamedata.add(new GameDataModel(0,7,3,6,2,3));
        gamedata.add(new GameDataModel(5,9,7,6,6,7));
        gamedata.add(new GameDataModel(3,7,10,7,2,10));
        gamedata.add(new GameDataModel(5,8,10,7,4,5));
        gamedata.add(new GameDataModel(7,4,10,7,5,7));
        gamedata.add(new GameDataModel(5,7,10,7,6,7));
        gamedata.add(new GameDataModel(5,8,10,7,1,10));
        gamedata.add(new GameDataModel(3,5,8,7,4,8));
        gamedata.add(new GameDataModel(3,6,10,8,5,10));
        gamedata.add(new GameDataModel(3,11,17,7,2,3));
        gamedata.add(new GameDataModel(5,8,10,8,1,10));
        gamedata.add(new GameDataModel(3,7,10,7,8,10));
        gamedata.add(new GameDataModel(4,7,10,8,5,10));
        gamedata.add(new GameDataModel(7,9,12,4,10,12));
        gamedata.add(new GameDataModel(4,5,11,5,2,4));
        gamedata.add(new GameDataModel(4,9,14,5,6,9));
        gamedata.add(new GameDataModel(3,8,17,5,13,17));
        gamedata.add(new GameDataModel(7,11,15,6,10,11));
        gamedata.add(new GameDataModel(3,12,17,4,8,17));
        gamedata.add(new GameDataModel(8,11,16,5,13,16));
        gamedata.add(new GameDataModel(11,17,23,6,18,23));
        gamedata.add(new GameDataModel(2,19,25,6,15,19));
        gamedata.add(new GameDataModel(5,9,16,6,3,9));
        gamedata.add(new GameDataModel(7,9,18,6,5,9));
        gamedata.add(new GameDataModel(4,17,19,3,13,17));
        gamedata.add(new GameDataModel(2,7,8,6,3,7));
        gamedata.add(new GameDataModel(6,13,21,4,15,21));
        gamedata.add(new GameDataModel(5,7,21,4,9,21));
        gamedata.add(new GameDataModel(12,9,17,6,4,12));
        gamedata.add(new GameDataModel(4,9,16,6,1,9));
        gamedata.add(new GameDataModel(7,16,21,4,14,21));
        gamedata.add(new GameDataModel(3,10,17,5,1,17));
        gamedata.add(new GameDataModel(22,15,9,6,4,22));
        gamedata.add(new GameDataModel(13,8,24,4,19,24));
        gamedata.add(new GameDataModel(3,17,25,5,6,17));
        gamedata.add(new GameDataModel(2,15,27,5,1,15));
        gamedata.add(new GameDataModel(13,15,23,4,2,15));
        gamedata.add(new GameDataModel(7,9,19,6,4,7));
        gamedata.add(new GameDataModel(3,13,19,6,4,19));
        gamedata.add(new GameDataModel(4,12,17,6,9,17));
        gamedata.add(new GameDataModel(8,11,23,4,15,23));
        gamedata.add(new GameDataModel(5,13,18,6,8,18));
        gamedata.add(new GameDataModel(11,15,3,5,4,11));
        return  gamedata;
    }
}
