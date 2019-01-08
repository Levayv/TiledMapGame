package com.mygdx.game.ants.a;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.WorldResTexRegManager;
import com.mygdx.game.enums.EntityTex;

public class coreActor extends Actor {
    public TextureRegion texReg;
    protected EntityTex entityTex;
    private Color color;

//    public coreActor (TextureRegion texReg) {
////        texReg = new TextureRegion(new Texture(Gdx.files.internal("tree.png")));
//        this.texReg = texReg;
//        setBounds(texReg.getRegionX(), texReg.getRegionY(),
//                texReg.getRegionWidth(), texReg.getRegionHeight());
//    }
    protected coreActor(EntityTex entityTex){
        this.entityTex = entityTex;
    }
    public void set1TexReg(WorldResTexRegManager texRegManager){
        texReg = texRegManager.getTexRegByID(entityTex);
        setBounds(texReg.getRegionX(), texReg.getRegionY(),
                texReg.getRegionWidth(), texReg.getRegionHeight());
    }
    public void set2World(Group world){
        world.addActor(this);
    }
    @Override
    public void draw (Batch batch, float parentAlpha) {
        color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texReg, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
    public int getEntityID() {
        return this.entityTex.getID();
    }
    public String getEntityName(){
        return entityTex.name();
    }
}
