package com.mygdx.game.world;

import com.mygdx.game.enums.Entity;

public class WorldResStringManager { //todo integrate [unused class]
    private String[] strings;
    private String string;
    private int namesSize = 0;
    private int arrayCap;
    public WorldResStringManager(int namesArrayCap){
        this.arrayCap = namesArrayCap;
        strings = new String[arrayCap];
    }
    public void addTexReg(Entity entity, String name){
        strings[entity.getID()] = name;
        namesSize++;
    }
    public String getTexRegByID(Entity entity){
        string = strings[entity.getID()];
        return string;
    }
}
