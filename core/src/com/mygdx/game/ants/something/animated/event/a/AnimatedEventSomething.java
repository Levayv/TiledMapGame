package com.mygdx.game.ants.something.animated.event.a;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.WorldResAnimManager;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.ants.something.animated.a.MyAnimation;
import com.mygdx.game.enums.Entity;
import com.mygdx.game.enums.EntityAnimation;

public class AnimatedEventSomething extends Something {
    int stateCount;
    protected MyAnimation animation; // can be manipulated by children only
    // old
//    public boolean loopingEndless = true;
//    public boolean startAnimCycle = false;
//    public float animationTime = 0f;

//    private float coreFrameDur;
//    private int coreFrames;
//    protected Animation<TextureRegion> coreAnimation;

    public int tempINT;
    public String tempString;
    //    TextureRegion[][] animFramesBuffer;
    protected EntityAnimation entityAnim;

    public AnimatedEventSomething(Entity entity,
                                  EntityAnimation entityAnim,
                                  int stateCount) {
        super(entity);

        this.entityAnim = entityAnim;
        this.stateCount = stateCount;
        animation = new MyAnimation(true);


    }
//    public void tempCHANGEANIM(Animation<TextureRegion> anim){
//        this.coreAnimation = anim;
//    }

    public void setAnim(WorldResAnimManager animManager){
        animation.setCoreAnimation(animManager.getAnimationByID(this.entityAnim));

//        coreFrameDur = 0.015f; // 60 fps !

    }

    @Override
    public void act(float delta) {
//        if (loopingEndless || startAnimCycle){
//            setVisible(false);
//        }else
//            setVisible(true);
        texReg = animation.updateFrame(delta);
    }
    @Override
    public void draw (Batch batch, float parentAlpha) {
            batch.draw(texReg, getX(), getY());
    }
}
