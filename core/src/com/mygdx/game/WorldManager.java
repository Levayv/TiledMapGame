package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.ants.something.animated.npc.NonPlayableCharacter;
import com.mygdx.game.ants.something.animated.AnimatedSomething;
import com.mygdx.game.ants.something.Something;
import com.mygdx.game.ants.something.worldObjects.Door;
import com.mygdx.game.enums.Entity;

class WorldManager {
    // WorldManager's knowledge about grid
    private boolean debugging = false; // todo fix debugging boolean in all classes
    private int tileSize;
    private TiledMap map;
    private  int mapWidth;
    private  int mapHeight;
    private Grid grid;
    private WorldTexRegManager texRegManager;

    private Pos lastPos;
    public Group world;

    Something tree1;
    Something tree2;
    Something tree3;
    Something tower1;
    Something tower2;
    Something tower3;
    Something lever;

    Door door1;
    AnimatedSomething door2;
    NonPlayableCharacter[] slime1;
    NonPlayableCharacter[] slime2;
    NonPlayableCharacter[] slime3;
    private int slimeCount = 3;

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
        if (tLayersCount>0 && oLayersCount>0){  // at least one of T&O layers must be present
            tLayers = new TiledMapTileLayer [tLayersCount]; // separate T layers to array
            oLayers = new MapLayer          [oLayersCount]; // separate O layers to array
            for (int i = 0; i < tLayersCount; i++) { // separate T layers to array
                tLayers[i] = (TiledMapTileLayer) map.getLayers().get(i);
            }
            for (int i = tLayersCount; i < aLayersCount; i++) { // separate O layers to array
                oLayers[i-tLayersCount] = map.getLayers().get(i);
            }
            tileID = new int[mapWidth][mapHeight]; // init tileID based on map size
            for (int i = 0; i < mapWidth; i++) { // todo fix for multi layer tiles
                for (int j = 0; j < mapHeight; j++) {
                    //populate entity id from tmx, [0][0] is left bottom corner
                    tileID[i][j] = tLayers[0].getCell(i,j).getTile().getProperties().get("entity", int.class);
                    //todo add func to read all properties
                }
            }
//            System.out.println("oL COunt 0 = "+oLayers[0].getObjects().getCount());
//            System.out.println("oL COunt 1 = "+oLayers[1].getObjects().getCount());
//            System.out.println("oL COunt 2 = "+oLayers[2].getObjects().getCount());
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
    WorldManager(Stage stage, WorldTexRegManager buffer, TextureRegion texRegLever){
        world = new Group();
        stage.addActor(world);

        TextureRegion texRegTree;

        // Texture Handler
        texRegManager = buffer;


        // load map , get props
        map = new TmxMapLoader().load("maps/32/level0.tmx");
        MapProperties prop = map.getProperties();
        mapWidth = prop.get("width", Integer.class);
        mapHeight = prop.get("height", Integer.class);
        tileSize = prop.get("tilewidth", Integer.class);
        // Grid init
        grid = new Grid(mapWidth , mapHeight);

        //read tmx
        getMapData();

        // temp grid manipulations
        Spawner spawner = new Spawner(Entity.Temp , texRegManager, world);
//        spawner.entity = Entity.Temp;
//        world.addActor(spawner);
        spawner.setBorders();
        spawner.setPosition(objX[0],objY[0]);
//        spawner.create(world);



        tree1  = new Something(Entity.Tree  , texRegManager , world);
        tree2  = new Something(Entity.Stone , texRegManager , world);
        tree3  = new Something(Entity.Ore   , texRegManager , world);
        lever  = new Something(Entity.Temp  , texRegManager , world);
        door1  = new Door(Entity.Temp,texRegManager,world, "door1" , 18);
//        door1  = new Door(Entity.Temp,texRegManager,world, "pumpkin" , 8);
        door2  = new AnimatedSomething(Entity.Temp,texRegManager,world, "Explosion" , 12);
        slime1 = new NonPlayableCharacter[slimeCount];
        slime2 = new NonPlayableCharacter[slimeCount];
        slime3 = new NonPlayableCharacter[slimeCount];
        tower1 = new Something(Entity.Tower,texRegManager,world);
        tower2 = new Something(Entity.Tower,texRegManager,world);
        tower3 = new Something(Entity.Tower,texRegManager,world);


//        tree1  .entity = Entity.Tree;
//        tree2  .entity = Entity.Stone;
//        tree3  .entity = Entity.Ore;
//        lever  .entity = Entity.None;
//        door1  .entity = Entity.None;
//        door2  .entity = Entity.None;
//        tower1  .entity = Entity.Tower;
//        tower2  .entity = Entity.Tower;
//        tower3  .entity = Entity.Tower;

//        world.addActor(tree1);
//        world.addActor(tree2);
//        world.addActor(tree3);
//        world.addActor(lever);
//        world.addActor(door1);
//        world.addActor(tower1);
//        world.addActor(tower2);
//        world.addActor(tower3);
//        world.addActor(player);

//        world.addActorAfter(player,door2); //! fix

        for (int i = 0; i < slimeCount; i++) {
            slime1[i] = new NonPlayableCharacter(Entity.Temp, texRegManager,world,"slime-blue" , 4);
            slime2[i] = new NonPlayableCharacter(Entity.Temp, texRegManager,world,"slime-green" , 4);
            slime3[i] = new NonPlayableCharacter(Entity.Temp, texRegManager,world,"slime-orange" , 4);
            slime1[i].setBorders();
            slime2[i].setBorders();
            slime3[i].setBorders();
            slime1[i].setPosition(i*tileSize,1*tileSize);
            slime2[i].setPosition(i*tileSize,6*tileSize);
            slime3[i].setPosition(i*tileSize,9*tileSize);
//            world.addActor(slime1[i]);
//            world.addActor(slime2[i]);
//            world.addActor(slime3[i]);
        }



        // grl
        tree1.setBorders();
        tree2.setBorders();
        tree3.setBorders();
        lever.setBorders();
        door1.setBorders();
        door2.setBorders();
        tower1.setBorders();
        tower2.setBorders();
        tower3.setBorders();

        tree1.setPosition(9 *tileSize,3 *tileSize);
        tree2.setPosition(3 *tileSize,6 *tileSize);
        tree3.setPosition(6 *tileSize,6 *tileSize);
        lever.setPosition(12*tileSize,9 *tileSize);
        door1.setPosition(12*tileSize,12*tileSize);
        door2.setPosition(15*tileSize,12*tileSize);

        tower1.setPosition(18 *tileSize,0 *tileSize);
        tower2.setPosition(18 *tileSize,5 *tileSize);
        tower3.setPosition(18 *tileSize,10 *tileSize);


//        tree1.setName("tree 1");
//        tree2.setName("tree 2");
//        tree3.setName("tree 3");
//        lever.setName("lever1");
//        door1.setName("door1");
//        door2.setName("door2 AKA explosion");  //! wtf
//        slime1.setName("door2 AKA explosion");  //! wtf
//        slime2.setName("door2 AKA explosion");  //! wtf
//        slime3.setName("door2 AKA explosion");  //! wtf
//        stage.addActor(tree1);
//        stage.addActor(tree2);
//        stage.addActor(tree3);
//        stage.addActor(lever);
//        stage.addActor(door1);
//        stage.addActor(door2);

//        MapLayers layers = map.getLayers();
//        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("background0");
//        MapLayer layer = layers.get("background0");
//        MapLayer layer = map.getLayers().get(2);
//        TiledMapTileLayer.Cell cell = layer.getCell(0,0);
//        TiledMapTile tile = cell.getTile();
//        tile.getProperties();
//        MapProperties prop = tile.getProperties();

//        System.out.println(tile.getId());
//        tile.setId(40);
//        System.out.println(tile.getId());


//        if (prop.containsKey("typeZ")){
//             ignored , i got 2 objects both with property
//             type 1 and type 2
//            int i = prop.get("typeZ", Integer.class);
//            System.out.println(i);
//            entity = Entity.GetValue(i);
//            System.out.println(entity);
            // goal is to store tile object types in Entity enum

        door2.loopingEndless =  false ;

    }

    private void setPosition(Actor actor , Pos pos){
        actor.setPosition(pos.x*tileSize , pos.y*tileSize);
    }



    public TiledMap getMap(){
        return map;
    } // for Renderer


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
