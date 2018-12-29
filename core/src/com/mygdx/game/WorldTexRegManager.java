package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.enums.Entity;

public class WorldTexRegManager {

    private TextureRegion[] texRegs;
    private TextureRegion texReg;
    private int texRegsSize = 0;
    private int arrayCap;
    WorldTexRegManager(int texRegArrayCap){
        this.arrayCap = texRegArrayCap;
        texRegs = new TextureRegion[arrayCap];
    }
    public void addTexReg(Entity entity, TextureRegion texReg){
        texRegs[entity.GetID()] = texReg;
        texRegsSize++;
    }
    public TextureRegion getTexRegByID(Entity entity){
        texReg = texRegs[entity.GetID()];
        return texReg;
    }
}
