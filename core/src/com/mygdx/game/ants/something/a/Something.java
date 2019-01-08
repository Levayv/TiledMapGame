package com.mygdx.game.ants.something.a;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ants.a.coreActor;
import com.mygdx.game.enums.EntityTex;

public class Something extends coreActor {
//    Sprite sprite;
    private Rectangle border; // todo Change to dynamic object ?
    private Circle range;
    private Vector2 buffVect2;
    private int borderXdelta;
    private int borderYdelta;

//    public Something(TextureRegion texReg) { //todo change constructor , only entityTex enum must be needed
//        super(texReg);
//        border = new Rectangle();
//        range = new Circle();
//        buffVect2 = new Vector2();
//    }
    public Something(EntityTex entityTex) { //todo change constructor , only entityTex enum must be needed
        super(entityTex);
        border = new Rectangle();
        range = new Circle();
        buffVect2 = new Vector2();

    }
    public void setBorders(){
        border.x      = texReg.getRegionX();
        border.y      = texReg.getRegionY();
        border.width  = texReg.getRegionWidth();
        border.height = texReg.getRegionHeight();
//        border.x = this.getX() + 0;
//        border.y = this.getY() + 0;
//        range.set(border.getCenter(new Vector2()),new Vector2(border.x*2,border.y*2)  );
        setRange();
    }
    public void setBorders(int borderXdelta, int borderYdelta , int borderWdelta, int borderHdelta  ){
        this.borderXdelta = borderXdelta;
        this.borderYdelta = borderYdelta;
        border.x = texReg.getRegionX() + this.borderXdelta;
        border.y = texReg.getRegionY() + this.borderYdelta;
        border.width  = texReg.getRegionWidth()  - borderWdelta*2;
        border.height = texReg.getRegionHeight() - borderHdelta*2;
        setRange();
    }
    public void setRange(){
        range.setPosition(border.getCenter(new Vector2()));
        range.setRadius(border.x*2);
    }

    @Override
    protected void positionChanged(){
        if (range != null && border != null) {
            border.x = this.getX() + this.borderXdelta;
            border.y = this.getY() + this.borderYdelta;
            range.setPosition(border.getCenter(buffVect2));
        }
    }
    public float getBorderX(){ return border.x;}
    public float getBorderY(){ return border.y;}
    public float getBorderW(){ return border.width;}
    public float getBorderH(){ return border.height;}
    public Rectangle getBorder(){ return border;}
    public Circle getRange(){ return range;}
}
