package com.mygdx.game.enums.Events;

public enum BasicEvents {
    NONE(0),
    DOOR_OPEN(1),
    DOOR_CLOSE(2),
    DOOR_SWITCH(3),
    NONE4(4),
    NONE5(5),
    NONE6(6),
    NONE7(7),
    NONE8(8),
    NONE9(9),
    NONE10(10),
    MOB_STOP(11),
    MOB_WALK(12),
    MOB_REST(13),
    NONE14(14),
    NONE15(15),
    NONE16(16),
    NONE17(17),
    NONE18(18),
    NONE19(19),
    NONE20(20),
    FACTORY_AAA(21),
    FACTORY_BBB(22),
    FACTORY_CCC(23),
    NONE24(24),
    NONE25(25),
    NONE26(26),
    NONE27(27),
    NONE28(28),
    NONE29(29),
    NONE30(30),
    NPC_MOVED(31),
    NPC_T2(32),
    NPC_T3(33),
    DANCE_LIKE_THERE_IS_NO_TOMORROW(66);
    int id;
    BasicEvents(int i){id = i;}
    public int getID(){return id;}
}
