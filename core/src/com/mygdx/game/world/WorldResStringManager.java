package com.mygdx.game.world;

import com.mygdx.game.enums.entity.EntityTex;

public class WorldResStringManager { //todo integrate [unused class]
    private String[] strings;
    private String string;
    private int namesSize = 0;
    private int arrayCap;
    public WorldResStringManager(int namesArrayCap){
        this.arrayCap = namesArrayCap;
        strings = new String[arrayCap];
    }
    public void addTexReg(EntityTex entityTex, String name){
        strings[entityTex.getID()] = name;
        namesSize++;
    }
    public String getTexRegByID(EntityTex entityTex){
        string = strings[entityTex.getID()];
        return string;
    }
}
