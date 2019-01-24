package com.mygdx.game.enums.entity;

public enum EntityAnimation {
    NONE(0),
    DOOR_OPEN(1),
    DOOR_CLOSE_NOT_USED(2),
    PUMPKIN(3),
    PUMPKIN_MOVING(4),
    SLIME_1(11),
    SLIME_2(12),
    SLIME_3(13),
    EXPLOSION(14),
    TOWER(15),
    TEMP(66);

    int id;
    EntityAnimation(int i){id = i;}
    public int getID(){return id;}
}

