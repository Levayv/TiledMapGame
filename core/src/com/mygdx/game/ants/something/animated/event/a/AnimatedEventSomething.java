package com.mygdx.game.ants.something.animated.event.a;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.WorldResAnimManager;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.enums.EntityTex;
import com.mygdx.game.enums.EntityAnimation;

public class AnimatedEventSomething extends Something {
    int stateCount;
//    MyAnimation animation = new MyAnimation();
    // old
    public boolean loopingEndless = true;
    public boolean startAnimCycle = false;
    public float animationTime = 0f;

    private float coreFrameDur;
    private int coreFrames;
    protected Animation<TextureRegion> coreAnimation;

    public int tempINT;
    public String tempString;
    //    TextureRegion[][] animFramesBuffer;
    protected EntityAnimation entityAnim;

    public AnimatedEventSomething(EntityTex entityTex,
                                  EntityAnimation entityAnim,
                                  int stateCount) {
        super(entityTex);
//        super(entityTex, entityAnim);
        this.entityAnim = entityAnim;
        this.stateCount = stateCount;


    }
//    public void tempCHANGEANIM(Animation<TextureRegion> anim){
//        this.coreAnimation = anim;
//    }

    public void setAnim(WorldResAnimManager animManager){
        coreAnimation = animManager.getAnimationByID(this.entityAnim);
        coreFrames = coreAnimation.getKeyFrames().length;
        coreFrameDur = (0.025f / coreFrames)*20; // 3fps ?
//        coreFrameDur = 0.015f; // 60 fps !
    }
    public void setAnimEvent(){

    }
    @Override
    public void act(float delta) {
//        if (loopingEndless || startAnimCycle){
//            setVisible(false);
//        }else
//            setVisible(true);
        if (startAnimCycle){
            if (coreFrameDur * coreFrames *1 < animationTime){
                startAnimCycle = false;
                System.out.println("stop" + animationTime);
            }else {
                System.out.println("start" + animationTime);
                texReg = coreAnimation.getKeyFrame(animationTime,true);
                animationTime += delta;
                System.out.println(this.getName() + " at="+ animationTime);
                System.out.println(this.getName() + " FD="+ coreFrameDur * coreFrames *1);
            }
        }else {
            if (loopingEndless){
                texReg = coreAnimation.getKeyFrame(animationTime,loopingEndless);
                animationTime += delta;
//                System.out.println(this.getName() + " at="+ animationTime);
//                System.out.println(this.getName() + " FD="+ coreFrameDur*coreFrames*10);
            }
        }
    }
    @Override
    public void draw (Batch batch, float parentAlpha) {
        if (loopingEndless || startAnimCycle){
            batch.draw(texReg, getX(), getY());
        }
    }
}
