package com.mygdx.game.enums.Events;

public enum FactoryEvents {
    NONE(0),
    AAA(1),
    BBB(2),
    CCC(3),
    DANCE_LIKE_THERE_IS_NO_TOMMOROW(66);
    int id;
    FactoryEvents(int i){id = i;}
    public int getID(){return id;}
}
