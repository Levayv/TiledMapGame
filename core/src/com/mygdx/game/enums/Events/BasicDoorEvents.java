package com.mygdx.game.enums.Events;

public enum BasicDoorEvents {
    NONE(0),
    OPEN(1),
    CLOSE(2),
    SWITCH(3),
    DANCE_LIKE_THERE_IS_NO_TOMMOROW(66);
    int id;
    BasicDoorEvents(int i){id = i;}
    public int getID(){return id;}
}
