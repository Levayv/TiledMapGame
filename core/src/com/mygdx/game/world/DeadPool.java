package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.ants.something.animated.event.npc.a.Npc;
import com.mygdx.game.enums.entity.EntityAnimation;
import com.mygdx.game.enums.entity.EntityTex;

import java.util.ArrayList;
import java.util.List;

public class DeadPool {
    private List<Something> graveOfSomething;
    private Something bufferSomething;
    private List<Npc> graveOfNpc;
    private Npc bufferNpc;
    DeadPool(){
        graveOfSomething = new ArrayList<Something>();
        graveOfNpc       = new ArrayList<Npc>();
    }
    public Something createSomething(EntityTex entityTex){
        if (graveOfSomething.size()==0){
            Gdx.app.debug("DeadPool", "no dead Something left");
            bufferSomething = new Something();
            bufferSomething.set01EntityTex(entityTex);
            graveOfSomething.add(bufferSomething);
            bufferSomething = null;
            return graveOfSomething.remove(0);
        }else {
            Gdx.app.debug("DeadPool", "reviving Something, left:"+ graveOfSomething.size());
            return graveOfSomething.remove(graveOfSomething.size()-1);
        }
    }
    public Npc createNpc(EntityAnimation entityAnim){
        if (graveOfNpc.size()==0){
            Gdx.app.debug("DeadPool", "no dead Npc left");
            bufferNpc = new Npc();
            bufferNpc.set02EntityAnim(entityAnim);
            graveOfNpc.add(bufferNpc);
            bufferNpc = null;
            return graveOfNpc.remove(0);
        }else {
            Gdx.app.debug("DeadPool", "reviving Npc, left:"+ graveOfNpc.size());
            return graveOfNpc.remove(graveOfNpc.size()-1);
        }
    }
    public void burySomething(Something something){ // must wipe internal state
        Gdx.app.debug("DeadPool", "burying Something, total:"+(graveOfSomething.size()+1));
        something.destroy();
        graveOfSomething.add(something);
    }
    public void buryNpc(Npc npc){ // must wipe internal state
        Gdx.app.debug("DeadPool", "burying Npc, total:"+(graveOfSomething.size()+1));
        npc.destroy();
        graveOfNpc.add(npc);
    }
}
