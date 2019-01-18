package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.Collider;
import com.mygdx.game.ants.something.a.AllData;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.enums.entity.EntityTex;

import java.util.ArrayList;
import java.util.List;

public class Builder {
    private boolean isBuilding;
    private boolean isDestroying;
    private boolean canBuild;
    private boolean canDestroy;

    private Something ghostBuild;
    private Something ghostDestroy;
    private final Vector2 nullVector = new Vector2(-1000,-1000);

    private List<Something> somethingsOnDuty;

    private WorldPositionManager posManager;
    private Group worldGroup;
    private WorldResTexRegManager texRegManager;
    private WorldResAnimManager animManager;
    private AllData allData;
    public DeadPool deadPool;
    private Collider collider;
    private CoreTileData coreTileData;

    // for debug only, posManager sets, Gdx.app.debug gets
    private int x;
    private int y;
    public Builder(Group worldGroup,
                   WorldResTexRegManager texRegManager,
                   WorldResAnimManager animManager,
                   AllData allData,
                   DeadPool deadPool,
                   Collider collider,
                   CoreTileData coreTileData,
                   int tileSize,
                   int mapWidth,
                   int mapHeight){
        this.worldGroup = worldGroup;
        this.texRegManager = texRegManager;
        this.animManager = animManager;
        this.allData = allData;
        this.deadPool = deadPool;
        this.collider = collider;
        this.coreTileData = coreTileData;
        // position helpers , actual to tile[i][j] and tile corner
        posManager = new WorldPositionManager(tileSize);
        // active entities goes onDuty
        somethingsOnDuty = new ArrayList<Something>();
        // init 2 ghosts for build and destroy
        ghostBuild = new Something();
        ghostBuild.set01EntityTex(EntityTex.Ghost1);
        ghostBuild.set11TexReg(texRegManager);
        ghostBuild.set21Bounds();
        ghostBuild.set22Borders();
        ghostBuild.set31World(worldGroup);
        ghostBuild.setPosition(nullVector.x, nullVector.y);
        ghostBuild.setTouchable(Touchable.disabled);
        ghostBuild.setVisible(false);
        ghostDestroy = new Something();
        ghostDestroy.set01EntityTex(EntityTex.Ghost2);
        ghostDestroy.set11TexReg(texRegManager);
        ghostDestroy.set21Bounds();
        ghostDestroy.set22Borders();
        ghostDestroy.set31World(worldGroup);
        ghostDestroy.setPosition(nullVector.x, nullVector.y);
        ghostDestroy.setTouchable(Touchable.disabled);
        ghostDestroy.setVisible(false);
    }
    // Building / Destroying
    public void buildOnClick(EntityTex entity){
        this.build(entity);
    }
    public void destroyOnClick(int indexID){
        this.destroy(indexID);
    }
    public void buildOnEvent(EntityTex entity, Vector2 pos){
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
    private void build(EntityTex entityTex){
        if (isBuilding){
            if (canBuild){
                // worldGroup.swapActor()
                // todo make ghost always on foreground
                // todo add x y, id >> convert entity
                Gdx.app.log("Builder", "Building Something at "+x+"/"+y+" with EID:"+entityTex.name());
                Gdx.app.debug("Debug: Builder", "build() method *START* ------------"+
                        " ------------ ------------ ------------ ------------ ------------");
                Gdx.app.debug("Debug: Builder", "arg entityID="+entityTex.getID());
                Gdx.app.debug("Debug: Builder", "arg entity="+entityTex);
                Gdx.app.debug("Debug: Builder", "x/y="+x+"/"+y);
                Gdx.app.debug("Debug: Builder", "OnDuty.size()="+somethingsOnDuty.size());
                Gdx.app.debug("Debug: Builder", "");
                // tear off DeadPool , and store in ... from ...onDuty
                //todo BOOOO its not a tavern
                Something tavern = deadPool.createSomething(entityTex);
                somethingsOnDuty.add(tavern);
                tavern.setMyIndex(somethingsOnDuty.size()-1);
                // Object property init
                tavern.set01EntityTex(entityTex);
                tavern.set11TexReg(texRegManager);
                tavern.set21Bounds();
                tavern.set31World(worldGroup);
                tavern.set22Borders();
                tavern.setVisible(true);
                tavern.setPosition(posManager.getPosX(), posManager.getPosY());
                tavern.setName("Tavern: "+entityTex.name());
                tavern.set09Data(allData.getSomethingDataByID(tavern.getEntityID()));
                // inform coreTileData about data update
                coreTileData.buildingHere(posManager.getTileX(), posManager.getTileY());
                // remove ghost from screen, it will go invisible after phase end
                ghostBuild.setPosition(nullVector.x, nullVector.y);
//                stopBuildingPhase();
                // add collider
                if (tavern.getData().isCollider)
                    collider.add(tavern.getBorder());
            }
        }
    }
    private void destroy(int indexID){
        if (isDestroying){
            if (canDestroy){
                Gdx.app.log("Builder", "Destroying Something at "+x+"/"+y);
                Gdx.app.debug("Debug: Builder", "destroy() method *START* ------------"+
                        " ------------ ------------ ------------ ------------ ------------");
                Gdx.app.debug("Debug: Builder", "arg indexID="+indexID);
                Gdx.app.debug("Debug: Builder", "x/y="+x+"/"+y);
                Gdx.app.debug("Debug: Builder", "OnDuty.size()="+somethingsOnDuty);
                // remove collider if it has one
                if (somethingsOnDuty.get(indexID).getData().isCollider)
                    collider.del(somethingsOnDuty.get(indexID).getBorder());
                // tear off ... from ...onDuty , and store in DeadPool (which wipes internal state)
                deadPool.burySomething(somethingsOnDuty.remove(indexID));
                // iterate threw ...onDuty , to fix shifted ID's
                for (int i = indexID; i < somethingsOnDuty.size() ; i++) { // -1 ?
                    Gdx.app.debug("Debug: Builder", "for i="+i);
                    Gdx.app.debug("Debug: Builder", "for onDutySize="+somethingsOnDuty.size());
                    Gdx.app.debug("Debug: Builder", "duty ID [BUGGY]="+somethingsOnDuty.get(i).getMyIndex());
                    // shifting ID to right in Actor's container via getMyIndex & getMyIndex
                    somethingsOnDuty.get(i).setMyIndex(somethingsOnDuty.get(i).getMyIndex()-1);
                    Gdx.app.debug("Debug: Builder", "duty ID [FIXED]="+somethingsOnDuty.get(i).getMyIndex());
                }
                Gdx.app.debug("Debug: Builder", "Tile ="+posManager.getTileX()+" / "+ posManager.getTileY());
                // inform coreTileData about data update
                coreTileData.destroyingThis(posManager.getTileX(), posManager.getTileY());
                // remove ghost from screen, it will go invisible after phase end
                ghostDestroy.setPosition(nullVector.x,nullVector.y);
                Gdx.app.debug("Debug: Builder", "destroy() method  *END*  ------------"+
                        " ------------ ------------ ------------ ------------ ------------");
                //stopDestroyingPhase();

            }
        }
    }
    // Building / Destroying Phase changes
    public void startBuildingPhase(){
        Gdx.app.debug("Builder", "start Building Phase");
        isBuilding = true;
        ghostBuild.setVisible(true);
    }
    public void stopBuildingPhase(){
        Gdx.app.debug("Builder", "stop Building Phase");
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
        Gdx.app.debug("Builder", "start Destroying Phase");
        isDestroying = true;
        ghostDestroy.setVisible(true);
    }
    public void stopDestroyingPhase(){
        Gdx.app.debug("Builder", "stop Destroying Phase");
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
        if (this.isBuilding){ //todo only during build phase
            this.updateBuildGhostPosition(pos);
        }
        if (this.isDestroying){ //todo only during destroy phase
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
