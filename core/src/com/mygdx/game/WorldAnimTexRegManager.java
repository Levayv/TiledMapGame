package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.enums.Entity;

public class WorldAnimTexRegManager {
    private Animation[] animations;
    private Animation animation;
    private int animationsSize = 0;
    private int arrayCap;
    WorldAnimTexRegManager(int texRegArrayCap){
        this.arrayCap = texRegArrayCap;
        animations = new Animation[arrayCap];
//        animations = new TextureRegion[arrayCap];
    }
    public void addAnimation(Entity entity, Animation animation){
        animations[entity.GetID()] = animation;
        animationsSize++;
    }
    public Animation getTexRegByID(Entity entity){
        animation = animations[entity.GetID()];
        return animation;
    }
}
