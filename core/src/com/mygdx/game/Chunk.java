package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

public class Chunk extends Group {
    private shit rootActor;

    public Chunk(TextureRegion texReg) {
        rootActor = new shit(texReg);
//        rootActor.texReg = texReg;
        this.addActor(rootActor);
    }

    public void setRootWidth(int width) {
        rootActor.texReg.setRegionWidth(width);
    }

    public void setRootHeight(int height) {
        rootActor.texReg.setRegionHeight(height);
    }
}