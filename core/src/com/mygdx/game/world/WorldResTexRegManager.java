package com.mygdx.game.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.enums.entity.Entity;

public class WorldResTexRegManager {

    private TextureRegion[] texRegs;
    private TextureRegion texReg;
    private int texRegsSize = 0;
    private int arrayCap;
    public WorldResTexRegManager(int texRegArrayCap){
        this.arrayCap = texRegArrayCap;
        texRegs = new TextureRegion[arrayCap];
    }
    public void addTexReg(Entity entity, TextureRegion texReg){
        texRegs[entity.getID()] = texReg;
        texRegsSize++;
    }
    public TextureRegion getTexRegByID(Entity entity){
        texReg = texRegs[entity.getID()];
        return texReg;
    }
}
