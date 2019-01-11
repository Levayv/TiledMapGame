package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.enums.Entity;

public class Factory {
    private DeadPool deadPool;
    private CoreTileData coreTileData;
    private boolean isBuilding;
    private boolean canBuild = false;
    private Something ghost;
    private WorldResTexRegManager texRegManager;
    private WorldResAnimManager animManager;
    private Group worldGroup;
    private WorldPositionManager positionManager;
    private int tileX;
    private int tileY;
    Factory (Group worldGroup,
             WorldResTexRegManager texRegManager,
             WorldResAnimManager animManager,
             int tileSize,
             int mapWidth,
             int mapHeight
             ){ //todo make Factory singleton
        this.worldGroup = worldGroup;
        this.texRegManager = texRegManager;
        this.animManager = animManager;
        ghost = new Something(Entity.Ghost);
        ghost.set1TexReg(texRegManager);
        ghost.set2World(worldGroup);
        ghost.setBorders();
        ghost.setPosition(-1000,-1000);
        ghost.setTouchable(Touchable.disabled);
        ghost.setVisible(false);
        positionManager = new WorldPositionManager(32);
        coreTileData = new CoreTileData(mapWidth,mapHeight);
        deadPool = new DeadPool();
    }
    public void updateGhostPosition(Vector2 pos){
        positionManager.toTile(pos);
        if (coreTileData.canBuildHere(positionManager.getTileX(),positionManager.getTileY())){
            ghost.setPosition(positionManager.getPosX(),positionManager.getPosY());
            canBuild = true;
        }
//        ghost.setPosition((int)(x-ghost.getWidth()/2),(int)(y-ghost.getHeight()/2));
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
                tavern.setPosition(ghost.getX(),ghost.getY());
                tavern.setName("Tavern");
                stopBuildingPhase();
                coreTileData.buildingHere(positionManager.getTileX(),positionManager.getTileY());
            }
        }
    }
    public void update(Vector2 pos){
        if (this.isBuilding()){
//            this.positionManager.convertPointerToTile(pos);
//            tileX = positionManager.getTileX();
//            tileY = positionManager.getTileY();
            this.updateGhostPosition(pos);
        }
    }


    public void startBuildingPhase(){
        isBuilding = true;
        ghost.setVisible(true);
        ghost.setColor(Color.GREEN);
    }
    public void stopBuildingPhase(){
        isBuilding = false;
        ghost.setVisible(false);
        ghost.setPosition(-1000,-1000);
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
