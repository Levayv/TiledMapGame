package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.ants.a.coreActor;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.enums.Entity;

import java.util.ArrayList;
import java.util.List;

class DeadPool {
    // my shitty pool
    private int graveSize;
    private List<Something> graveOfSomething;
    DeadPool(int graveSize){
        this.graveSize = graveSize;
        graveOfSomething = new ArrayList<Something>();
    }
    public Something createSomething(Entity entity){
        if (graveOfSomething.size()==0){
            Gdx.app.log("DeadPool", "no dead Something left");
            graveOfSomething.add(new Something(entity));
            return graveOfSomething.remove(0);
        }else {
            Gdx.app.log("DeadPool", "reviving Something, left:"+ graveOfSomething.size());
            return graveOfSomething.remove(graveOfSomething.size()-1);
        }
    }
    public void burrySomething(Something something){
        Gdx.app.log("DeadPool", "burying Something, total:"+(graveOfSomething.size()+1));
        graveOfSomething.add(something);
    }
}
