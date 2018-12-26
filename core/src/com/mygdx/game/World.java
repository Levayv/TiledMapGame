package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

class World {
    // World's knowledge about chunks
    private int chunkSize = 3;
    private int chunkRes = 512;
    Chunk[][] chunks;

    // aaa
    World(Stage stage, Texture chunkTex){
        chunks = new Chunk[chunkSize][chunkSize];
        for (int i = 0; i < chunkSize; i++) {
            for (int j = 0; j < chunkSize; j++) {
                chunks[i][j] = new Chunk();
                chunks[i][j].setRootTexReg(new TextureRegion(chunkTex));
                chunks[i][j].setRootWidth(chunkRes);
                chunks[i][j].setRootHeight(chunkRes);
//                chunks[i][j].texReg.setRegionWidth(chunkRes);
//                chunks[i][j].texReg.setRegionHeight(chunkRes);
                chunks[i][j].setPosition(i*chunkRes,j*chunkRes);
            }
        }
        for (int i = 0; i < chunkSize; i++) {
            for (int j = 0; j < chunkSize; j++) {
                stage.addActor(chunks[i][j]);
            }
        }

    }
}
