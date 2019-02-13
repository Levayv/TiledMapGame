package com.mygdx.game.ants.something.animated.a;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyAnimation { //todo fix ASAP 1223
    private TextureRegion frame;
//    private Animation<TextureRegion> coreAnimation;
//    private float coreFrameDur;
//    private int coreFrames;
//    private Animation<TextureRegion> extraAnimation;
//    private float extraFrameDur;
//    private int extraFrames;
    private MyAnimationSequence coreSequence = new MyAnimationSequence();
    private MyAnimationSequence sequence1    = new MyAnimationSequence();
    private MyAnimationSequence sequence2    = new MyAnimationSequence();
    private MyAnimationSequence sequence3    = new MyAnimationSequence();
    private boolean loopingEndless;
    private boolean startAnimCycle = false;
    private float animationTime = 0f;

    public MyAnimation(boolean loopingEndless){
        this.loopingEndless = loopingEndless;
    }
    public void setCoreAnimation(Animation<TextureRegion> coreAnimation){
        sequence1.coreAnimation = coreAnimation;
        sequence1.coreFrames = coreAnimation.getKeyFrames().length;
        sequence1.coreFrameDur = (0.025f / sequence1.coreFrames)*20; // 3fps ?
        coreSequence = sequence1;
    }
    public void setExtraAnimation(Animation<TextureRegion> extraAnimation){
        sequence2.coreAnimation = extraAnimation;
        sequence2.coreFrames = extraAnimation.getKeyFrames().length;
        sequence2.coreFrameDur = (0.025f / sequence2.coreFrames)*20; // 3fps ?
    }
    public TextureRegion updateFrame(float delta,int state){ // inside Actor's Act method
        if (state == 0)
            coreSequence = sequence1;
        if (state == 1)
            coreSequence = sequence2;
//        if (loopingEndless || startAnimCycle) {
//            if (!loopingEndless) {
//                //checking cycle time
//                if (coreSequence.coreFrameDur * coreSequence.coreFrames < animationTime) {
//                    startAnimCycle = false;
//                    animationTime = 0;
////                System.out.println("stop" + animationTime);
//                }
//            }
//            frame = coreSequence.coreAnimation.getKeyFrame(animationTime, loopingEndless);
//        }
//        animationTime += delta;

        // todo delete , old 1/2
        if (startAnimCycle) {
            if (!loopingEndless){
                if (coreSequence.coreFrameDur * coreSequence.coreFrames < animationTime) { // todo checking if not looping
                    startAnimCycle = false;
                    animationTime = 0;
//                System.out.println("stop" + animationTime);
                } else {
//                System.out.println("start" + animationTime);
                    frame = coreSequence.coreAnimation.getKeyFrame(animationTime, true);
                    animationTime += delta;
                }
            }
        } else {
            if (loopingEndless) {
                frame = coreSequence.coreAnimation.getKeyFrame(animationTime, true);
                if (animationTime >= coreSequence.coreFrameDur * coreSequence.coreFrames)
                    animationTime -= coreSequence.coreFrameDur * coreSequence.coreFrames;
                animationTime += delta;
//                System.out.println(this.getName() + " at="+ animationTime);
//                System.out.println(this.getName() + " FD="+ coreFrameDur*coreFrames*10);
            }
        }

        // todo delete , old 2/2
//        if (state == 1){
//            if (startAnimCycle){
//                if (extraFrameDur * extraFrames < animationTime){ // todo checking if not looping
//                    startAnimCycle = false;
//                    animationTime = 0;
////                System.out.println("stop" + animationTime);
//                }else {
////                System.out.println("start" + animationTime);
//                    frame = extraAnimation.getKeyFrame(animationTime,true);
//                    animationTime += delta;
//                }
//            }else {
//                if (loopingEndless){
//                    frame = extraAnimation.getKeyFrame(animationTime,loopingEndless);
//                    animationTime += delta;
////                System.out.println(this.getName() + " at="+ animationTime);
////                System.out.println(this.getName() + " FD="+ coreFrameDur*coreFrames*10);
//                }
//            }
//        }

//        frame = coreAnimation.getKeyFrame(animationTime,loopingEndless);
        return frame;
    }
    public void startAnim(boolean invertFrameOrder){
        this.startAnimCycle = true;
        if (invertFrameOrder)
            coreSequence.coreAnimation.setPlayMode(Animation.PlayMode.REVERSED);
        else
            coreSequence.coreAnimation.setPlayMode(Animation.PlayMode.NORMAL);
    }
    public void setLoopingEndless(boolean loopingEndless){
        this.loopingEndless = loopingEndless;
    }
//    public boolean drawOrNot(){
//        return loopingEndless || startAnimCycle;
////       return true;
//    }
    public void setToLastFrame(){
        frame = coreSequence.coreAnimation.getKeyFrames()[coreSequence.coreAnimation.getKeyFrames().length-1];
    }
    public void setToFirstFrame() {
        frame = coreSequence.coreAnimation.getKeyFrames()[0];
    }
    public boolean isAlive(){
        return startAnimCycle;
    }
    public TextureRegion getTEMPPP(){
        frame = coreSequence.coreAnimation.getKeyFrames()[0];
        return frame;
    }
}
