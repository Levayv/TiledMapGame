package com.mygdx.game.world;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.ants.something.a.AllData;
import com.mygdx.game.enums.events.BasicEvents;

public class Factory implements Telegraph {
    public DeadPool deadPool;
    public Builder builder;
    public Spawner spawner;
    private CoreTileData coreTileData;
    private WorldResTexRegManager texRegManager;
    private WorldResAnimManager animManager;
    private Group worldGroup;
    private WorldPositionManager posManager;
    private AllData allData;
    private Collider collider;
    private final Vector2 nullVector = new Vector2(-1000,-1000);
    // for debug only
    private int x;
    private int y;
    // build and destroy phases are mutually exclusive
    Factory (Group worldGroup,
             WorldResTexRegManager texRegManager,
             WorldResAnimManager animManager,
             AllData allData,
             Collider collider,
             int tileSize,
             int mapWidth,
             int mapHeight) {
        this.worldGroup = worldGroup;
        this.texRegManager = texRegManager;
        this.animManager = animManager;
        this.allData = allData;
        this.collider = collider;
        // position helpers , actual to tile[i][j] and tile corner
        posManager = new WorldPositionManager(tileSize);
        // tiled map data: isOccupied, isDestructible etc.
        coreTileData = new CoreTileData(mapWidth, mapHeight);
        // destroyed entities goes to DeadPool
        deadPool = new DeadPool();
        // init builder and spawner
        builder = new Builder(
                worldGroup,
                texRegManager,
                animManager,
                allData,
                deadPool,
                this.collider,
                coreTileData,
                tileSize,
                mapWidth,
                mapHeight
        );
        spawner = new Spawner(
                worldGroup,
                texRegManager,
                animManager,
                allData,
                deadPool,
                this.collider,
                coreTileData,
                tileSize,
                mapWidth,
                mapHeight
        );
    }
    @Override
    public boolean handleMessage(Telegram msg) {
        System.out.println("!!! "+ "Factory new event:"+BasicEvents.values()[msg.message]);
        return false;
    }
}
