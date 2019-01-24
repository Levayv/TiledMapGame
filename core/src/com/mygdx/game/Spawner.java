package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

import com.mygdx.game.ants.something.a.AllData;
import com.mygdx.game.ants.something.animated.event.npc.a.MyPath;
import com.mygdx.game.ants.something.animated.event.npc.a.Npc;
import com.mygdx.game.enums.entity.EntityAnimation;
import com.mygdx.game.enums.entity.EntityTex;
import com.mygdx.game.world.Clock;
import com.mygdx.game.world.CoreTileData;
import com.mygdx.game.world.DeadPool;
import com.mygdx.game.world.Factory;
import com.mygdx.game.world.Job;
import com.mygdx.game.world.MyPathFinder;
import com.mygdx.game.world.Router;
import com.mygdx.game.world.WorldManager;
import com.mygdx.game.world.WorldPositionManager;
import com.mygdx.game.world.WorldResAnimManager;
import com.mygdx.game.world.WorldResTexRegManager;

import java.util.ArrayList;
import java.util.List;


public class Spawner{
//    private boolean isBuilding;
//    private boolean isDestroying;
    private boolean canSpawn;
    private boolean canDespawn;

    private final Vector2 nullVector = new Vector2(-1000,-1000);

    private List<Npc> npcsOnDuty;

    private Router router = new Router();

    private Job job;
    private Vector1 destination;
    private Vector2[] vvv;
    private Npc[] pumps;
    public void setJob(Clock clock){
        job = new Job();
        job.hrs = 1;
        job.task = this.getClass().getName() + ": Job Done";
        job.vvv = this.vvv;
        job.npcs = this.pumps;
        job.endless = true;
        clock.shedule(job);
    }

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
    public Spawner(Group worldGroup,
                   WorldResTexRegManager texRegManager,
                   WorldResAnimManager animManager,
                   AllData allData,
                   DeadPool deadPool,
                   Collider collider,
                   CoreTileData coreTileData,
                   int tileSize,
                   int mapWidth,
                   int mapHeight) {
        this.worldGroup = worldGroup;
        this.texRegManager = texRegManager;
        this.animManager = animManager;
        this.allData = allData;
        this.deadPool = deadPool;
        this.collider = collider;
        // position helpers , actual to tile[i][j] and tile corner
        posManager = new WorldPositionManager(tileSize);
        // active entities goes onDuty
        npcsOnDuty = new ArrayList<Npc>();
    }
    private Npc spawn(EntityAnimation entityAnim){
        if (true){
            if (canSpawn){
                // worldGroup.swapActor()
                // todo make ghost always on foreground
                // todo add x y, id >> convert entity
                Gdx.app.log("Factory", "Spawning NPC at "+x+"/"+y+" with EID:"+entityAnim.name());
                Gdx.app.debug("Debug: Factory", "build() method *START* ------------"+
                        " ------------ ------------ ------------ ------------ ------------");
                Gdx.app.debug("Debug: Factory", "arg entityID="+entityAnim.getID());
                Gdx.app.debug("Debug: Factory", "arg entity="+entityAnim);
                Gdx.app.debug("Debug: Factory", "x/y="+x+"/"+y);
                Gdx.app.debug("Debug: Factory", "OnDuty.size()="+npcsOnDuty.size());
                Gdx.app.debug("Debug: Factory", "");
                // tear off DeadPool , and store in ... from ...onDuty
                //todo BOOOO its not a pumpkin
                Npc pumpkin = deadPool.createNpc(entityAnim);
                npcsOnDuty.add(pumpkin);
                pumpkin.setMyIndex(npcsOnDuty.size()-1);
                // Object property init
                pumpkin.set01EntityTex(EntityTex.Temp);
                pumpkin.set02EntityAnim(entityAnim);
                pumpkin.set09Data(allData.getSomethingDataByID(pumpkin.getEntityID()));
                pumpkin.set11TexReg(texRegManager);
                pumpkin.set12Anim(animManager);
                pumpkin.set21Bounds();
                pumpkin.set22Borders();
                pumpkin.set22Borders(0,0); //todo add border delta to json data
//                pumpkin.set22Borders(50,2);
                pumpkin.set23Range();
                pumpkin.set31World(worldGroup);
                pumpkin.setVisible(true);
                pumpkin.setPosition(posManager.getPosX(), posManager.getPosY());
                pumpkin.setName("Tavern: "+entityAnim.name());
                router.add(pumpkin);
                // inform coreTileData about data update
//                coreTileData.buildingHere(posManager.getTileX(), posManager.getTileY());
                // add collider
//                if (tavern.getData().isCollider)
//                    collider.add(tavern.getBorder());

                return pumpkin;
            }
        }
        return null;
    }
    private void despawn(int indexID){
        if (true){
            if (canDespawn){
                Gdx.app.log("Spawner", "De spawning Npc at "+x+"/"+y);
                Gdx.app.debug("Debug: Spawner", "destroy() method *START* ------------"+
                        " ------------ ------------ ------------ ------------ ------------");
                Gdx.app.debug("Debug: Spawner", "arg indexID="+indexID);
                Gdx.app.debug("Debug: Spawner", "x/y="+x+"/"+y);
                Gdx.app.debug("Debug: Spawner", "OnDuty.size()="+npcsOnDuty.size());
                // remove collider if it has one
//                if (npcsOnDuty.get(indexID).getData().isCollider)
//                    collider.del(npcsOnDuty.get(indexID).getBorder());
                // tear off ... from ...onDuty , and store in DeadPool (which wipes internal state)
                router.remove(npcsOnDuty.get(indexID));
                deadPool.buryNpc(npcsOnDuty.remove(indexID));
                // iterate threw ...onDuty , to fix shifted ID's
                for (int i = indexID; i < npcsOnDuty.size() ; i++) { // -1 ?
                    Gdx.app.debug("Debug: Spawner", "for i="+i);
                    Gdx.app.debug("Debug: Spawner", "for onDutySize="+npcsOnDuty.size());
                    Gdx.app.debug("Debug: Spawner", "duty ID [BUGGY]="+npcsOnDuty.get(i).getMyIndex());
                    // shifting ID to right in Actor's container via getMyIndex & getMyIndex
                    npcsOnDuty.get(i).setMyIndex(npcsOnDuty.get(i).getMyIndex()-1);
                    Gdx.app.debug("Debug: Spawner", "duty ID [FIXED]="+npcsOnDuty.get(i).getMyIndex());
                }
                Gdx.app.debug("Debug: Spawner", "Tile ="+posManager.getTileX()+" / "+ posManager.getTileY());
                // inform coreTileData about data update
//                coreTileData.destroyingThis(posManager.getTileX(), posManager.getTileY());
                Gdx.app.debug("Debug: Spawner", "destroy() method  *END*  ------------"+
                        " ------------ ------------ ------------ ------------ ------------");
            }
        }


    }
    public Npc spawnOnEvent(EntityAnimation entity, Vector2 pos){
        this.canSpawn = true;
        posManager.update(pos);
        x = posManager.getPosX();
        y = posManager.getPosY();
        Npc rv = this.spawn(entity);
        this.canSpawn = false;
        return rv;
    }
    public void despawnOnEvent(int indexID, Vector2 pos){
        this.canDespawn = true;
        posManager.update(pos);
        x = posManager.getPosX();
        y = posManager.getPosY();
        this.despawn(indexID);
        this.canDespawn = false;
    }


    public void set33Destination(int x, int y){
        destination = new Vector1(x/32,y/32);

    }
    public void set34Path(MyPathFinder pathFinder,Npc temp){
        pathFinder.calculate(temp, new Vector1(2,10)); //todo this>>npc
        Vector2[] vvv = pathFinder.getPath5();
        this.vvv = vvv;
        pumps = new Npc[30];
        for (int i = 0; i < pumps.length; i++) {
            pumps[i] = new Npc();
            pumps[i].set01EntityTex(EntityTex.Temp);
            pumps[i].set02EntityAnim(EntityAnimation.PUMPKIN);
            pumps[i].set11TexReg(texRegManager);
            pumps[i].set12Anim(animManager);
            pumps[i].set21Bounds();
            pumps[i].set22Borders(0,0);
            pumps[i].set23Range();
            pumps[i].set31World(worldGroup);
            pumps[i].set32Position(2*32,2*32);
        }

    }
}
