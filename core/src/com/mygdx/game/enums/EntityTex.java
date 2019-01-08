package com.mygdx.game.enums;

public enum EntityTex {
    None(0),
    Player(1),
    NPC(2),
    Tower(3),
    Tree(11),
    Stone(12),
    Ore(13),
    Temp(66);

    int id;
    private EntityTex(int i){id = i;}
    public int getID(){return id;}
//    public boolean IsEmpty(){return this.equals(EntityTex.None);}
//    public boolean Compare(int i){return id == i;}
//    public static EntityTex GetValue(int _id)
//    {
//        EntityTex[] As = EntityTex.values();
//        for(int i = 0; i < As.length; i++)
//        {
//            if(As[i].Compare(_id))
//                return As[i];
//        }
//        return EntityTex.None;
//    }
}
