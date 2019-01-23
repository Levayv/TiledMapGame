package com.mygdx.game.ants.something.a;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.Vector1;
import com.mygdx.game.ants.a.coreActor;
import com.mygdx.game.enums.entity.EntityClass;
import com.mygdx.game.enums.entity.EntityTex;

public class Something extends coreActor{
//    Sprite sprite;
    private Rectangle border; // todo Change to dynamic object ?
    private boolean customBorder;
    private boolean customRange;
    private Circle range;
    private Vector2 buffVect2;
    private Vector1 center;
    public int borderXdelta;
    public int borderYdelta;
    private SomethingData data;
    private final Vector2 nullVector = new Vector2(-1000,-1000);
    public Something() {
        super();
        border = new Rectangle();
        range = new Circle();
        buffVect2 = new Vector2();
        center = new Vector1();
    }
    public void set09Data(SomethingData data) {
        this.data = data;
    }
    public void set22Borders(){
        border.x      = texReg.getRegionX();
        border.y      = texReg.getRegionY();
        border.width  = texReg.getRegionWidth();
        border.height = texReg.getRegionHeight();
//        border.x = this.getX() + 0;
//        border.y = this.getY() + 0;
//        range.set(border.getCenter(new Vector2()),new Vector2(border.x*2,border.y*2)  );
        setCenter();
    }
    public void set22Borders(int borderXdelta, int borderYdelta , int borderWdelta, int borderHdelta  ){
        this.borderXdelta = borderXdelta;
        this.borderYdelta = borderYdelta;
        border.x = texReg.getRegionX() + this.borderXdelta;
        border.y = texReg.getRegionY() + this.borderYdelta;
        border.width  = texReg.getRegionWidth()  - borderWdelta*2;
        border.height = texReg.getRegionHeight() - borderHdelta*2;
        customBorder = true;
        setCenter();
    }
    public void set22Borders(int borderXdelta, int borderYdelta){
        this.borderXdelta = borderXdelta;
        this.borderYdelta = borderYdelta;
        border.x = texReg.getRegionX() + this.borderXdelta;
        border.y = texReg.getRegionY() + this.borderYdelta;
        border.width  = texReg.getRegionWidth()  - borderXdelta*2;
        border.height = texReg.getRegionHeight() - borderYdelta*2;
        customBorder = true;
        // change rectangle to square, by smallest side
        if (border.width!=border.height){
            if (border.width > border.height)
                //noinspection SuspiciousNameCombination
                border.width = border.height;
            if (border.height > border.width)
                //noinspection SuspiciousNameCombination
                border.height = border.width;
        }
        setCenter();
    }
    private void setCenter(){ // must be called from public setBorders x3 & posChanged
        center.x = (int) (border.width/2 + border.x);
        center.y = (int) (border.height/2 + border.y);
    }
    public void set23Range(){
        customRange = true;
        range.setPosition(border.getCenter(new Vector2()));
        range.setRadius(border.width*2);
    }
    public void set23Range(int diff){
        customRange = true;
        range.setPosition(border.getCenter(new Vector2()));
        range.setRadius(border.width*2+diff);
    }
    //-------------------------------------------------------------------------------------------//
    public SomethingData getData(){
        return data;
    }
    public Rectangle getBorder(){ return border;}
    public Circle getRange(){ return range;}
    public float getBorderX(){ return border.x;}
    public float getBorderY(){ return border.y;}
    public float getBorderW(){ return border.width;}
    public float getBorderH(){ return border.height;}
    public Vector1 getCenter(){ return center;}
    //-------------------------------------------------------------------------------------------//
    @Override
    protected void positionChanged(){ //todo optimise ! Something pos rarely changes
        // update Range, if both range and border are not null
        if (range != null && border != null) {
            border.x = this.getX() + this.borderXdelta;
            border.y = this.getY() + this.borderYdelta;
            range.setPosition(border.getCenter(buffVect2));
        }
        if (border != null){
            setCenter();
        }
    }
    @Override
    protected void drawDebugBounds (ShapeRenderer shapes) {
        super.drawDebugBounds(shapes);
        // draw Range, if both range and border are not null
        if (customRange){
            shapes.setColor(Color.YELLOW);
            shapes.circle(range.x,range.y,range.radius);
        }
        if (customBorder){
            shapes.setColor(Color.RED);
            shapes.rect(border.x, border.y,border.width, border.height);
        }
//        if (!debug) return;
//        shapes.set(ShapeRenderer.ShapeType.Line);
//        shapes.setColor(stage.getDebugColor());
//        shapes.rect(x, y, originX, originY, width, height, scaleX, scaleY, rotation);
    }
    //-------------------------------------------------------------------------------------------//
    public void destroy(){ // Call from DeadPool
        this.remove();
        this.setVisible(false);
        this.setPosition(nullVector.x, nullVector.y);
        this.data = null;
        this.texReg = null;
//        border = null;
//        range = null;
        entityTex = null; //todo research
        setMyIndex(-1);
        customRange = false;
    }
}
