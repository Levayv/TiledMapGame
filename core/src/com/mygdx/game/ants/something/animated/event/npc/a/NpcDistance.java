package com.mygdx.game.ants.something.animated.event.npc.a;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Vector1;

public class NpcDistance {
    public Vector1 center;
    public Vector1 targetCenter;
    private float range;
    public NpcDistance(){

    }
    public void setMyCenter(Vector1 center){
        this.center = center;
    }
    public void setMyRange(float range){ //todo change to object not primitive
        this.range = range;
    }
    public boolean inRange(Vector1 targetCenter){
        this.targetCenter = targetCenter;
        float triangleX = this.center.x - targetCenter.x;
        float triangleY = this.center.y - targetCenter.y;
        int distance = (int) Math.sqrt((triangleX*triangleX)+(triangleY*triangleY));
        System.out.println("!!! math trx = "+triangleX);
        System.out.println("!!! math try = "+triangleY);
        System.out.println("!!! math x^2 = "+triangleX*triangleX);
        System.out.println("!!! math y^2 = "+triangleY*triangleY);
        System.out.println("!!! math dis = "+distance);
        System.out.println("!!! math rng = "+range);
        if (distance<range)
            return true;
        else
            return false;
    }

}
