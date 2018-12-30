package com.mygdx.game.ants.something.animated.pc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.WorldTexRegManager;
import com.mygdx.game.enums.DirConst4;
import com.mygdx.game.enums.Entity;


public class Player extends PlayableCharacter { // todo future refactor to main player or ?
    private static int low      = 50;
    private static int medium   = 200;
    private static int high     = 600;
    public int moveSpeedL = medium;
    public int moveSpeedR = medium;
    public int moveSpeedU = medium;
    public int moveSpeedD = medium;
    private DirConst4 dirX;
    private DirConst4 dirY;
    private DirConst4 dirLast;
    private byte dirNumber;

    private boolean upLock          ;
    private boolean downLock        ;
    private boolean leftLock        ;
    private boolean rightLock       ;
    private boolean go;
    private float updateX;
    private float updateY;

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
    float animationTime = 0f;

    public Player(Entity entity, WorldTexRegManager texRegManager, Group world, String file, int rows) {
        super(entity,  texRegManager,  world, file, rows);
    }
    public Player(Entity entity, WorldTexRegManager texRegManager, Group world) {
        super(entity,  texRegManager,  world, "Explosion", 111);
        dirLast = DirConst4.NULL;
        dirX = DirConst4.NULL;
        dirY = DirConst4.NULL;
    }

    public void setUpLock(boolean upLock){
        this.upLock = upLock;
    }
    public void setDownLock(boolean downLock){
        this.downLock = downLock;
    }
    public void setLeftLock(boolean leftLock){
        this.leftLock  = leftLock;
    }
    public void setRightLock(boolean rightLock){
        this.rightLock = rightLock;
    }
    public void go(){
        go = true;
    }



    @Override
    public void act(float delta){
        if (go){
            updateX = 0;
            updateY = 0;
            if (dirX == DirConst4.LEFT  && !leftLock  ){
                updateX = -moveSpeedL*delta;
            }
            if (dirX == DirConst4.RIGHT && !rightLock ){
                updateX = moveSpeedR*delta;
            }
            if (dirY == DirConst4.UP    && !upLock    ){
                updateY = moveSpeedU*delta;
            }
            if (dirY == DirConst4.DOWN  && !downLock  ){
                updateY = -moveSpeedD*delta;
            }
            moveBy(updateX, updateY);
            if (dirY != DirConst4.NULL)
                dirLast = dirY;
            else
                dirLast = dirX;
            getDirFromDir();
            getAnimationsWorking(delta);
            dirX = DirConst4.NULL;
            dirY = DirConst4.NULL;
            go = false;
            if (getDebug()){
                System.out.print("Moves ");
                if (!leftLock    )
                    System.out.print("<");
                if (!rightLock  )
                    System.out.print(">");
                if (!upLock  )
                    System.out.print("A");
                if (!downLock )
                    System.out.print("V");
                System.out.println();
            }
        }
        else
            texReg = waitFrame[dirNumber];
    }

    private void getAnimationsWorking(float delta) {
        if (dirY == DirConst4.UP){
            texReg = walkAnimationU.getKeyFrame(animationTime,true);
        }
        if (dirY == DirConst4.DOWN){
            texReg = walkAnimationD.getKeyFrame(animationTime,true);
        }
        if (dirX == DirConst4.LEFT){
            texReg = walkAnimationL.getKeyFrame(animationTime,true);
        }
        if (dirX == DirConst4.RIGHT){
            texReg = walkAnimationR.getKeyFrame(animationTime,true);
        }
        animationTime += delta;
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
//        Color color = getColor();
//        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
//        TextureRegion currentFrameU = walkAnimationU.getKeyFrame(animationTime1, true);
//        TextureRegion currentFrameD = walkAnimationD.getKeyFrame(animationTime2, true);
//        TextureRegion currentFrameL = walkAnimationL.getKeyFrame(animationTime3, true);
//        TextureRegion currentFrameR = walkAnimationR.getKeyFrame(animationTime4, true);
//        animationTime1 += Gdx.graphics.getDeltaTime();
//        animationTime2 += Gdx.graphics.getDeltaTime();
//        animationTime3 += Gdx.graphics.getDeltaTime();
//        animationTime4 += Gdx.graphics.getDeltaTime();
//        batch.draw(currentFrameU, 0, 0); // Draw current frame at (50, 50)
//        batch.draw(currentFrameD, 0, 75); // Draw current frame at (50, 50)
//        batch.draw(currentFrameL, 75, 0); // Draw current frame at (50, 50)
//        batch.draw(currentFrameR, 75, 75); // Draw current frame at (50, 50)


        batch.draw(texReg, getX()+16, getY()+2);
//        batch.draw(texReg, getX(), getY(), getOriginX(), getOriginY(),
//                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
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
        walkAnimationU = new Animation<TextureRegion>(frameDur*1, walkFramesU);
        walkAnimationD = new Animation<TextureRegion>(frameDur*1, walkFramesD);
        walkAnimationL = new Animation<TextureRegion>(frameDur*1, walkFramesL);
        walkAnimationR = new Animation<TextureRegion>(frameDur*1, walkFramesR);

        waitFrame[0] = walkFramesU[2];
        waitFrame[2] = walkFramesD[2];
        waitFrame[3] = walkFramesL[0];
        waitFrame[1] = walkFramesR[0];
    }

    private void getDirFromDir() { // Fix dir enum for 8 directional and integrate with animations

        switch (dirLast){
            case UP:    dirNumber = 0; break;
            case DOWN:  dirNumber = 2; break;
            case LEFT:  dirNumber = 3; break;
            case RIGHT: dirNumber = 1; break;
        }
    }

    public void speedLow(){
        moveSpeedL = low;
        moveSpeedR = low;
        moveSpeedU = low;
        moveSpeedD = low;
    }
    public void speedMed(){
        moveSpeedL = medium;
        moveSpeedR = medium;
        moveSpeedU = medium;
        moveSpeedD = medium;
    }
    public void speedHigh(){
        moveSpeedL = high;
        moveSpeedR = high;
        moveSpeedU = high;
        moveSpeedD = high;
    }

    public void setDirX(DirConst4 dirX) {
        this.dirX = dirX;
    }
    public void setDirY(DirConst4 dirY) {
        this.dirY = dirY;
    }
}
