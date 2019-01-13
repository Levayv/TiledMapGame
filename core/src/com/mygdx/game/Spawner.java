package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.enums.entity.EntityTex;
import com.mygdx.game.world.Factory;

public class Spawner extends Something {
    public Spawner() {
        super();
    }
    public void create(Factory factory, Vector2 pos){
        Something mob; //= new Npc(Entity.Temp,EntityAnimation.PUMPKIN);
        int lastActorIndex = factory.somethingsOnDuty.size();
        factory.buildOnEvent(EntityTex.Temp,pos);
        mob = factory.somethingsOnDuty.get(lastActorIndex);
        System.out.println("!!!");
    }
}
