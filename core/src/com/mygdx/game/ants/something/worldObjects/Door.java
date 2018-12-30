package com.mygdx.game.ants.something.worldObjects;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.WorldTexRegManager;
import com.mygdx.game.ants.something.animated.AnimatedSomething;
import com.mygdx.game.enums.Entity;

public class Door extends AnimatedSomething implements doorShit {
    public Door(Entity entity, WorldTexRegManager texRegManager, Group world, String file, int rows) {
        super(entity, texRegManager, world, file, rows);
    }
}
