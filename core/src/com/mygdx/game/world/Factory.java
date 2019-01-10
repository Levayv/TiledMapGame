package com.mygdx.game.world;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.enums.Entity;

public class Factory {
    private boolean isBuilding;
    private boolean canBuild;
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
    public void build(int id, float x , float y){
        if (!canBuild){ // todo canBuild integration
            System.out.println("id="+id);
            Something tavern = new Something(Entity.Temp);
            tavern.set1TexReg(texRegManager);
            tavern.set2World(worldGroup);
            tavern.setBorders();
            tavern.setPosition(x,y);
            stopBuilding();
        }
    }
    public void update(float x, float y){
        if (isBuilding){
            ghost.setVisible(true);
            ghost.setPosition(x+10,y+10);
        }else {
            ghost.setVisible(false);
        }
    }

    public void test(){}
    public void startBuilding(){isBuilding = true;}
    public void stopBuilding(){isBuilding = false;}
    public void swapBuilding(){isBuilding = !isBuilding;}
    public boolean isBuilding(){return isBuilding;}
}
