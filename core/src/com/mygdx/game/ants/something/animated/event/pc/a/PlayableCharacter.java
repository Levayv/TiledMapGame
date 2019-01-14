package com.mygdx.game.ants.something.animated.event.pc.a;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.ants.something.animated.event.npc.a.Npc;

import com.mygdx.game.enums.entity.EntityClass;
import com.mygdx.game.enums.entity.EntityTex;
import com.mygdx.game.enums.entity.EntityAnimation;

public class PlayableCharacter extends Npc {

    public PlayableCharacter() {
        super();
    }
    @Override
    public void set0Entity(EntityClass entityClass) {
        super.set0Entity(entityClass);
    }
    @Override
    public void set01EntityTex(EntityTex entityTex) {
        super.set01EntityTex(entityTex);
    }
    @Override
    public void set02EntityAnim(EntityAnimation entityAnim) {
        super.set02EntityAnim(entityAnim);
    }
}
