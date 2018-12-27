package com.mygdx.game.ants;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.enums.Entity;

public class Something extends shit{
//    Sprite sprite;
    private Rectangle border; // todo Change to dynamic object
    public Entity entity;

    public Something(TextureRegion texReg) {
        super(texReg);
        border = new Rectangle();
    }

//    int x;
//    int y;
//    int width;
//    int height;

    public void setBorders(){
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
    public float getBorderX(){ return border.x;}
    public float getBorderY(){ return border.y;}
    public float getBorderW(){ return border.width;}
    public float getBorderH(){ return border.height;}
    public Rectangle getBorder(){ return border;}

    //        Actor a = super.hit(x,y,touchable);
//    @Override
    //    public Actor hit(float x, float y, boolean touchable){
//        System.out.println("!");
////        Something s = (Something) a;
//        return a;
//    }
//    @Override
//    public Actor hit(float x, float y, boolean touchable){
//        System.out.println("!");
//        return null;
//    }

}
