package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.ants.AnimatedNPC;
import com.mygdx.game.ants.AnimatedSomething;
import com.mygdx.game.ants.Something;
import com.mygdx.game.enums.Entity;

class World {
    // World's knowledge about grid
    private int tileSize;
    private TiledMap map;
    private  int mapWidth;
    private  int mapHeight;
    private Grid grid;
    private  WorldTexRegHandle texRegHandle;

    private Pos lastPos;
    public Group worldGroup;

    Something tree1;
    Something tree2;
    Something tree3;
    Something tower1;
    Something tower2;
    Something tower3;
    Something lever;

    AnimatedSomething door1;
    AnimatedSomething door2;
    AnimatedNPC[] slime1;
    AnimatedNPC[] slime2;
    AnimatedNPC[] slime3;
    private int slimeCount = 3;

    World(Stage stage, WorldTexRegHandle buffer,TextureRegion texRegLever){
        worldGroup = new Group();
        stage.addActor(worldGroup);

        TextureRegion texRegTree;

        // Texture Handler
        texRegHandle = buffer;


        // load map , get props
        map = new TmxMapLoader().load("maps/32/level0.tmx");
        MapProperties prop = map.getProperties();
        mapWidth = prop.get("width", Integer.class);
        mapHeight = prop.get("height", Integer.class);
        tileSize = prop.get("tilewidth", Integer.class);
        // Grid init
        grid = new Grid(mapWidth , mapHeight);

        // temp grid manipulations
//        grid.addObject();


        MapLayer layer = map.getLayers().get(2); //todo fix ALL map object loading
        MapObjects objects = layer.getObjects();
        System.out.println("obj count = " +objects.getCount());
// objects.getCount() returns 2 or 3 when i change items via editor
        MapObject object = objects.get(0);
// Entity entity;  // omitted for testing
        MapProperties prop111 = object.getProperties();
        if (prop111.containsKey("type111")){
            int i = prop111.get("type111", int.class);
            System.out.println(i);
            System.out.println("hell yeah");
            // entity = Entity.GetValue(i); // omitted for testing
            // System.out.println(entity); // omitted for testing
            // the goal is to store tile object types in Entity enum
        }

        //------------------------------------------
//        System.out.println("1");
//        System.out.println("2" + texRegHandle.toString());
//        System.out.println("3" + texRegHandle.texRegsSize);
//        System.out.println("4" + texRegHandle.getTexRegByID(Entity.Tree));

        tree1  = new Something(texRegHandle.getTexRegByID(Entity.Tree));
        tree2  = new Something(texRegHandle.getTexRegByID(Entity.Stone));
        tree3  = new Something(texRegHandle.getTexRegByID(Entity.Ore));
        lever  = new Something(texRegLever);
        door1  = new AnimatedSomething(texRegLever, "door1" , 18);
        door2  = new AnimatedSomething(texRegLever, "Explosion" , 12);
        slime1 = new AnimatedNPC[slimeCount];
        slime2 = new AnimatedNPC[slimeCount];
        slime3 = new AnimatedNPC[slimeCount];
        tower1 = new Something(texRegHandle.getTexRegByID(Entity.Tower));
        tower2 = new Something(texRegHandle.getTexRegByID(Entity.Tower));
        tower3 = new Something(texRegHandle.getTexRegByID(Entity.Tower));



        tree1  .entity = Entity.Tree;
        tree2  .entity = Entity.Stone;
        tree3  .entity = Entity.Ore;
        lever  .entity = Entity.None;
        door1  .entity = Entity.None;
        door2  .entity = Entity.None;
        tower1  .entity = Entity.Tower;
        tower2  .entity = Entity.Tower;
        tower3  .entity = Entity.Tower;


        worldGroup.addActor(tree1);
        worldGroup.addActor(tree2);
        worldGroup.addActor(tree3);
        worldGroup.addActor(lever);
        worldGroup.addActor(door1);
        worldGroup.addActor(door2);
        worldGroup.addActor(tower1);
        worldGroup.addActor(tower2);
        worldGroup.addActor(tower3);


        for (int i = 0; i < slimeCount; i++) {
            slime1[i] = new AnimatedNPC(texRegLever, "slime-blue" , 4);
            slime2[i] = new AnimatedNPC(texRegLever, "slime-green" , 4);
            slime3[i] = new AnimatedNPC(texRegLever, "slime-orange" , 4);
            slime1[i].setBorders();
            slime2[i].setBorders();
            slime3[i].setBorders();
            slime1[i].setPosition(i*tileSize,1*tileSize);
            slime2[i].setPosition(i*tileSize,6*tileSize);
            slime3[i].setPosition(i*tileSize,9*tileSize);
            worldGroup.addActor(slime1[i]);
            worldGroup.addActor(slime2[i]);
            worldGroup.addActor(slime3[i]);
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
        door2.setPosition(15*tileSize,15*tileSize);

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
        }

    private void setPosition(Actor actor , Pos pos){
        actor.setPosition(pos.x*tileSize , pos.y*tileSize);
    }
    TiledMap getMap(){
        return map;
    }


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
