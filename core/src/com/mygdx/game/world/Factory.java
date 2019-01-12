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
    private WorldPositionManager positionManager;
    private final Vector2 nullVector = new Vector2(-1000,-1000);
    private List<Something> somethingsOnDuty;
    //todo make Factory singleton or not ?
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
        positionManager = new WorldPositionManager(tileSize);
        coreTileData = new CoreTileData(mapWidth,mapHeight);
        deadPool = new DeadPool();
        somethingsOnDuty = new ArrayList<Something>();
    }
    private void updateBuildGhostPosition(Vector2 pos){
        if (canBuildQ(pos)){
            ghostBuild.setPosition(positionManager.getPosX(),positionManager.getPosY());
            canBuild = true;
        }else {
            canBuild = false;
        }
    }
    private void updateDestroyGhostPosition(Vector2 pos){
        if (canDestroyQ(pos)){
            ghostDestroy.setPosition(positionManager.getPosX(),positionManager.getPosY());
            canDestroy = true;
        }else {
            canDestroy = false;
        }
    }
    public boolean canBuildQ(Vector2 pos){
        positionManager.toTile(pos);
        return coreTileData.canBuildHere(positionManager.getTileX(),positionManager.getTileY());
    }
    public boolean canDestroyQ(Vector2 pos){
        positionManager.toTile(pos);
        return coreTileData.canDestroyThis(positionManager.getTileX(),positionManager.getTileY());
    }
    public void buildOnClick(Entity entity){
        this.build(entity);
    }
    public void buildOnEvent(Entity entity, float x , float y){
        this.isBuilding = true;
        canBuildQ(new Vector2(x,y));
        this.build(entity);
        this.isBuilding = false;
        this.canBuild = false;
    }
    private void build(Entity entity){ //todo event based
        if (isBuilding){
            if (canBuild){
//                worldGroup.swapActor() //todo make ghost always on foreground
                Gdx.app.log("Factory", "Building sh*t at posx posy fixme! ");
                //todo add x y, id >> convert entity
                Something tavern = deadPool.createSomething(entity);
                somethingsOnDuty.add(tavern);
                System.out.println("!!!b"+(somethingsOnDuty.size()-1));
                tavern.set0Entity(entity);
                tavern.setIndexID(somethingsOnDuty.size()-1);
                tavern.set1TexReg(texRegManager);
                tavern.set2World(worldGroup);
                tavern.setBorders();
                tavern.setVisible(true);
                tavern.setPosition(positionManager.getPosX(),positionManager.getPosY()); //todo fix
                tavern.setName("Tavern");
//                stopBuildingPhase();
                System.out.println("!pos B ="+positionManager.getTileX()+" / "+positionManager.getTileY());
                coreTileData.buildingHere(positionManager.getTileX(),positionManager.getTileY());
                ghostBuild.setPosition(nullVector.x, nullVector.y);
            }
        }
    }
    public void destroyOnClick(int indexID, float x , float y){
        this.destroy(indexID,x ,y);
    }
    public void destroyOnEvent(int indexID, float x , float y){
        this.isDestroying = true;
        canDestroyQ(new Vector2(x,y));
        this.destroy(indexID,x ,y);
        this.isDestroying = false;
        this.canDestroy = false;
    }
    private void destroy(int indexID, float x , float y){
        if (isDestroying){
            if (canDestroy){
                Gdx.app.log("Factory", "Destroying Something at "+x+"/"+y);
                Gdx.app.debug("Debug: Factory", "destroy() method *START* ------------"+
                        " ------------ ------------ ------------ ------------ ------------");
                Gdx.app.debug("Debug: Factory", "arg indexID="+indexID);
                Gdx.app.debug("Debug: Factory", "arg x/y="+x+"/"+y);
                Gdx.app.debug("Debug: Factory", "OnDuty.size()="+somethingsOnDuty);
                Gdx.app.debug("Debug: Factory", "");
                // tear off ... from ...onDuty , and store in DeadPool
                deadPool.burySomething(somethingsOnDuty.remove(indexID));
                // iterate threw ...onDuty , to fix shifted ID's
                for (int i = indexID; i < somethingsOnDuty.size() ; i++) { // -1 ?
                    Gdx.app.debug("Debug: Factory", "for i="+i);
                    Gdx.app.debug("Debug: Factory", "for onDutySize="+somethingsOnDuty.size());
                    Gdx.app.debug("Debug: Factory", "duty ID [BUGGY]="+somethingsOnDuty.get(i).getIndexID());
                    // shifting ID to right in Actor's container via getIndexID & getIndexID
                    somethingsOnDuty.get(i).setIndexID(somethingsOnDuty.get(i).getIndexID()-1);
                    Gdx.app.debug("Debug: Factory", "duty ID [FIXED]="+somethingsOnDuty.get(i).getIndexID());
                }
                System.out.println("!pos D ="+positionManager.getTileX()+" / "+positionManager.getTileY());
                coreTileData.destroyingThis(positionManager.getTileX(),positionManager.getTileY());
                ghostDestroy.setPosition(nullVector.x,nullVector.y);
                Gdx.app.debug("Debug: Factory", "destroy() method  *END*  ------------"+
                        " ------------ ------------ ------------ ------------ ------------");
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
}
