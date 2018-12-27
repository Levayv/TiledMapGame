package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.enums.DirConst;


class Player extends AnimatedPlayableCharacter{
    private static int low      = 50;
    private static int medium   = 200;
    private static int high     = 600;
    int moveSpeedL = medium;
    int moveSpeedR = medium;
    int moveSpeedU = medium;
    int moveSpeedD = medium;
    private DirConst dirX;
    private DirConst dirY;
    private DirConst dirLast;
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

    public Player(TextureRegion texReg, String file, int rows) {
        super(texReg, file, rows);
    }
    public Player(TextureRegion texReg) {
        super(texReg , "Explosion", 111);
        dirLast = DirConst.NULL;
        dirX = DirConst.NULL;
        dirY = DirConst.NULL;
    }

    public void setupLock (boolean upLock){
        this.upLock = upLock;
    }
    public void setdownLock (boolean downLock){
        this.downLock = downLock;
    }
    public void setleftLock (boolean leftLock){
        this.leftLock  = leftLock;
    }
    public void setrightLock (boolean rightLock){
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
            if (dirX == DirConst.LEFT  && !leftLock  ){
                updateX = -moveSpeedL*delta;
            }
            if (dirX == DirConst.RIGHT && !rightLock ){
                updateX = moveSpeedR*delta;
            }
            if (dirY == DirConst.UP    && !upLock    ){
                updateY = moveSpeedU*delta;
            }
            if (dirY == DirConst.DOWN  && !downLock  ){
                updateY = -moveSpeedD*delta;
            }
            moveBy(updateX, updateY);
            if (dirY != DirConst.NULL)
                dirLast = dirY;
            else
                dirLast = dirX;
            getDirFromDir();
            getAnimationsWorking(delta);
            dirX = DirConst.NULL;
            dirY = DirConst.NULL;
            go = false;
            if (getDebug()){
                System.out.print("Moves available");
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
        if (dirY == DirConst.UP){
            texReg = walkAnimationU.getKeyFrame(animationTime,true);
        }
        if (dirY == DirConst.DOWN){
            texReg = walkAnimationD.getKeyFrame(animationTime,true);
        }
        if (dirX == DirConst.LEFT){
            texReg = walkAnimationL.getKeyFrame(animationTime,true);
        }
        if (dirX == DirConst.RIGHT){
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

//        texReg = new TextureRegion(new Texture(Gdx.files.internal("bucket_64.png")));
        if (getDebug())
            System.out.println(dirNumber);
    }

    void speedLow(){
        moveSpeedL = low;
        moveSpeedR = low;
        moveSpeedU = low;
        moveSpeedD = low;
    }
    void speedMed(){
        moveSpeedL = medium;
        moveSpeedR = medium;
        moveSpeedU = medium;
        moveSpeedD = medium;
    }
    void speedHigh(){
        moveSpeedL = high;
        moveSpeedR = high;
        moveSpeedU = high;
        moveSpeedD = high;
    }

    public void setDirX(DirConst dirX) {
        this.dirX = dirX;
    }
    public void setDirY(DirConst dirY) {
        this.dirY = dirY;
    }
}
