package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.sound.midi.Sequence;


class Player extends Something{
    private static int low      = 50;
    private static int medium   = 200;
    private static int high     = 600;
    int moveSpeedL = medium;
    int moveSpeedR = medium;
    int moveSpeedU = medium;
    int moveSpeedD = medium;
    private DirConst dir;
    private boolean upLock          ;
    private boolean downLock        ;
    private boolean leftLock        ;
    private boolean rightLock       ;
    private boolean go;

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
            if (dir == DirConst.UP    && !upLock    ){
                moveBy(0 , moveSpeedU*delta);
            }
            if (dir == DirConst.DOWN  && !downLock  ){
                moveBy(0 , -moveSpeedD*delta);
            }
            if (dir == DirConst.LEFT  && !leftLock  ){
                moveBy(-moveSpeedL*delta , 0);
            }
            if (dir == DirConst.RIGHT && !rightLock ){
                moveBy(moveSpeedR*delta , 0);
            }
            Sequence sequence = new seX
            go = false;
            if (true){
                if (!upLock    )
                    System.out.print("<");
                if (!downLock  )
                    System.out.print(">");
                if (!leftLock  )
                    System.out.print("A");
                if (!rightLock )
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

    public void setDir(DirConst dir) {
        this.dir = dir;
    }


//    Sprite sprite;
//    Rectangle border;
//    int x;
//    int y;
//    int width;
//    int height;



}
