package com.mygdx.game.ants.something.animated.event.a;

import com.badlogic.gdx.graphics.g2d.Batch;

import com.mygdx.game.enums.entity.EntityClass;
import com.mygdx.game.enums.entity.EntityTex;
import com.mygdx.game.world.WorldResAnimManager;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.ants.something.animated.a.MyAnimation;
import com.mygdx.game.enums.entity.EntityTex;
import com.mygdx.game.enums.entity.EntityAnimation;

public class AnimatedEventSomething extends Something {
    public MyAnimation animation; // can be manipulated by children only
    protected EntityAnimation entityAnim;
    public AnimatedEventSomething() {
        super();

    }
    public void set02EntityAnim(EntityAnimation entityAnim) { //todo remove
        this.entityAnim = entityAnim;
    }
    public void set12Anim(WorldResAnimManager animManager){
        animation = new MyAnimation(true);
        animation.setCoreAnimation(animManager.getAnimationByID(this.entityAnim));
        animation.setToFirstFrame(); //todo check , otherwise texReg will be null ?
        texReg = animation.getTEMPPP();
//        coreFrameDur = 0.015f; // 60 fps !
    }
    //-------------------------------------------------------------------------------------------//
    @Override
    public void act(float delta) {
        if (animation!=null)
            texReg = animation.updateFrame(delta);
    }
    //-------------------------------------------------------------------------------------------//
    @Override
    public void destroy(){ // Call from DeadPool
        super.destroy();
        //todo add Anim for garbage collection
    }
}
