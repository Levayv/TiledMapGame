package com.mygdx.game.enums.entity;

import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.ants.something.a.SomethingData;

public enum EntityTex {
    //todo use ENUM.ordinal() !!!
    None(0),
    Player(1),
    NPC(2),
    Tower(3),
    Spawner(4),
    Tree(11),
    Stone(12),
    Ore(13),
    Door(14),
    Ghost1(15),
    Ghost2(16),
    Temp(66);

    int id;
    EntityTex(int i){id = i;}
    public int getID(){return id;}
//    public SomethingData data(){return data;}
//    public void setData(SomethingData data){this.data = data;}

//    public boolean IsEmpty(){return this.equals(Entity.None);}
//    public boolean Compare(int i){return id == i;}
//    public static Entity GetValue(int _id)
//    {
//        Entity[] As = Entity.values();
//        for(int i = 0; i < As.length; i++)
//        {
//            if(As[i].Compare(_id))
//                return As[i];
//        }
//        return Entity.None;
//    }
}
