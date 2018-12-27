package com.mygdx.game.ants;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.enums.Entity;

public class Something extends shit{
//    Sprite sprite;
    private Rectangle border; // todo Change to dynamic object
    private Circle range;
    public Entity entity;
    private Vector2 buffVect2;
    private int borderXdelta;
    private int borderYdelta;

    public Something(TextureRegion texReg) {
        super(texReg);
        border = new Rectangle();
        range = new Circle();
        buffVect2 = new Vector2();
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
//        range.set(border.getCenter(new Vector2()),new Vector2(border.x*2,border.y*2)  );
        range.setPosition(border.getCenter(new Vector2()));
        range.setRadius(border.x*2);

    }
    void setBorders(int borderX, int borderY , int borderWidth, int borderHeight  ){
        border.x = borderX;
        border.y = borderY;
        border.width = borderWidth;
        border.height = borderHeight;
    }
    @Override
    protected void positionChanged(){
        if (range != null && border != null) {
            border.x = this.getX() + 0;
            border.y = this.getY() + 0;
//            range.setPosition(this.getX(),this.getY());
            range.setPosition(border.getCenter(buffVect2));
        }
    }
    public float getBorderX(){ return border.x;}
    public float getBorderY(){ return border.y;}
    public float getBorderW(){ return border.width;}
    public float getBorderH(){ return border.height;}
    public Rectangle getBorder(){ return border;}
    public Circle getRange(){ return range;}

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
