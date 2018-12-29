package com.mygdx.game.ants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.WorldTexRegManager;
import com.mygdx.game.enums.Entity;

public class AnimatedSomething extends Something{
    TextureRegion[] animFrames;
    Animation<TextureRegion> eventAnimation;
    public float animationTime = 0f;
    private float frameDur;
    private int rows;
    public boolean start;
    public boolean loopingEndless = true;
    public boolean startAnimCycle = false;

    public AnimatedSomething(Entity entity, WorldTexRegManager texRegManager, Group world, String file, int rows) {
        super(entity,  texRegManager,  world);
        this.rows = rows;
        int FRAME_COLS = rows;
        int FRAME_ROWS = 1;
        Texture walkSheet = new Texture(Gdx.files.internal("animation/"+file+".png"));
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
        frameDur = (0.025f / rows)*20; // 3fps ?
//        frameDur = 0.015f; // 60 fps !
        eventAnimation = new Animation<TextureRegion>(frameDur, animFrames);

    }
    @Override
    public void act(float delta){
//        if (loopingEndless || startAnimCycle){
//            setVisible(false);
//        }else
//            setVisible(true);
        if (startAnimCycle){
            if (frameDur*rows*1 < animationTime){
                startAnimCycle = false;
                System.out.println("stop" + animationTime);
            }else {
                System.out.println("start" + animationTime);
                texReg = eventAnimation.getKeyFrame(animationTime,true);
                animationTime += delta;
                System.out.println(this.getName() + " at="+ animationTime);
                System.out.println(this.getName() + " FD="+ frameDur*rows*1);
            }
        }else {
            if (loopingEndless){
                texReg = eventAnimation.getKeyFrame(animationTime,loopingEndless);
                animationTime += delta;
//                System.out.println(this.getName() + " at="+ animationTime);
//                System.out.println(this.getName() + " FD="+ frameDur*rows*10);
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
