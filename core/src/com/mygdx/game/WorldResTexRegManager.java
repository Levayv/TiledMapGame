package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.enums.EntityTex;

public class WorldResTexRegManager {

    private TextureRegion[] texRegs;
    private TextureRegion texReg;
    private int texRegsSize = 0;
    private int arrayCap;
    WorldResTexRegManager(int texRegArrayCap){
        this.arrayCap = texRegArrayCap;
        texRegs = new TextureRegion[arrayCap];
    }
    public void addTexReg(EntityTex entityTex, TextureRegion texReg){
        texRegs[entityTex.getID()] = texReg;
        texRegsSize++;
    }
    public TextureRegion getTexRegByID(EntityTex entityTex){
        texReg = texRegs[entityTex.getID()];
        return texReg;
    }
}
