package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedPlayableCharacter extends AnimatedNPC {
    public AnimatedPlayableCharacter(TextureRegion texReg) {
        super(texReg);
    }
    TextureRegion[] walkFramesU;
    TextureRegion[] walkFramesD;
    TextureRegion[] walkFramesL;
    TextureRegion[] walkFramesR;
    Animation<TextureRegion> walkAnimation1;
    Animation<TextureRegion> walkAnimation2;
    Animation<TextureRegion> walkAnimation3;
    Animation<TextureRegion> walkAnimation4;

    private float frameDur = 0.025f;
    float animationTime1 = 0f;
    float animationTime2 = 0f;
    float animationTime3 = 0f;
    float animationTime4 = 0f;

    public void setAnimations(){
        int FRAME_COLS = 8, FRAME_ROWS = 4;
        Texture walkSheet = new Texture(Gdx.files.internal("animation/male_sprite_model.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);
        TextureRegion[] buffer = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int upDown      = 5;
        int leftRight   = 8;
        walkFramesU = new TextureRegion[upDown];
        walkFramesD = new TextureRegion[upDown];
        walkFramesL = new TextureRegion[leftRight];
        walkFramesR = new TextureRegion[leftRight];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                buffer[index++] = tmp[i][j];
            }
        }
        index = 0;
        for (int i = 0; i < 5; i++) { walkFramesU[i] = buffer[index++];}
        index +=3;
        for (int i = 0; i < 5; i++) { walkFramesD[i] = buffer[index++];}
        index +=3;
        for (int i = 0; i < 8; i++) { walkFramesL[i] = buffer[index++];}
        for (int i = 0; i < 8; i++) { walkFramesR[i] = buffer[index++];}
        walkAnimation1 = new Animation<TextureRegion>(frameDur*10, walkFramesU);
        walkAnimation2 = new Animation<TextureRegion>(frameDur*10, walkFramesD);
        walkAnimation3 = new Animation<TextureRegion>(frameDur*10, walkFramesL);
        walkAnimation4 = new Animation<TextureRegion>(frameDur*10, walkFramesR);
    }
    @Override
    public void draw (Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        TextureRegion currentFrame1 = walkAnimation1.getKeyFrame(animationTime1, true);
        TextureRegion currentFrame2 = walkAnimation2.getKeyFrame(animationTime2, true);
        TextureRegion currentFrame3 = walkAnimation3.getKeyFrame(animationTime3, true);
        TextureRegion currentFrame4 = walkAnimation4.getKeyFrame(animationTime4, true);
        animationTime1 += Gdx.graphics.getDeltaTime();
        animationTime2 += Gdx.graphics.getDeltaTime();
        animationTime3 += Gdx.graphics.getDeltaTime();
        animationTime4 += Gdx.graphics.getDeltaTime();
        batch.draw(currentFrame1, 0, 0); // Draw current frame at (50, 50)
        batch.draw(currentFrame2, 0, 75); // Draw current frame at (50, 50)
        batch.draw(currentFrame3, 75, 0); // Draw current frame at (50, 50)
        batch.draw(currentFrame4, 75, 75); // Draw current frame at (50, 50)
//        batch.draw(texReg, getX(), getY(), getOriginX(), getOriginY(),
//                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

}
