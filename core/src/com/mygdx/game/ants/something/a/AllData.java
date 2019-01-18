package com.mygdx.game.ants.something.a;


import com.mygdx.game.ants.something.animated.event.npc.a.NpcData;
import com.mygdx.game.enums.entity.EntityAnimation;
import com.mygdx.game.enums.entity.EntityTex;

import java.util.ArrayList;
import java.util.List;

public class AllData {
    private SomethingData[] somethingData; //todo more more data !
    private NpcData[] npcData; //todo more more data !
    private transient SomethingData somethingDataBuffer;
    private transient NpcData npcDataBuffer;
//    public List<SomethingData> data2;
    public void setExample() {
        int entityTexLength = EntityTex.values().length;
        int entityAnimLength = EntityAnimation.values().length;
        somethingData = new SomethingData[entityTexLength];
        npcData = new NpcData[entityAnimLength];
//        data2 = new ArrayList<SomethingData>();
        for (int i = 0; i < entityTexLength; i++) {
            somethingData[i] = new SomethingData();
            somethingData[i].setExample(i);
        }
        for (int i = 0; i < entityAnimLength; i++) {
            npcData[i] = new NpcData();
            npcData[i].setExample(i);
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
    public NpcData getNpcDataByID(int entityID){
        for (int i = 0; i < somethingData.length; i++) {
            if(somethingData[i].entityID == entityID){
                somethingDataBuffer = somethingData[i];
                break; //todo research for optimisation
            }
        }
        return npcDataBuffer;
    }
}
