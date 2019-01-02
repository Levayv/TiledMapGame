package com.mygdx.game.ants;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.WorldResTexRegManager;
import com.mygdx.game.enums.Entity;

public class coreActor extends Actor {
    public TextureRegion texReg;
    private Entity entity;

//    public coreActor (TextureRegion texReg) {
////        texReg = new TextureRegion(new Texture(Gdx.files.internal("tree.png")));
//        this.texReg = texReg;
//        setBounds(texReg.getRegionX(), texReg.getRegionY(),
//                texReg.getRegionWidth(), texReg.getRegionHeight());
//    }
    public coreActor(Entity entity, WorldResTexRegManager texRegManager, Group world){
        this.entity = entity;
        world.addActor(this);
        texReg = texRegManager.getTexRegByID(entity);
        setBounds(texReg.getRegionX(), texReg.getRegionY(),
                texReg.getRegionWidth(), texReg.getRegionHeight());
    }
    @Override
    public void draw (Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texReg, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public int getEntityID() {
        return entity.GetID();
    }
    public String getEntityName(){
        return entity.name();
    }
}
