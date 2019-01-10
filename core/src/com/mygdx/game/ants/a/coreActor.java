package com.mygdx.game.ants.a;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.world.WorldResTexRegManager;
import com.mygdx.game.enums.Entity;

public class coreActor extends Actor {
    public TextureRegion texReg;
    protected Entity entity;
    private Color color;

//    public coreActor (TextureRegion texReg) {
////        texReg = new TextureRegion(new Texture(Gdx.files.internal("tree.png")));
//        this.texReg = texReg;
//        setBounds(texReg.getRegionX(), texReg.getRegionY(),
//                texReg.getRegionWidth(), texReg.getRegionHeight());
//    }
    protected coreActor(Entity entity){
        this.entity = entity;
    }
    public void set1TexReg(WorldResTexRegManager texRegManager){
        texReg = texRegManager.getTexRegByID(entity);
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
        return this.entity.getID();
    }
    public String getEntityName(){
        return entity.name();
    }
}
