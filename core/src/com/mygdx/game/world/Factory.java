package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.enums.Entity;

public class Factory {
    private boolean isBuilding;
    private boolean canBuild = true; // todo canBuild integration
    private Something ghost;
    private WorldResTexRegManager texRegManager;
    private WorldResAnimManager animManager;
    private Group worldGroup;
    Factory (Group worldGroup,
             WorldResTexRegManager texRegManager,
             WorldResAnimManager animManager){ //todo make Factory singleton
        this.worldGroup = worldGroup;
        this.texRegManager = texRegManager;
        this.animManager = animManager;
        ghost = new Something(Entity.Ghost);
        ghost.set1TexReg(texRegManager);
        ghost.set2World(worldGroup);
        ghost.setBorders();
        ghost.setPosition(0,0);
        ghost.setTouchable(Touchable.disabled);
        ghost.setVisible(false);
    }
    public void updateGhostPosition(float x, float y){
        ghost.setPosition((int)(x+10),(int)(y+10));
    }
    public void build(int id, float x , float y){
        if (isBuilding){
            if (canBuild){
                Gdx.app.log("Factory", "Building sh*t at posx posy fixme! "); //todo add x y
                System.out.println("id="+id); // todo remove this
                Something tavern = new Something(Entity.Temp);
                tavern.set1TexReg(texRegManager);
                tavern.set2World(worldGroup);
                tavern.setBorders();
                tavern.setPosition(x,y);
                tavern.setName("Tavern");
                stopBuildingPhase();
            }
        }
    }
    public void update(float x, float y){
        if (this.isBuilding()){
            this.updateGhostPosition(x,y);
        }
    }


    public void startBuildingPhase(){
        isBuilding = true;
        ghost.setVisible(true);
    }
    public void stopBuildingPhase(){
        isBuilding = false;
        ghost.setVisible(false);

    }
    public void swapBuildingPhase(){
        if (isBuilding)
            stopBuildingPhase();
        else
            startBuildingPhase();
//        isBuilding = !isBuilding;
    }
    public boolean isBuilding(){
        return isBuilding;
    }
}
