package com.mygdx.game.ants.something.a;

import java.util.ArrayList;
import java.util.List;

public class AllData {
    private SomethingData[] somethingData;
//    public List<SomethingData> data2;
    public void setExample(int entityLength) {
        somethingData = new SomethingData[entityLength];
//        data2 = new ArrayList<SomethingData>();
        for (int i = 0; i < entityLength; i++) {
            somethingData[i] = new SomethingData();
            somethingData[i].setExample(i);
//            data2.add(data1[i]);
        }
    }
    public SomethingData getSomethingDataByIndex(int index){
        return somethingData[index];
    }
}
