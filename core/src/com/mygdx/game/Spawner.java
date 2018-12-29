package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.ants.Something;
import com.mygdx.game.enums.Entity;

class Spawner extends Something {
    public Spawner(TextureRegion texReg) {
        super(texReg);
    }
    public void create(Group worldGroup){
        Something mob = new Something(texReg);
        mob.entity = Entity.Temp;
        worldGroup.addActor(mob);
        mob.setBorders();
        mob.setPosition(200,200);
    }
}
