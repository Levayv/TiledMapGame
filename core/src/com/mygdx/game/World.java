package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

class World {
    // World's knowledge about chunks
//    private int chunkSize = 3;
//    private int chunkRes = 512;
//    Chunk[][] chunks;
    Group group = new Group();
    private short tileSize = 32;
    TiledMap map;
    Something tree1;
    Something tree2;
    Something tree3;
    Something lever;
    AnimatedSomething door1;
    AnimatedSomething door2;
    AnimatedNPC[] slime1;
    AnimatedNPC[] slime2;
    AnimatedNPC[] slime3;
    private int slimeCount = 1000;


    World(Stage stage, TextureRegion texRegTree, TextureRegion texRegLever){
        stage.addActor(group);

        map = new TmxMapLoader().load("maps/32/level0.tmx");


        tree1 = new Something(texRegTree);
        tree2 = new Something(texRegTree);
        tree3 = new Something(texRegTree);
        lever = new Something(texRegLever);
        door1 = new AnimatedSomething(texRegTree, "door1" , 18);
        door2 = new AnimatedSomething(texRegTree, "Explosion" , 12);
        slime1 = new AnimatedNPC[slimeCount];
        slime2 = new AnimatedNPC[slimeCount];
        slime3 = new AnimatedNPC[slimeCount];
        for (int i = 0; i < slimeCount; i++) {
            slime1[i] = new AnimatedNPC(texRegTree, "slime-blue" , 4);
            slime2[i] = new AnimatedNPC(texRegTree, "slime-green" , 4);
            slime3[i] = new AnimatedNPC(texRegTree, "slime-orange" , 4);
            slime1[i].setBorders();
            slime2[i].setBorders();
            slime3[i].setBorders();
            slime1[i].setPosition(i*tileSize,1*tileSize);
            slime2[i].setPosition(i*tileSize,6*tileSize);
            slime3[i].setPosition(i*tileSize,9*tileSize);
            group.addActor(slime1[i]);
            group.addActor(slime2[i]);
            group.addActor(slime3[i]);
        }



        // grl
        tree1.setBorders();
        tree2.setBorders();
        tree3.setBorders();
        lever.setBorders();
        door1.setBorders();
        door2.setBorders();



        tree1.setPosition(9 *tileSize,3 *tileSize);
        tree2.setPosition(3 *tileSize,6 *tileSize);
        tree3.setPosition(6 *tileSize,6 *tileSize);
        lever.setPosition(12*tileSize,9 *tileSize);
        door1.setPosition(12*tileSize,12*tileSize);
        door2.setPosition(15*tileSize,15*tileSize);



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

//        MapLayer layer0 = map.getLayers().get(0);
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
