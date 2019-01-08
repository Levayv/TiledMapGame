package com.mygdx.game.enums;

public enum Entity {
    None(0),
    Player(1),
    NPC(2),
    Tower(3),
    Tree(11),
    Stone(12),
    Ore(13),
    Door(14),
    Temp(66);

    int id;
    Entity(int i){id = i;}
    public int getID(){return id;}
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
