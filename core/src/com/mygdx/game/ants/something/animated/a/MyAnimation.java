package com.mygdx.game.ants.something.animated.a;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyAnimation {
    private Animation<TextureRegion> coreAnimation;
    private TextureRegion frame;
    private float coreFrameDur;
    private int coreFrames;
    private boolean loopingEndless = true;
    private boolean startAnimCycle = false;
    private float animationTime = 0f;

    public MyAnimation(boolean loopingEndless){
        this.loopingEndless = loopingEndless;
    }
    public void setCoreAnimation(Animation<TextureRegion> coreAnimation){
        this.coreAnimation = coreAnimation;
        coreFrames = coreAnimation.getKeyFrames().length;
        coreFrameDur = (0.025f / coreFrames)*20; // 3fps ?
    }
    public TextureRegion updateFrame(float delta){ // inside Actor's Act method
        if (startAnimCycle){
            if (coreFrameDur * coreFrames < animationTime){ // todo checking if not looping
                startAnimCycle = false;
//                System.out.println("stop" + animationTime);
            }else {
//                System.out.println("start" + animationTime);
                frame = coreAnimation.getKeyFrame(animationTime,true);
                animationTime += delta;
            }
        }else {
            if (loopingEndless){
                frame = coreAnimation.getKeyFrame(animationTime,loopingEndless);
                animationTime += delta;
//                System.out.println(this.getName() + " at="+ animationTime);
//                System.out.println(this.getName() + " FD="+ coreFrameDur*coreFrames*10);
            }
        }
        frame = coreAnimation.getKeyFrame(animationTime,loopingEndless);
        return frame;
    }
    public void startAnim(){
        this.startAnimCycle = true;
    }
    public boolean drawOrNot(){
        return loopingEndless || startAnimCycle;
    }

}
