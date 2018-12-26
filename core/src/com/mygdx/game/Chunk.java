package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Group;

public class Chunk extends Group {
    private shit rootActor;
    public int testInt = 1;
    public float testfloat = 1.1f;
    public String testString = "str";

    public void setRootTexReg(TextureRegion texReg){
        rootActor = new shit(texReg);
        this.addActor(rootActor);
    }

    public void setRootWidth(int width) {
        rootActor.texReg.setRegionWidth(width);
    }

    public void setRootHeight(int height) {
        rootActor.texReg.setRegionHeight(height);
    }

}