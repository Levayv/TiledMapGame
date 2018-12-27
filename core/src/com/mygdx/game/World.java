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
import com.badlogic.gdx.scenes.scene2d.Stage;

class World {
    // World's knowledge about chunks
//    private int chunkSize = 3;
//    private int chunkRes = 512;
//    Chunk[][] chunks;
    TiledMap map;
    Something tree1;
    Something tree2;
    Something tree3;
    Something lever;
    AnimatedSomething door;


    World(Stage stage, TextureRegion texRegTree, TextureRegion texRegLever){
        map = new TmxMapLoader().load("maps/32/level0.tmx");
        tree1 = new Something(texRegTree);
        tree2 = new Something(texRegTree);
        tree3 = new Something(texRegTree);
        lever = new Something(texRegLever);
        // grl
        tree1.setBorders();
        tree2.setBorders();
        tree3.setBorders();
        lever.setBorders();
        int temp = 32;
        tree1.setPosition(9*temp,3*temp);
        tree2.setPosition(3*temp,6*temp);
        tree3.setPosition(6*temp,6*temp);
        lever.setPosition(12*temp,6*temp);
        tree1.setName("tree 1");
        tree2.setName("tree 2");
        tree3.setName("tree 3");
        lever.setName("lever1");
        stage.addActor(tree1);
        stage.addActor(tree2);
        stage.addActor(tree3);
        stage.addActor(lever);



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
