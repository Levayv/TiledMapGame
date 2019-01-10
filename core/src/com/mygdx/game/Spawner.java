package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.enums.Entity;

public class Spawner extends Something {
    public Spawner(Entity entity) {
        super(entity);
    }
    public void create(Group worldGroup){
//        Something mob = new Something(texReg);
//        mob.entity = Entity.Temp;
//        worldGroup.addActor(mob);
//        mob.setBorders();
//        mob.setPosition(200,200);
    }
}
