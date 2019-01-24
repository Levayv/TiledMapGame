package com.mygdx.game.ants.something.animated.a;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyAnimation {
    private TextureRegion frame;
    private Animation<TextureRegion> coreAnimation;
    private float coreFrameDur;
    private int coreFrames;
    private Animation<TextureRegion> extraAnimation;
    private float extraFrameDur;
    private int extraFrames;
    private boolean loopingEndless;
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
    public void setExtraAnimation(Animation<TextureRegion> extraAnimation){
        this.extraAnimation = extraAnimation;
        extraFrames = extraAnimation.getKeyFrames().length;
        extraFrameDur = (0.025f / extraFrames)*20; // 3fps ?
    }
    public TextureRegion updateFrame(float delta,int state){ // inside Actor's Act method
        if (state==0){
            if (startAnimCycle){
                if (coreFrameDur * coreFrames < animationTime){ // todo checking if not looping
                    startAnimCycle = false;
                    animationTime = 0;
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
        }
        if (state == 1){
            if (startAnimCycle){
                if (extraFrameDur * extraFrames < animationTime){ // todo checking if not looping
                    startAnimCycle = false;
                    animationTime = 0;
//                System.out.println("stop" + animationTime);
                }else {
//                System.out.println("start" + animationTime);
                    frame = extraAnimation.getKeyFrame(animationTime,true);
                    animationTime += delta;
                }
            }else {
                if (loopingEndless){
                    frame = extraAnimation.getKeyFrame(animationTime,loopingEndless);
                    animationTime += delta;
//                System.out.println(this.getName() + " at="+ animationTime);
//                System.out.println(this.getName() + " FD="+ coreFrameDur*coreFrames*10);
                }
            }
        }

//        frame = coreAnimation.getKeyFrame(animationTime,loopingEndless);
        return frame;
    }
    public void startAnim(boolean invert){
        this.startAnimCycle = true;
        if (invert)
            coreAnimation.setPlayMode(Animation.PlayMode.REVERSED);
        else
            coreAnimation.setPlayMode(Animation.PlayMode.NORMAL);
    }
    public void setLoopingEndless(boolean loopingEndless){
        this.loopingEndless = loopingEndless;
    }
    public boolean drawOrNot(){
        return loopingEndless || startAnimCycle;
//        return true;
    }
    public void setToLastFrame(){
        frame = coreAnimation.getKeyFrames()[coreAnimation.getKeyFrames().length-1];
    }
    public void setToFirstFrame() {
        frame = coreAnimation.getKeyFrames()[0];
    }
    public boolean isAlive(){
        return startAnimCycle;
    }
    public TextureRegion getTEMPPP(){
        frame = coreAnimation.getKeyFrames()[0];
        return frame;
    }
}
