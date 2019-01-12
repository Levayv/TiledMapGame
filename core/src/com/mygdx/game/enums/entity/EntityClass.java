package com.mygdx.game.enums.entity;

public enum EntityClass {
    None                    (0),
    Something               (1),
    AnimatedSomething       (2),
    AnimatedEventSomething  (3),
    Door                    (4),
    NonPlayableCharacter    (5),
    PlayableCharacter       (6),
    Player                  (7),
    TEMP                    (66);

    int id;
    EntityClass(int i){id = i;}
    public int GetID(){return id;}
}
