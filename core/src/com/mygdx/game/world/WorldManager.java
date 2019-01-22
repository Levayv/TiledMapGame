package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Collider;
import com.mygdx.game.Grid;
import com.mygdx.game.Vector1;
import com.mygdx.game.ants.something.a.AllData;
import com.mygdx.game.ants.something.animated.event.npc.a.Npc;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.ants.something.animated.event.a.Door;
import com.mygdx.game.enums.entity.EntityAnimation;
import com.mygdx.game.enums.entity.EntityTex;
import com.mygdx.game.enums.entity.jsonDataLoaderForEntities;

import java.util.Random;

public class WorldManager {
    // WorldManager's knowledge about grid
    private boolean debugging = false; // todo fix debugging boolean in all classes
    private int tileSize;
    private TiledMap map;
    private  int mapWidth;
    private  int mapHeight;
    private Grid grid;
    private WorldResTexRegManager texRegManager;
    private WorldResAnimManager animManager;
    public WorldPositionManager posManager;
    private Collider collider;
    private AllData allData;
    public Group world;
    public Clock clock;
    public Npc[] pumpkins;

    //    public Something tree1;
//    public Something tree2;
//    public Something tree3;
//    public Something tower1;
//    public Something tower2;
//    public Something tower3;
//    public Something lever;
//    public Npc pumpkin1;
    public Door door1;
//    public AnimatedSomething boomE;


    public Door[]                 doorss;
    public Npc[] slime1;
    public Npc[] slime2;
    public Npc[] slime3;
    public int mobCount = 5;

    //tiled map loader // todo tiled map optimise
    private int aLayersCount;   // all layers
    private int tLayersCount;   // tile layers
    private int oLayersCount;   // object layers
    private TiledMapTileLayer[] tLayers;
    private MapLayer[] oLayers;
    private boolean[][] isRoad;
    private int[][] tileID;
    private int[] objID;
    private int[] objX;
    private int[] objY;
    private Rectangle[] objRect;
    private int[] objCountPerLayer;

    public Factory factory;
    private void getMapData(){
        aLayersCount = map.getLayers().getCount(); // get both /sum of both T&O layers
        for (int i = 0; i < aLayersCount; i++) {   // get individual T&O layers count
            if (map.getLayers().get(i).getName().contains("Tile Layer")){
                tLayersCount++; // increment T layers count
            }
            if (map.getLayers().get(i).getName().contains("Object Layer")){
                oLayersCount++; // increment O layers count
            }
        }
        if (tLayersCount > 0 && oLayersCount > 0) {  // at least one of T&O layers must be present
            tLayers = new TiledMapTileLayer[tLayersCount]; // separate T layers to array
            oLayers = new MapLayer[oLayersCount]; // separate O layers to array
            for (int i = 0; i < tLayersCount; i++) { // separate T layers to array
                tLayers[i] = (TiledMapTileLayer) map.getLayers().get(i);
            }
            for (int i = tLayersCount; i < aLayersCount; i++) { // separate O layers to array
                oLayers[i - tLayersCount] = map.getLayers().get(i);
            }
            //todo add more tile properties
            // init tileID and other properties based on map size
            tileID = new int[mapWidth][mapHeight];
            isRoad = new boolean[mapWidth][mapHeight];
            for (int i = 0; i < mapWidth; i++) { // todo fix for multi layer tiles
                for (int j = 0; j < mapHeight; j++) {
                    //populate entity id from tmx, [0][0] is left bottom corner
                    tileID[i][j] = tLayers[0].getCell(i,j).getTile().getProperties().get("entity", int.class);
                    isRoad[i][j] = tLayers[0].getCell(i,j).getTile().getProperties().get("road", boolean.class);
                    //todo add func to read all properties
                }
            }
            objCountPerLayer = new int[1]; // todo fix for multi layer map objects
            objCountPerLayer[0] = oLayers[0].getObjects().getCount();
            objID = new int[objCountPerLayer[0]];
            objX = new int[objCountPerLayer[0]];
            objY = new int[objCountPerLayer[0]];
            objRect = new Rectangle[objCountPerLayer[0]];
            for (int i = 0; i < objCountPerLayer[0]; i++) { // get ID X Y from O layer objects
                objID[i] = oLayers[0].getObjects().get(i).getProperties()
                        .get("entity", int.class);
                objX[i] = (int) ((TextureMapObject) oLayers[0].getObjects().get(i)).getX();
                objY[i] = (int) ((TextureMapObject) oLayers[0].getObjects().get(i)).getX();
            }

            if (debugging){
                System.out.println(this.getClass() + ": mapWidth    = "+ mapWidth       );
                System.out.println(this.getClass() + ": mapHeight   = "+ mapHeight      );
                System.out.println(this.getClass() + ": tileSize    = "+ tileSize       +"px");
                System.out.println(this.getClass() + ": tLayersCount = "+ tLayersCount +"/"+ tLayers.length);
                System.out.println(this.getClass() + ": oLayersCount = "+ oLayersCount +"/"+ oLayers.length);
                System.out.println(this.getClass() + ": tileID[0][0]    = "+ tileID[0][0]       );
                System.out.println(this.getClass() + ": tileSize    = "+ tileSize       +"px");
                for (int i = 0; i < objCountPerLayer[0]; i++) {
                    System.out.println(this.getClass() + ": objID["+i+"] = "+ objID[i]);
                    System.out.println(this.getClass() + ": objX["+i+"] = "+ objX[i]);
                    System.out.println(this.getClass() + ": objY["+i+"] = "+ objY[i]);
                }
            }
        }
    }
    public WorldManager(Stage stage,
                        WorldResTexRegManager buffer1,
                        WorldResAnimManager buffer2,
                        TextureRegion texRegLever,
                        Collider buffer3
    ){
        world = new Group();
        stage.addActor(world);

        // load map , get props
        map = new TmxMapLoader().load("root/maps/32/level0.tmx"); //todo fix file
        MapProperties prop = map.getProperties();
        mapWidth = prop.get("width", Integer.class);
        mapHeight = prop.get("height", Integer.class);
        tileSize = prop.get("tilewidth", Integer.class);
        // Texture Handler, helper classes
        texRegManager = buffer1;
        animManager   = buffer2;
        posManager = new WorldPositionManager(tileSize);
        collider = buffer3;
        // Grid init
        grid = new Grid(mapWidth , mapHeight);

        //read tmx
        getMapData();

        //populate world, player then trees rocks //todo some kind of order , move player init here
//        System.out.println(stage.getViewport().getScreenWidth()+" "+
//      stage.getViewport().getScreenHeight());

        // temp grid manipulations


        // Read Game Object Data from File //todo also texture and animation
        jsonDataLoaderForEntities jsonLoader = new jsonDataLoaderForEntities();
        allData = jsonLoader.getAllData();

        // Factory init
        factory = new Factory(world, texRegManager, animManager, allData, collider,
                tileSize,mapWidth,mapHeight);
        // Factory build
        Vector2 pos = new Vector2();
        factory.builder.buildOnEvent(EntityTex.Tree,  pos.set( 9*tileSize , 3*tileSize ));
        factory.builder.buildOnEvent(EntityTex.Stone, pos.set( 3*tileSize , 6*tileSize ));
        factory.builder.buildOnEvent(EntityTex.Ore,   pos.set( 6*tileSize , 6*tileSize ));
        factory.builder.buildOnEvent(EntityTex.Temp,  pos.set(12*tileSize , 9*tileSize ));
//        factory.spawner.spawnOnEvent(EntityAnimation.EXPLOSION,  pos.set(2*tileSize , 2*tileSize ));
//        Npc t1 = factory.spawner.spawnOnEvent(EntityAnimation.TOWER,  pos.set(18 *tileSize,0 *tileSize));
//        Npc t2 = factory.spawner.spawnOnEvent(EntityAnimation.TOWER,  pos.set(18 *tileSize,5 *tileSize));
//        Npc t3 = factory.spawner.spawnOnEvent(EntityAnimation.TOWER,  pos.set(18 *tileSize,10 *tileSize));
//        Npc pumpkin1 = factory.spawner.spawnOnEvent(EntityAnimation.PUMPKIN,  pos.set(3*tileSize,10*tileSize));
//        Npc pumpkin2 = factory.spawner.spawnOnEvent(EntityAnimation.PUMPKIN,  pos.set(3*tileSize,10*tileSize));
//        Npc pumpkin3 = factory.spawner.spawnOnEvent(EntityAnimation.PUMPKIN,  pos.set(3*tileSize,10*tileSize));
//        pumpkin1.set22Borders(0,0);

        MyPathFinder pathFinder;
        pathFinder = new MyPathFinder(mapWidth,mapHeight,posManager,isRoad);
//        pathFinder.calculate(pumpkin1, new Vector1(10,8));

        int pp = 20;
        Random rrr = new Random();
        Npc[] towers = new Npc[pp/10];
        pumpkins = new Npc[pp];
        for (int i = 0; i < pp / 10; i++) {
            towers[i] = factory.spawner.spawnOnEvent(EntityAnimation.TOWER,
                    pos.set((rrr.nextInt(30)+1) *tileSize,(rrr.nextInt(30)+1) *tileSize));
        }
        for (int i = 0; i < pp; i++) {
            pumpkins[i] = factory.spawner.spawnOnEvent(EntityAnimation.PUMPKIN,
                    pos.set((rrr.nextInt(30)+1) *tileSize,(rrr.nextInt(30)+1) *tileSize));
            if (pathFinder.calculate(pumpkins[i], new Vector1((rrr.nextInt(30)+1), (rrr.nextInt(30)+1))))
                pumpkins[i].moveToPosition(pathFinder.getPath5());

        }
//        System.out.println("!!!"+factory.builder.);
//
//
//
//        pumpkin1.set32Position(3 * tileSize, 10 * tileSize);
//        if (pathFinder.calculate(pumpkin1, new Vector1(1, 10)))
//            pumpkin1.moveToPosition(pathFinder.getPath5());
//
//        pumpkin2.set32Position(4 * tileSize, 10 * tileSize);
//        if (pathFinder.calculate(pumpkin2, new Vector1(14, 7)))
//            pumpkin2.moveToPosition(pathFinder.getPath5());
//
//        pumpkin3.set32Position(5 * tileSize, 10 * tileSize);
//        if (pathFinder.calculate(pumpkin3, new Vector1(8, 13)))
//            pumpkin3.moveToPosition(pathFinder.getPath5());
//
//        t1.set23Range(20);
//        t2.set23Range(40);
//        t3.set23Range(60);







        // Clock init
        clock = new Clock(factory);
        //todo how to manipulate objects ?
        //todo 1. add NPC class to factory builder
        //todo 2. add Spawner
        //todo 3. integrate FSM
        //todo 4. integrate listeners ()
        //todo 5.

//        pumpkin1 = new Npc();
//        pumpkin1.setJob(clock);
//        pumpkin1.set01EntityTex(EntityTex.Temp);
//        pumpkin1.set02EntityAnim(EntityAnimation.PUMPKIN);
//        pumpkin1.set11TexReg(texRegManager);
//        pumpkin1.set12Anim(animManager);
//        pumpkin1.set21Bounds();
////        pumpkin1.set22Borders(50,2);
//        pumpkin1.set22Borders(0,0);
//        pumpkin1.set23Range();
//        pumpkin1.set31World(world);
//        pumpkin1.set32Position(4*32,4*32);

//        pumpkin1.getRange().radius += 100;
//        MyPathFinder pathFinder;
//        pathFinder = new MyPathFinder(mapWidth,mapHeight,posManager,isRoad);
//        pumpkin1.set32Position(3*tileSize,10*tileSize);
//        pathFinder.calculate(pumpkin1, new Vector1(10,8));
//        pathFinder.calculate(pumpkin1, new Vector1(3,4));

//        Spawner spawner = new Spawner(texRegManager,animManager,world);
//        spawner.set01EntityTex(EntityTex.Spawner);
//        spawner.set11TexReg(texRegManager);
//        spawner.set21Bounds();
//        spawner.set22Borders();
//        spawner.set23Range();
//        spawner.set31World(world);
//        spawner.set32Position(2*tileSize,2*tileSize);
//        spawner.set33Destination(2*tileSize,10*tileSize);
//        spawner.set34Path(pathFinder,pumpkin1);
//        spawner.setJob(clock);

//        factory.collider.add(pumpkin1.getBorder());



//        System.out.println("!!! pumpkin1="+pumpkin1.getEntityName());

//        Vector2[] vvv = pathFinder.getPath5();

//        Vector2[] v5 = pathFinder.getPath5();
//        System.out.println("!!! aaa "+pumpkin1.texReg.getRegionHeight());
//        System.out.println("!!! aaa "+pumpkin1.texReg.getRegionWidth());
//        System.out.println("!!! aaa "+pumpkin1.getBorderW());
//        System.out.println("!!! aaa "+pumpkin1.getBorderH());

//        if (isRoad[0][0]) System.out.println("!!! 1/3");
//        if (isRoad[1][0]) System.out.println("!!! 2/3");
//        if (isRoad[2][0]) System.out.println("!!! 3/3");


//        Npc[] pumpkins = new Npc[30];
//        for (int i = 0; i < pumpkins.length; i++) {
//            pumpkins[i] = new Npc();
//            pumpkins[i].set01EntityTex(EntityTex.Temp);
//            pumpkins[i].set02EntityAnim(EntityAnimation.PUMPKIN);
//            pumpkins[i].set11TexReg(texRegManager);
//            pumpkins[i].set12Anim(animManager);
//            pumpkins[i].set21Bounds();
//            pumpkins[i].set22Borders();
//            pumpkins[i].set23Range();
//            pumpkins[i].set31World(world);
//            pumpkins[i].set32Position(500,300);
//            pumpkins[i].moveToPosition(200,300);
//        }
//        Spawner spawner = new Spawner(Entity.Temp );
//        spawner.set11TexReg(texRegManager);
//        spawner.set31World(world);
//        spawner.set22Borders();
//        spawner.setPosition(objX[0],objY[0]);
//        spawner.create(factory,pos.set(12*tileSize , 3*tileSize ));

//        System.out.println("!!!");
//        System.out.println("!!!");
//        System.out.println("!!!");
//        //test
//        Vector1 v1 = new Vector1(1,1);
//        Vector1 v2 = new Vector1();
//        Vector1 v3 = new Vector1();
//        v1.x++;
//        v3 = v1.newOne();
//        v1.x++;
//        v3.x--;
//        System.out.println("v1="+ v1.x + "/" + v1.y);
//        System.out.println("v2="+ v2.x + "/" + v2.y);
//        System.out.println("v3="+ v3.x + "/" + v3.y);

        door1  = new Door();
        // doors and slimes
        door1.set01EntityTex(EntityTex.Temp);
        door1.set02EntityAnim(EntityAnimation.DOOR_OPEN);
//        door1.tempCHANGEANIM(animManager.getAnimationByID(EntityAnimation.DOOR_OPEN));
//        door1  = new Door(Entity.Temp,texRegManager,world, "pumpkin" , 8);
//        boomE = new AnimatedSomething(Entity.Temp,EntityAnimation.EXPLOSION); // ,"Explosion" , 12
        slime1 = new Npc[mobCount];
        slime2 = new Npc[mobCount];
        slime3 = new Npc[mobCount];
        doorss = new Door[mobCount];
//        tower1 = new Something();
//        tower2 = new Something();
//        tower3 = new Something();
//        tower1.set01EntityTex(EntityTex.Tower);
//        tower2.set01EntityTex(EntityTex.Tower);
//        tower3.set01EntityTex(EntityTex.Tower);
//        tower1.set11TexReg(texRegManager);
//        tower2.set11TexReg(texRegManager);
//        tower3.set11TexReg(texRegManager);
//        tower1.set21Bounds();
//        tower2.set21Bounds();
//        tower3.set21Bounds();

        door1.set11TexReg(texRegManager);
        door1.set12Anim(animManager);
        door1.set21Bounds();
//        boomE.set11TexReg(texRegManager);

//        tower1.set31World(world);
//        tower2.set31World(world);
//        tower3.set31World(world);
        door1.set31World(world);
//        boomE.set31World(world);

        door1.set22Borders();
//        boomE.set22Borders();
//        tower1.set22Borders();
//        tower2.set22Borders();
//        tower3.set22Borders();
//        factory.collider.add(tower1.getBorder());
//        factory.collider.add(tower1.getBorder());
//        factory.collider.add(tower1.getBorder());
//        door1.tempINT = 12;
//        door1.tempString = "Explosion";
//        door2.tempINT = 12;
//        door2.tempString = "Explosion";
//        boomE.set12Anim(animManager);

        for (int i = 0; i < mobCount; i++) {
            slime1[i] = new Npc();
            slime2[i] = new Npc();
            slime3[i] = new Npc();
            doorss[i] = new Door();
            slime1[i].set01EntityTex(EntityTex.Temp);
            slime2[i].set01EntityTex(EntityTex.Temp);
            slime3[i].set01EntityTex(EntityTex.Temp);
            doorss[i].set01EntityTex(EntityTex.Temp);
            slime1[i].set02EntityAnim(EntityAnimation.SLIME_1);
            slime2[i].set02EntityAnim(EntityAnimation.SLIME_2);
            slime3[i].set02EntityAnim(EntityAnimation.SLIME_3);
            doorss[i].set02EntityAnim(EntityAnimation.DOOR_OPEN);


            slime1[i].set11TexReg(texRegManager);
            slime2[i].set11TexReg(texRegManager);
            slime3[i].set11TexReg(texRegManager);
            doorss[i].set11TexReg(texRegManager);
            slime1[i].set12Anim(animManager);
            slime2[i].set12Anim(animManager);
            slime3[i].set12Anim(animManager);
            doorss[i].set12Anim(animManager);
            slime1[i].set21Bounds();
            slime2[i].set21Bounds();
            slime3[i].set21Bounds();
            doorss[i].set21Bounds();
            slime1[i].set31World(world);
            slime2[i].set31World(world);
            slime3[i].set31World(world);
            doorss[i].set31World(world);
            slime1[i].set22Borders();
            slime2[i].set22Borders();
            slime3[i].set22Borders();
            doorss[i].set22Borders();
            slime1[i].set32Position(i*tileSize,1*tileSize);
            slime2[i].set32Position(i*tileSize,6*tileSize);
            slime3[i].set32Position(i*tileSize,9*tileSize);
            doorss[i].set32Position(i*tileSize,12*tileSize);

//            world.addActor(slime1[i]);
//            world.addActor(slime2[i]);
//            world.addActor(slime3[i]);
        }

        door1.set32Position(12*tileSize,12*tileSize);
//        boomE.setPosition(15*tileSize,12*tileSize);
//        tower1.set32Position(18 *tileSize,0 *tileSize);
//        tower2.set32Position(18 *tileSize,5 *tileSize);
//        tower3.set32Position(18 *tileSize,10 *tileSize);

//        boomE.setLoop(false);



    }

    public TiledMap getMap(){
        return map;
    } // for Renderer

    //todo move build method to Builder class

//    public boolean isBuilding;
//    public void build(int id,float x, float y){
//        System.out.println("id="+id);
//        Something tavern = new Something(Entity.Temp);
//        tavern.set11TexReg(texRegManager);
//        tavern.set31World(world);
//        tavern.set22Borders();
//        tavern.setPosition(x,y);
//
//        factory.stopBuilding();
//    }

    //        chunks = new Chunk[chunkSize][chunkSize];
//        for (int i = 0; i < chunkSize; i++) {
//            for (int j = 0; j < chunkSize; j++) {
//                chunks[i][j] = new Chunk();
//                chunks[i][j].setRootTexReg(new TextureRegion(chunkTex));
//                chunks[i][j].setRootWidth(chunkRes);
//                chunks[i][j].setRootHeight(chunkRes);
////                chunks[i][j].texReg.setRegionWidth(chunkRes);
////                chunks[i][j].texReg.setRegionHeight(chunkRes);
//                chunks[i][j].setPosition(i*chunkRes,j*chunkRes);
//            }
//        }
//        for (int i = 0; i < chunkSize; i++) {
//            for (int j = 0; j < chunkSize; j++) {
//                stage.addActor(chunks[i][j]);
//            }
//        }

//        String tempS;
//        tempS = map.getProperties().get("custom-property", String.class);
//        System.out.println(tempS);




//        MapLayer layer1 = map.getLayers().get(1);
//        MapLayer layer2 = map.getLayers().get(2);
//        MapLayer layer3 = map.getLayers().get(3);
//        MapObjects objects = layer2.getObjects();
//        MapObject object = objects.get(0);
//        object.setColor(Color.RED);
//        TiledMapTileSets tileSets = map.getTileSets();

//        String name = object.getName();
//        float opacity = object.getOpacity();
//        boolean isVisible = object.isVisible();
//        Color color = object.getColor();

}
