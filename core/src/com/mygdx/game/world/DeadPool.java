package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.enums.entity.EntityTex;

import java.util.ArrayList;
import java.util.List;

class DeadPool {
    private List<Something> graveOfSomething;
    private Something bufferSomething;
    DeadPool(){
        graveOfSomething = new ArrayList<Something>();
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
    public void burySomething(Something something){ // must wipe internal state
        Gdx.app.debug("DeadPool", "burying Something, total:"+(graveOfSomething.size()+1));
        something.destroy();
        graveOfSomething.add(something);
    }
}
