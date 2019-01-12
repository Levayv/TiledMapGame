package com.mygdx.game.ants.a;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.world.WorldResTexRegManager;
import com.mygdx.game.enums.entity.Entity;

public class coreActor extends Actor {
    public TextureRegion texReg;
    private Color color;
    private Data data = new Data();
    protected Entity entity;
    protected boolean collision;


//    public coreActor (TextureRegion texReg) {
////        texReg = new TextureRegion(new Texture(Gdx.files.internal("tree.png")));
//        this.texReg = texReg;
//        setBounds(texReg.getRegionX(), texReg.getRegionY(),
//                texReg.getRegionWidth(), texReg.getRegionHeight());
//    }
    protected coreActor(Entity entity){
        this.entity = entity;
    }
    public void set0Entity(Entity entity){this.entity = entity;}
    public void set1TexReg(WorldResTexRegManager texRegManager){
        texReg = texRegManager.getTexRegByID(entity);
        setBounds(texReg.getRegionX(), texReg.getRegionY(),
                texReg.getRegionWidth(), texReg.getRegionHeight());
    }
    public void set2World(Group world){
        world.addActor(this);
        setUserObject(data);
    }
    @Override
    public void draw (Batch batch, float parentAlpha) {
//        color = getColor();
//        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texReg, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
    public int getEntityID() {
        return this.entity.getID();
    }
    public String getEntityName(){
        return entity.name();
    }

    public void setIndexID(int indexID) {
        data.indexID = indexID;
    }
    public int getIndexID() {
        return ((Data)getUserObject()).indexID;
    }


    public boolean getColision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }
}
