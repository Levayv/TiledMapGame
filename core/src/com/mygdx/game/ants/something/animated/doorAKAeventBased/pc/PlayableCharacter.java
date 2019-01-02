package com.mygdx.game.ants.something.animated.doorAKAeventBased.pc;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.WorldTexRegManager;
import com.mygdx.game.ants.something.animated.doorAKAeventBased.npc.NonPlayableCharacter;
import com.mygdx.game.enums.Entity;

public class PlayableCharacter extends NonPlayableCharacter {


    public PlayableCharacter(Entity entity, WorldTexRegManager texRegManager, Group world, String file, int rows) {
        super(entity,  texRegManager,  world, file, rows);
    }
}
