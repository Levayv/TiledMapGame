package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Something extends shit{
//    Sprite sprite;
    Rectangle border;

    public Something(TextureRegion texReg) {
        super(texReg);
    }

//    int x;
//    int y;
//    int width;
//    int height;

    void setBorders(){
        border = new Rectangle();
        border.x      = texReg.getRegionX();
        border.y      = texReg.getRegionY();
        border.width  = texReg.getRegionWidth();
        border.height = texReg.getRegionHeight();
    }
    void setBorders(int borderX, int borderY , int borderWidth, int borderHeight  ){
        border = new Rectangle();
        border.x = borderX;
        border.y = borderY;
        border.width = borderWidth;
        border.height = borderHeight;

//        texReg.setRegionX(x);
//        texReg.setRegionY(y);
//        texReg.setRegionWidth(width);
//        texReg.setRegionHeight(height);

//        System.out.println(border.getX());
//        System.out.println(border.getY());
//        System.out.println(border.getWidth());
//        System.out.println(border.getHeight());

//        this.x = x;
//        this.y = y;
//        this.width = width;
//        this.height = height;
    }


}
