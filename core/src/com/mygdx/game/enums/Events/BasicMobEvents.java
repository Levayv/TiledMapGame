package com.mygdx.game.enums.Events;

public enum BasicMobEvents {
    NONE(0),
    STOP(1),
    WALK(2),
    REST(3),
    DANCE_LIKE_THERE_IS_NO_TOMMOROW(66);
    int id;
    BasicMobEvents(int i){id = i;}
    public int getID(){return id;}
}
