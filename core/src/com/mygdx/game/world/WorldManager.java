package com.mygdx.game.world;

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
import com.mygdx.game.Collider;
import com.mygdx.game.Grid;
import com.mygdx.game.Pos;
import com.mygdx.game.Spawner;
import com.mygdx.game.ants.something.animated.event.npc.a.NonPlayableCharacter;
import com.mygdx.game.ants.something.animated.a.AnimatedSomething;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.ants.something.animated.event.a.Door;
import com.mygdx.game.enums.entity.Entity;
import com.mygdx.game.enums.entity.EntityAnimation;

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

    private Pos lastPos;
    public Group world;

//    public Something tree1;
//    public Something tree2;
//    public Something tree3;
    public Something tower1;
    public Something tower2;
    public Something tower3;
    public Something lever;

    public Door door1;
    public AnimatedSomething boomE;

    public Door[]                 doorss;
    public NonPlayableCharacter[] slime1;
    public NonPlayableCharacter[] slime2;
    public NonPlayableCharacter[] slime3;
    public int mobCount = 5;

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
            tileID = new int[mapWidth][mapHeight]; // init tileID based on map size
            for (int i = 0; i < mapWidth; i++) { // todo fix for multi layer tiles
                for (int j = 0; j < mapHeight; j++) {
                    //populate entity id from tmx, [0][0] is left bottom corner
                    tileID[i][j] = tLayers[0].getCell(i,j).getTile().getProperties().get("entity", int.class);
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

        TextureRegion texRegTree; //todo wtf is this ?



        // load map , get props
        map = new TmxMapLoader().load("maps/32/level0.tmx");
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
        Spawner spawner = new Spawner(Entity.Temp );
        spawner.set1TexReg(texRegManager);
        spawner.set2World(world);
//        spawner.entity = Entity.Temp;
//        world.addActor(spawner);
        spawner.setBorders();
        spawner.setPosition(objX[0],objY[0]);
//        spawner.create(world);

        // Factory init and build
        factory = new Factory(world, texRegManager, animManager,
                tileSize,mapWidth,mapHeight);
        factory.buildOnEvent(Entity.Tree,   9 *tileSize,3 *tileSize);
        factory.buildOnEvent(Entity.Stone,  3 *tileSize,6 *tileSize);
        factory.buildOnEvent(Entity.Ore,    6 *tileSize,6 *tileSize);
        //todo how to manipulate objects ?




//        tree1  = new Something(Entity.Tree  );
//        tree2  = new Something(Entity.Stone );
//        tree3  = new Something(Entity.Ore   );
        lever  = new Something(Entity.Temp  );
        door1  = new Door(Entity.Temp, EntityAnimation.DOOR_OPEN);
//        door1.tempCHANGEANIM(animManager.getAnimationByID(EntityAnimation.DOOR_OPEN));
//        door1  = new Door(Entity.Temp,texRegManager,world, "pumpkin" , 8);
        boomE = new AnimatedSomething(Entity.Temp,EntityAnimation.EXPLOSION); // ,"Explosion" , 12
        slime1 = new NonPlayableCharacter[mobCount];
        slime2 = new NonPlayableCharacter[mobCount];
        slime3 = new NonPlayableCharacter[mobCount];
        doorss = new Door[mobCount];
        tower1 = new Something(Entity.Tower);
        tower2 = new Something(Entity.Tower);
        tower3 = new Something(Entity.Tower);

//        tree1.set1TexReg(texRegManager);
//        tree2.set1TexReg(texRegManager);
//        tree3.set1TexReg(texRegManager);
        lever.set1TexReg(texRegManager);
        tower1.set1TexReg(texRegManager);
        tower2.set1TexReg(texRegManager);
        tower3.set1TexReg(texRegManager);
        door1.set1TexReg(texRegManager);
        boomE.set1TexReg(texRegManager);

//        tree1.set2World(world);
//        tree2.set2World(world);
//        tree3.set2World(world);
        lever.set2World(world);
        tower1.set2World(world);
        tower2.set2World(world);
        tower3.set2World(world);
        door1.set2World(world);
        boomE.set2World(world);

//        tree1.setBorders();
//        tree2.setBorders();
//        tree3.setBorders();
        lever.setBorders();
        door1.setBorders();
        boomE.setBorders();
        tower1.setBorders();
        tower2.setBorders();
        tower3.setBorders();

//        door1.tempINT = 12;
//        door1.tempString = "Explosion";
//        door2.tempINT = 12;
//        door2.tempString = "Explosion";
        door1.setAnim(animManager);
        boomE.setAnim(animManager);

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

        for (int i = 0; i < mobCount; i++) {
            slime1[i] = new NonPlayableCharacter(Entity.Temp,EntityAnimation.SLIME_1);
            slime2[i] = new NonPlayableCharacter(Entity.Temp,EntityAnimation.SLIME_2);
            slime3[i] = new NonPlayableCharacter(Entity.Temp,EntityAnimation.SLIME_3);
            doorss[i] = new Door(Entity.Temp,EntityAnimation.DOOR_OPEN);
            slime1[i].set1TexReg(texRegManager);
            slime2[i].set1TexReg(texRegManager);
            slime3[i].set1TexReg(texRegManager);
            doorss[i].set1TexReg(texRegManager);
            slime1[i].set2World(world);
            slime2[i].set2World(world);
            slime3[i].set2World(world);
            doorss[i].set2World(world);
            slime1[i].setBorders();
            slime2[i].setBorders();
            slime3[i].setBorders();
            doorss[i].setBorders();
            slime1[i].setPosition(i*tileSize,1*tileSize);
            slime2[i].setPosition(i*tileSize,6*tileSize);
            slime3[i].setPosition(i*tileSize,9*tileSize);
            doorss[i].setPosition(i*tileSize,12*tileSize);
            slime1[i].setAnim(animManager);
            slime2[i].setAnim(animManager);
            slime3[i].setAnim(animManager);
            doorss[i].setAnim(animManager);
//            world.addActor(slime1[i]);
//            world.addActor(slime2[i]);
//            world.addActor(slime3[i]);
        }



        // grl

//        tree1.setPosition(9 *tileSize,3 *tileSize);
//        tree2.setPosition(3 *tileSize,6 *tileSize);
//        tree3.setPosition(6 *tileSize,6 *tileSize);
        lever.setPosition(12*tileSize,9 *tileSize);
        door1.setPosition(12*tileSize,12*tileSize);
        boomE.setPosition(15*tileSize,12*tileSize);
        tower1.setPosition(18 *tileSize,0 *tileSize);
        tower2.setPosition(18 *tileSize,5 *tileSize);
        tower3.setPosition(18 *tileSize,10 *tileSize);

        boomE.setLoop(false);



        // add colliders

//        collider.add(tree1.getBorder());
//        collider.add(tree2.getBorder());
//        collider.add(tree3.getBorder());
    }

    private void setPosition(Actor actor , int x, int y){ // todo usage ?
        actor.setPosition(x*tileSize , y*tileSize);
    }



    public TiledMap getMap(){
        return map;
    } // for Renderer

    //todo move build method to Builder class

//    public boolean isBuilding;
//    public void build(int id,float x, float y){
//        System.out.println("id="+id);
//        Something tavern = new Something(Entity.Temp);
//        tavern.set1TexReg(texRegManager);
//        tavern.set2World(world);
//        tavern.setBorders();
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
