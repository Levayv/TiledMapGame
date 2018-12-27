package com.mygdx.game.ants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedSomething extends Something{
    TextureRegion[] animFrames;
    Animation<TextureRegion> eventAnimation;
    private float animationTime = 0f;
    public boolean start;

    public AnimatedSomething(TextureRegion texReg, String file, int rows) {
        super(texReg);
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
        float frameDur = (0.025f / rows)*20;
        eventAnimation = new Animation<TextureRegion>(frameDur*1, animFrames);

    }
    @Override
    public void act(float delta){
        texReg = eventAnimation.getKeyFrame(animationTime,true);
        animationTime += delta;
    }
    @Override
    public void draw (Batch batch, float parentAlpha) {
        batch.draw(texReg, getX(), getY());
    }
}
