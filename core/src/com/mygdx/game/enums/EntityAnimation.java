package com.mygdx.game.enums;

public enum EntityAnimation {
    NONE(0),
    DOOR_OPEN(1),
    DOOR_CLOSE(2),
    PUMPKIN(3),
    SLIME_1(11),
    SLIME_2(12),
    SLIME_3(13),
    EXPLOSION(14),
    TEMP(66);

    int id;
    private EntityAnimation(int i){id = i;}

    public int GetID(){return id;}
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

