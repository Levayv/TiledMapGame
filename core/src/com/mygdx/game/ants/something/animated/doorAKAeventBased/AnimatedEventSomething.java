package com.mygdx.game.ants.something.animated.doorAKAeventBased;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.WorldResTexRegManager;
import com.mygdx.game.ants.something.animated.AnimatedSomething;
import com.mygdx.game.enums.Entity;

public class AnimatedEventSomething extends AnimatedSomething {
    int stateCount;
    TextureRegion[][] animFramesBuffer;
    public AnimatedEventSomething(Entity entity, WorldResTexRegManager texRegManager, Group world,
                                  String file, int rows, int stateCount) {
        super(entity, texRegManager, world, file, rows);
        this.stateCount = stateCount;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
