package com.mygdx.game.ants.something.animated.event.npc.a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Vector1;

public class NpcDistance {
    public Vector1 center;
    public Vector1 targetCenter;
    public boolean inRangeB;
    private float range;
    private float distance;
    public NpcDistance(){

    }
    public void setMyCenter(Vector1 center){
        this.center = center;
    }
    public void setMyRange(float range){ //todo change to object not primitive
        this.range = range;
    }
    public boolean inRange(Vector1 targetCenter, Vector1 border){
        this.targetCenter = targetCenter;
        if (center.x == targetCenter.x || center.y == targetCenter.y){
            if (targetCenter.x == center.x){
                distance = Math.abs(center.y - targetCenter.y);
            }else {
                distance = Math.abs(center.x - targetCenter.x);
            }
        }else {
            if (targetCenter.x>center.x-range && targetCenter.x<center.x+range &&
                    targetCenter.y>center.y-range && targetCenter.y<center.y+range){
                float triangleX = this.center.x - targetCenter.x;
                float triangleY = this.center.y - targetCenter.y;
                distance = (int) Math.sqrt((triangleX*triangleX)+(triangleY*triangleY));
            }else {
                distance = range+1;
            }
        }
        if (distance<=range) {
            inRangeB = true;
        } else {
            inRangeB = false;
        }

        return inRangeB;

//        if (triangleX<(range) && triangleY<(range)){
//            distance = (int) Math.sqrt((triangleX*triangleX)+(triangleY*triangleY));
////            Gdx.app.debug("NpcDistance", "trx = "+triangleX);
////            Gdx.app.debug("NpcDistance", "try = "+triangleY);
////            Gdx.app.debug("NpcDistance", "x^2 = "+triangleX*triangleX);
////            Gdx.app.debug("NpcDistance", "y^2 = "+triangleY*triangleY);
////            Gdx.app.debug("NpcDistance", "dis = "+distance);
////            Gdx.app.debug("NpcDistance", "rng = "+range);
//        }else {
//            distance = (int)range;
//        }
//        if (distance<range)
//            return true;
//        else
//            return false;
    }

}
