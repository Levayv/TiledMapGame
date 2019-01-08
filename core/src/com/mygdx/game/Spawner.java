package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.enums.EntityTex;

class Spawner extends Something {
    public Spawner(EntityTex entityTex) {
        super(entityTex);
    }
    public void create(Group worldGroup){
//        Something mob = new Something(texReg);
//        mob.entityTex = EntityTex.Temp;
//        worldGroup.addActor(mob);
//        mob.setBorders();
//        mob.setPosition(200,200);
    }
}
