package com.mygdx.game.enums.Events;

public enum NpcEvents {
    NONE(0),
    iMoved(1),
    npc2(2),
    npc3(3),
    DANCE_LIKE_THERE_IS_NO_TOMMOROW(66);
    int id;
    NpcEvents(int i){id = i;}
    public int getID(){return id;}
}
