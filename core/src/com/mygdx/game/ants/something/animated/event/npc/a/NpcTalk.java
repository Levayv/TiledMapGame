package com.mygdx.game.ants.something.animated.event.npc.a;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Vector1;

public class NpcTalk {
    public Vector1 outerBorder;
    private Vector1 center;
    public NpcFaction faction;
    public NpcTalk(){
        this.center = new Vector1();
        this.faction = new NpcFaction();
        this.outerBorder = new Vector1();
    }

    public Vector1 getCenter() {
        return center;
    }

    public void setCenter(Vector1 center) {
        this.center = center;
    }
}
