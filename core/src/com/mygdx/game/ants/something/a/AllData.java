package com.mygdx.game.ants.something.a;

import com.mygdx.game.enums.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class AllData {
    private SomethingData[] somethingData; //todo more more data !
    private transient SomethingData somethingDataBuffer;
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
    public int size(){
        return somethingData.length;
    }
    public SomethingData getSomethingDataByID(int entityID){
        for (int i = 0; i < somethingData.length; i++) {
            if(somethingData[i].entityID == entityID){
                somethingDataBuffer = somethingData[i];
                break; //todo research for optimisation
            }
        }
        return somethingDataBuffer;
    }
}
