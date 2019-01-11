package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.ants.something.animated.event.pc.a.Player;
import com.mygdx.game.enums.Entity;

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
    private WorldPositionManager positionManager;
    private final Vector2 nullVector = new Vector2(-1000,-1000);
    private int tileX;
    private int tileY;
    private int playerIndex;
    Factory (Group worldGroup,
             WorldResTexRegManager texRegManager,
             WorldResAnimManager animManager,
             int tileSize,
             int mapWidth,
             int mapHeight){ //todo make Factory singleton or not ?
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
        positionManager = new WorldPositionManager(32);
        coreTileData = new CoreTileData(mapWidth,mapHeight);
        deadPool = new DeadPool(10);
    }
    public void updateBuildGhostPosition(Vector2 pos){
        positionManager.toTile(pos);
        if (coreTileData.canBuildHere(positionManager.getTileX(),positionManager.getTileY())){
            ghostBuild.setPosition(positionManager.getPosX(),positionManager.getPosY());
            canBuild = true;
        }
    }
    public void updateDestroyGhostPosition(Vector2 pos){
        positionManager.toTile(pos);
        if (!coreTileData.canBuildHere(positionManager.getTileX(),positionManager.getTileY())){
            ghostDestroy.setPosition(positionManager.getPosX(),positionManager.getPosY());
            canDestroy = true;
        }
    }
    public void build(Entity entity, float x , float y){ //todo event based
        if (isBuilding){
            if (canBuild){
//                worldGroup.swapActor() //todo make ghost always on foreground
                Gdx.app.log("Factory", "Building sh*t at posx posy fixme! ");
                //todo add x y, id >> convert entity
                Something tavern = deadPool.createSomething(entity);
                tavern.set1TexReg(texRegManager);
                tavern.set2World(worldGroup);
                tavern.setBorders();
                tavern.setPosition(ghostBuild.getX(),ghostBuild.getY());
                tavern.setName("Tavern");
                stopBuildingPhase();
                coreTileData.buildingHere(positionManager.getTileX(),positionManager.getTileY());
            }
        }
    }
    public void destroy(Something something, float x , float y){
        if (isDestroying){
            if (canDestroy){
                Gdx.app.log("Factory", "Destroying sh*t at posx posy fixme! "); //todo add x y
                deadPool.burrySomething(new Something(Entity.Temp));
//                something.setVisible(false);
//                something.setPosition(nullVector.x, nullVector.y);
//                worldGroup.removeActor(something);
            }
        }
    }
    public void update(Vector2 pos){
        if (this.isBuilding){
            this.updateBuildGhostPosition(pos);
        }
        if (this.isDestroying){
            this.updateDestroyGhostPosition(pos);
        }
    }
    private void startBuildingPhase(){
        Gdx.app.log("Factory", "startBuildingPhase");
        isBuilding = true;
        ghostBuild.setVisible(true);
        worldGroup.setColor(Color.GREEN);
    }
    public void stopBuildingPhase(){
        Gdx.app.log("Factory", "stopBuildingPhase");
        isBuilding = false;
        ghostBuild.setVisible(false);
        ghostBuild.setPosition(-1000,-1000);
    }
    public void swapBuildingPhase(){
        if (isBuilding)
            stopBuildingPhase();
        else
            startBuildingPhase();
    }
    public boolean isBuilding(){
        return isBuilding;
    }
    private void startDestroyingPhase(){
        Gdx.app.log("Factory", "startDestroyingPhase");
        isDestroying = true;
        ghostDestroy.setVisible(true);
    }
    public void stopDestroyingPhase(){
        Gdx.app.log("Factory", "stopDestroyingPhase");
        isDestroying = false;
        ghostDestroy.setVisible(false);
        ghostDestroy.setPosition(-1000,-1000);
    }
    public void swapDestroyingPhase(){
        if (isDestroying)
            stopDestroyingPhase();
        else
            startDestroyingPhase();
    }
}
