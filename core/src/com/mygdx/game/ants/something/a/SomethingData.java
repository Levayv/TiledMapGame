package com.mygdx.game.ants.something.a;

import com.mygdx.game.enums.entity.EntityTex;

public class SomethingData {
    public String entityName;
    public int entityID;

    public boolean isCollider;
    public boolean isRigid;
    public boolean isLiquid;
    public boolean isFlammable;

    public int test = 1;
    public void setExample(int i){
        entityID = EntityTex.values()[i].getID();
        entityName = EntityTex.values()[i].name();

        isCollider = false;
        isRigid = false;
        isLiquid = false;
        isFlammable = false;

        test = 111;
    }
}
