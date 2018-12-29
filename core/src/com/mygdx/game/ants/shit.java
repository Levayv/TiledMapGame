package com.mygdx.game.ants;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.WorldTexRegManager;
import com.mygdx.game.enums.Entity;

import sun.security.acl.WorldGroupImpl;

public class shit extends Actor {
    public TextureRegion texReg;
    public Entity entity;

    public shit (TextureRegion texReg) {
//        texReg = new TextureRegion(new Texture(Gdx.files.internal("tree.png")));
        this.texReg = texReg;
        setBounds(texReg.getRegionX(), texReg.getRegionY(),
                texReg.getRegionWidth(), texReg.getRegionHeight());
    }
    public shit(Entity entity, WorldTexRegManager texRegManager, Group world){
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
}
