package com.mygdx.game.enums;

public enum BasicEvents {
    NONE(0),
    OPEN(1),
    CLOSE(2),
    SWITCH(3),
    DANCE_LIKE_THERE_IS_NO_TOMMOROW(66);
    int id;
    BasicEvents(int i){id = i;}
    public int getID(){return id;}
}
