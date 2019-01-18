package com.mygdx.game.ants.something.animated.event.npc.a;

import com.mygdx.game.enums.entity.EntityAnimation;

public class NpcData {
    public String entityName;
    public int entityID;

    public boolean isCollider;
    public boolean isRigid;
    public boolean isLiquid;
    public boolean isFlammable;

    public int test = 1;
    public void setExample(int i){
        entityID = EntityAnimation.values()[i].getID();
        entityName = EntityAnimation.values()[i].name();

        isCollider = false;
        isRigid = false;
        isLiquid = false;
        isFlammable = false;

        test = 111;
    }
}
