package com.mygdx.game.ants.something.a;

import com.mygdx.game.enums.entity.Entity;

public class SomethingData {
    public String entityName;
    public int entityID;

    public boolean isCollider;
    public boolean isRigid;
    public boolean isLiquid;
    public boolean isFlammable;

    public int test = 1;
    public void setExample(int i){
        entityID = Entity.values()[i].getID();
        entityName = Entity.values()[i].name();

        isCollider = false;
        isRigid = false;
        isLiquid = false;
        isFlammable = false;

        test = 111;
    }
}
