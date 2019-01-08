package com.mygdx.game.ants.something.animated.event.a;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.ants.something.animated.a.AnimatedSomething;
import com.mygdx.game.enums.EntityTex;
import com.mygdx.game.enums.EntityAnimation;

public class AnimatedEventSomething extends AnimatedSomething {
    int stateCount;
    TextureRegion[][] animFramesBuffer;
    public AnimatedEventSomething(EntityTex entityTex,
                                  EntityAnimation entityAnim,
                                  int stateCount) {
        super(entityTex, entityAnim);
        this.stateCount = stateCount;


    }
//    public void tempCHANGEANIM(Animation<TextureRegion> anim){
//        this.eventAnimation = anim;
//    }
    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
