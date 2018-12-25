package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;


class Player extends Something{
    private static int low      = 50;
    private static int medium   = 200;
    private static int high     = 600;
    int moveSpeedL = medium;
    int moveSpeedR = medium;
    int moveSpeedU = medium;
    int moveSpeedD = medium;
    private DirConst dirX;
    private DirConst dirY;
    private boolean upLock          ;
    private boolean downLock        ;
    private boolean leftLock        ;
    private boolean rightLock       ;
    private boolean go;
    float updateX;
    float updateY;

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

    public Player(TextureRegion texReg) {
        super(texReg);
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

            dirX = DirConst.NULL;
            dirY = DirConst.NULL;
            go = false;
            if (getDebug()){
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



//    Sprite sprite;
//    Rectangle border;
//    int x;
//    int y;
//    int width;
//    int height;



}
