package com.mygdx.game.ants;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.WorldTexRegManager;
import com.mygdx.game.enums.Entity;

public class AnimatedPlayableCharacter extends AnimatedNPC {


    public AnimatedPlayableCharacter(Entity entity, WorldTexRegManager texRegManager, Group world, String file, int rows) {
        super(entity,  texRegManager,  world, file, rows);
    }
}
