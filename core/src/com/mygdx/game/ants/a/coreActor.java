package com.mygdx.game.ants.a;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import com.mygdx.game.enums.entity.EntityAnimation;
import com.mygdx.game.enums.entity.EntityClass;
import com.mygdx.game.enums.entity.EntityTex;
import com.mygdx.game.world.WorldResTexRegManager;

public class coreActor extends Actor {
    public TextureRegion texReg;
    private Color color;
    private InternalActorData internalData = new InternalActorData();
    protected EntityTex entityTex;
    protected EntityClass entityClass;

    protected coreActor() {
    }

    public void set0Entity(EntityClass entityClass) {
        this.entityClass = entityClass;
    }

    public void set01EntityTex(EntityTex entityTex) {
        this.entityTex = entityTex;
    }


    public void set1TexReg(WorldResTexRegManager texRegManager) {
        texReg = texRegManager.getTexRegByID(entityTex);
        setBounds(texReg.getRegionX(), texReg.getRegionY(),
                texReg.getRegionWidth(), texReg.getRegionHeight());
    }
    public void set2World(Group world) {
        world.addActor(this);
        setUserObject(internalData);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
//        color = getColor();
//        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texReg, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
    public int getEntityID() {
        return this.entityTex.getID();
    }
    public String getEntityName() {
        return entityTex.name();
    }
    public void setMyIndexID(int index) {
        internalData.index = index;
    }
    public int getMyIndex() {
        return ((InternalActorData) getUserObject()).index;
    }


//    public boolean getColision() {
//        return collision;
//    }
//
//    public void setCollision(boolean collision) {
//        this.collision = collision;
//    }


}
