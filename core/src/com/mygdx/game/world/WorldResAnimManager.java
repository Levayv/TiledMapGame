package com.mygdx.game.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.enums.entity.EntityAnimation;

public class WorldResAnimManager {
    private Animation[] animations;
    private Animation animation;
    private int animationsSize = 0;
    private int arrayCap;
    public WorldResAnimManager(int texRegArrayCap){
        this.arrayCap = texRegArrayCap;
        animations = new Animation[arrayCap];
//        animations = new TextureRegion[arrayCap];
    }
    public void addAnimation(EntityAnimation entity, Animation animation){
        animations[entity.GetID()] = animation;
        animationsSize++;
    }
    public Animation getAnimationByID(EntityAnimation entity){
//        System.out.println(entity);
        animation = animations[entity.GetID()];
        return animation;
    }
    public void addAnimationFromFile(EntityAnimation entity, Texture walkSheet, int FRAME_COLS){
        int FRAME_ROWS = 1;
        TextureRegion[] animFrames;
        float frameDur = 0;
        //        this.rows = rows;
        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);
        TextureRegion[] buffer = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        animFrames = new TextureRegion[FRAME_COLS*FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                buffer[index++] = tmp[i][j];
            }
        }
        index = 0;
        for (int i = 0; i < FRAME_COLS; i++) { animFrames[i] = buffer[index++];}
        frameDur = (0.025f / FRAME_COLS)*20; // 3fps ?
//        frameDur = 0.015f; // 60 fps !
        Animation eventAnimation;
        eventAnimation = new Animation<TextureRegion>(frameDur, animFrames);
        addAnimation(entity , eventAnimation);
    }
}
