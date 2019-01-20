package com.mygdx.game.ants.something.animated.event.npc.a;

import com.mygdx.game.Vector1;

public class NpcTalk {
    public Vector1 center;
    public NpcFaction faction;
    public NpcTalk(){
        this.center = new Vector1();
        this.faction = new NpcFaction();
    }
}
