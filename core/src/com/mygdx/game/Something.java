package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Something extends shit{
//    Sprite sprite;
    private Rectangle border;

    public Something(TextureRegion texReg) {
        super(texReg);
        border = new Rectangle();
    }

//    int x;
//    int y;
//    int width;
//    int height;

    void setBorders(){
        border.x      = texReg.getRegionX();
        border.y      = texReg.getRegionY();
        border.width  = texReg.getRegionWidth();
        border.height = texReg.getRegionHeight();
        border.x = this.getX() + 0;
        border.y = this.getY() + 0;
    }
    void setBorders(int borderX, int borderY , int borderWidth, int borderHeight  ){
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
    @Override
    protected void positionChanged(){
        if (border != null) {
            border.x = this.getX() + 0;
            border.y = this.getY() + 0;
        }
    }
    float getBorderX(){ return border.x;}
    float getBorderY(){ return border.y;}
    float getBorderW(){ return border.width;}
    float getBorderH(){ return border.height;}
    Rectangle getBorder(){ return border;}
}
