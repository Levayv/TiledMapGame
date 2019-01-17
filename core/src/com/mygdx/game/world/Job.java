package com.mygdx.game.world;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Vector1;
import com.mygdx.game.ants.something.animated.event.npc.a.Npc;

public class Job {
    public boolean endless;
    public String task;
    public int min;
    public int hrs;
    public int day;
    public Vector2[] vvv;
    public Npc[] npcs;
    public int next = 0;
    public void start(){

        if (next<npcs.length){
            npcs[next].moveToPosition(vvv);
            next++;

        }





        System.out.println(task);
    }
    public void startEndless(){

        if (next<npcs.length){
            npcs[next].moveToPosition(vvv);
            next++;
        }
        System.out.println(task);
    }

}
