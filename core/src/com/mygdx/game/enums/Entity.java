package com.mygdx.game.enums;

public enum Entity {
    None(0),
    Wood(1),
    Stone(2),
    Ore(3);

    int id;
    private Entity(int i){id = i;}

    public int GetID(){return id;}
    public boolean IsEmpty(){return this.equals(Entity.None);}
    public boolean Compare(int i){return id == i;}
    public static Entity GetValue(int _id)
    {
        Entity[] As = Entity.values();
        for(int i = 0; i < As.length; i++)
        {
            if(As[i].Compare(_id))
                return As[i];
        }
        return Entity.None;
    }
}
