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
    }   //todo fix this shit , you got no direction or last frame for animations
    TextureRegion[] waitFrame;

    TextureRegion[] walkFramesU;
    TextureRegion[] walkFramesD;
    TextureRegion[] walkFramesL;
    TextureRegion[] walkFramesR;

    Animation<TextureRegion> walkAnimationU;
    Animation<TextureRegion> walkAnimationD;
    Animation<TextureRegion> walkAnimationL;
    Animation<TextureRegion> walkAnimationR;

    private float frameDur = 0.025f;
    float animationTime1 = 0f;
    float animationTime2 = 0f;
    float animationTime3 = 0f;
    float animationTime4 = 0f;

    public void setAnimations(){
        waitFrame = new TextureRegion[4];
//----------------------------------------------------------------------------------
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
        for (int i = 0; i < 8; i++) { walkFramesR[i] = buffer[index++];}
        for (int i = 0; i < 8; i++) { walkFramesL[i] = buffer[index++];}
        walkAnimationU = new Animation<TextureRegion>(frameDur*10, walkFramesU);
        walkAnimationD = new Animation<TextureRegion>(frameDur*10, walkFramesD);
        walkAnimationL = new Animation<TextureRegion>(frameDur*10, walkFramesL);
        walkAnimationR = new Animation<TextureRegion>(frameDur*10, walkFramesR);
        waitFrame[0] = walkFramesU[0];
        waitFrame[2] = walkFramesD[0];
        waitFrame[3] = walkFramesL[0];
        waitFrame[1] = walkFramesR[0];
    }
    @Override
    public void draw (Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        TextureRegion currentFrameU = walkAnimationU.getKeyFrame(animationTime1, true);
        TextureRegion currentFrameD = walkAnimationD.getKeyFrame(animationTime2, true);
        TextureRegion currentFrameL = walkAnimationL.getKeyFrame(animationTime3, true);
        TextureRegion currentFrameR = walkAnimationR.getKeyFrame(animationTime4, true);
        animationTime1 += Gdx.graphics.getDeltaTime();
        animationTime2 += Gdx.graphics.getDeltaTime();
        animationTime3 += Gdx.graphics.getDeltaTime();
        animationTime4 += Gdx.graphics.getDeltaTime();
        batch.draw(currentFrameU, 0, 0); // Draw current frame at (50, 50)
        batch.draw(currentFrameD, 0, 75); // Draw current frame at (50, 50)
        batch.draw(currentFrameL, 75, 0); // Draw current frame at (50, 50)
        batch.draw(currentFrameR, 75, 75); // Draw current frame at (50, 50)


        batch.draw(texReg, getX()+16, getY()+2);
//        batch.draw(texReg, getX(), getY(), getOriginX(), getOriginY(),
//                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

}
