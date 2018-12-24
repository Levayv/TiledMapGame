package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class shit extends Actor {
    TextureRegion texReg;

    public shit (TextureRegion texReg) {
//        texReg = new TextureRegion(new Texture(Gdx.files.internal("tree.png")));
        this.texReg = texReg;
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
