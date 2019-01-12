package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.enums.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Factory {
    private DeadPool deadPool;
    private CoreTileData coreTileData;
    private boolean isBuilding;
    private boolean isDestroying;
    private boolean canBuild;
    private boolean canDestroy;
    private Something ghostBuild;
    private Something ghostDestroy;
    private WorldResTexRegManager texRegManager;
    private WorldResAnimManager animManager;
    private Group worldGroup;
    private WorldPositionManager posManager;
    private final Vector2 nullVector = new Vector2(-1000,-1000);
    private List<Something> somethingsOnDuty;
    // for debug only
    private int x;
    private int y;
    // build and destroy phases are mutually exclusive
    Factory (Group worldGroup,
             WorldResTexRegManager texRegManager,
             WorldResAnimManager animManager,
             int tileSize,
             int mapWidth,
             int mapHeight){
        this.worldGroup = worldGroup;
        this.texRegManager = texRegManager;
        this.animManager = animManager;
        ghostBuild = new Something(Entity.Ghost1);
        ghostBuild.set1TexReg(texRegManager);
        ghostBuild.set2World(worldGroup);
        ghostBuild.setBorders();
        ghostBuild.setPosition(nullVector.x, nullVector.y);
        ghostBuild.setTouchable(Touchable.disabled);
        ghostBuild.setVisible(false);
        ghostDestroy = new Something(Entity.Ghost2);
        ghostDestroy.set1TexReg(texRegManager);
        ghostDestroy.set2World(worldGroup);
        ghostDestroy.setBorders();
        ghostDestroy.setPosition(nullVector.x, nullVector.y);
        ghostDestroy.setTouchable(Touchable.disabled);
        ghostDestroy.setVisible(false);
        posManager = new WorldPositionManager(tileSize);
        coreTileData = new CoreTileData(mapWidth,mapHeight);
        deadPool = new DeadPool();
        somethingsOnDuty = new ArrayList<Something>();
    }
    // Building / Destroying
    public void buildOnClick(Entity entity){
        this.build(entity);
    }
    public void destroyOnClick(int indexID){
        this.destroy(indexID);
    }
    public void buildOnEvent(Entity entity, Vector2 pos){
        this.isBuilding = true;
        this.canBuild = true;
        posManager.update(pos);
        x = posManager.getPosX();
        y = posManager.getPosY();
        this.build(entity);
        this.isBuilding = false;
        this.canBuild = false;
    }
    public void destroyOnEvent(int indexID, Vector2 pos){
        this.isDestroying = true;
        this.canDestroy = true;
        posManager.update(pos);
        x = posManager.getPosX();
        y = posManager.getPosY();
        this.destroy(indexID);
        this.isDestroying = false;
        this.canDestroy = false;
    }
    private void build(Entity entity){ //todo event based
        if (isBuilding){
            if (canBuild){
                // worldGroup.swapActor()
                // todo make ghost always on foreground
                // todo add x y, id >> convert entity
                Gdx.app.log("Factory", "Building  Something at "+x+"/"+y);
                Gdx.app.debug("Debug: Factory", "build() method *START* ------------"+
                        " ------------ ------------ ------------ ------------ ------------");
                Gdx.app.debug("Debug: Factory", "arg entityID="+entity.getID());
                Gdx.app.debug("Debug: Factory", "arg entity="+entity);
                Gdx.app.debug("Debug: Factory", "x/y="+x+"/"+y);
                Gdx.app.debug("Debug: Factory", "OnDuty.size()="+somethingsOnDuty.size());
                Gdx.app.debug("Debug: Factory", "");
                // tear off DeadPool , and store in ... from ...onDuty
                Something tavern = deadPool.createSomething(entity);
                somethingsOnDuty.add(tavern);
                // Object property init
                tavern.set0Entity(entity);
                tavern.set3IndexID(somethingsOnDuty.size()-1);
                tavern.set1TexReg(texRegManager);
                tavern.set2World(worldGroup);
                tavern.setBorders();
                tavern.setVisible(true);
                tavern.setPosition(posManager.getPosX(), posManager.getPosY());
                tavern.setName("Tavern");
                // inform coreTileData about data update
                coreTileData.buildingHere(posManager.getTileX(), posManager.getTileY());
                // remove ghost from screen, it will go invisible after phase end
                ghostBuild.setPosition(nullVector.x, nullVector.y);
//                stopBuildingPhase();
            }
        }
    }
    private void destroy(int indexID){
        if (isDestroying){
            if (canDestroy){
                Gdx.app.log("Factory", "Destroying Something at "+x+"/"+y);
                Gdx.app.debug("Debug: Factory", "destroy() method *START* ------------"+
                        " ------------ ------------ ------------ ------------ ------------");
                Gdx.app.debug("Debug: Factory", "arg indexID="+indexID);
                Gdx.app.debug("Debug: Factory", "x/y="+x+"/"+y);
                Gdx.app.debug("Debug: Factory", "OnDuty.size()="+somethingsOnDuty);
                // tear off ... from ...onDuty , and store in DeadPool
                deadPool.burySomething(somethingsOnDuty.remove(indexID));
                // iterate threw ...onDuty , to fix shifted ID's
                for (int i = indexID; i < somethingsOnDuty.size() ; i++) { // -1 ?
                    Gdx.app.debug("Debug: Factory", "for i="+i);
                    Gdx.app.debug("Debug: Factory", "for onDutySize="+somethingsOnDuty.size());
                    Gdx.app.debug("Debug: Factory", "duty ID [BUGGY]="+somethingsOnDuty.get(i).getIndexID());
                    // shifting ID to right in Actor's container via getIndexID & getIndexID
                    somethingsOnDuty.get(i).set3IndexID(somethingsOnDuty.get(i).getIndexID()-1);
                    Gdx.app.debug("Debug: Factory", "duty ID [FIXED]="+somethingsOnDuty.get(i).getIndexID());
                }
                Gdx.app.debug("Debug: Factory", "Tile ="+posManager.getTileX()+" / "+ posManager.getTileY());
                // inform coreTileData about data update
                coreTileData.destroyingThis(posManager.getTileX(), posManager.getTileY());
                // remove ghost from screen, it will go invisible after phase end
                ghostDestroy.setPosition(nullVector.x,nullVector.y);
                Gdx.app.debug("Debug: Factory", "destroy() method  *END*  ------------"+
                        " ------------ ------------ ------------ ------------ ------------");
                //stopDestroyingPhase();

            }
        }
    }
    // Building / Destroying Phase changes
    public void startBuildingPhase(){
        Gdx.app.log("Factory", "start Building Phase");
        isBuilding = true;
        ghostBuild.setVisible(true);
    }
    public void stopBuildingPhase(){
        Gdx.app.log("Factory", "stop Building Phase");
        isBuilding = false;
        ghostBuild.setVisible(false);
        ghostBuild.setPosition(nullVector.x, nullVector.y);
    }
    public void swapBuildingPhase(){
        if (isBuilding)
            stopBuildingPhase();
        else
            startBuildingPhase();
    }
    public void startDestroyingPhase(){
        Gdx.app.log("Factory", "start Destroying Phase");
        isDestroying = true;
        ghostDestroy.setVisible(true);
    }
    public void stopDestroyingPhase(){
        Gdx.app.log("Factory", "stop Destroying Phase");
        isDestroying = false;
        ghostDestroy.setVisible(false);
        ghostDestroy.setPosition(nullVector.x, nullVector.y);
    }
    public void swapDestroyingPhase(){
        if (isDestroying)
            stopDestroyingPhase();
        else
            startDestroyingPhase();
    }
    public boolean isBuilding(){
        return isBuilding;
    }
    public boolean isDestroying(){
        return isDestroying;
    }
    // Updates during render, must be highly optimal
    public void update(Vector2 pos){ // arg = stage pos
        if (this.isBuilding){ // only during build phase
            this.updateBuildGhostPosition(pos);
        }
        if (this.isDestroying){ // only during destroy phase
            this.updateDestroyGhostPosition(pos);
        }
    }
    private void updateBuildGhostPosition(Vector2 pos){ // arg = stage pos
        if (canBuildQ(pos)){
            ghostBuild.setPosition(posManager.getPosX(), posManager.getPosY());
            canBuild = true;
        }else {
            canBuild = false;
        }
    }
    private void updateDestroyGhostPosition(Vector2 pos){ // arg = stage pos
        if (canDestroyQ(pos)){
            ghostDestroy.setPosition(posManager.getPosX(), posManager.getPosY());
            canDestroy = true;
        }else {
            canDestroy = false;
        }
    }
    public boolean canBuildQ(Vector2 pos){ // arg = stage pos
        posManager.update(pos); // find tileX,tileY for boolean[][], posX,poxY for Actors
        x = posManager.getPosX();
        y = posManager.getPosY();
        return coreTileData.canBuildHere(posManager.getTileX(), posManager.getTileY());
    }
    public boolean canDestroyQ(Vector2 pos){ // arg = stage pos
        posManager.update(pos); // find tileX,tileY for boolean[][], posX,poxY for Actor
        x = posManager.getPosX();
        y = posManager.getPosY();
        return coreTileData.canDestroyThis(posManager.getTileX(), posManager.getTileY());
    }
}
