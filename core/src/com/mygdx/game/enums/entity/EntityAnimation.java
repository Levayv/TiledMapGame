package com.mygdx.game.enums.entity;

public enum EntityAnimation {
    NONE(0),
    DOOR_OPEN(1),
    DOOR_CLOSE_NOT_USED(2),
    PUMPKIN(3),
    SLIME_1(11),
    SLIME_2(12),
    SLIME_3(13),
    EXPLOSION(14),
    TEMP(66);

    int id;
    EntityAnimation(int i){id = i;}
    public int GetID(){return id;}
}
