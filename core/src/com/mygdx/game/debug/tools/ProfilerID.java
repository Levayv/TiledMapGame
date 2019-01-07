package com.mygdx.game.debug.tools;

public enum  ProfilerID {
    GLOBAL(0),
    Player(1),
    NPC(2),
    Tower(3),
    Tree(11),
    Stone(12),
    Ore(13),
    Temp(66);

    int id;
    private ProfilerID(int i){id = i;}
    public int GetID(){return id;}

}
