package com.mygdx.game.world;

import com.badlogic.gdx.math.Vector2;

public class WorldPositionManager {
    private int tileSize; // set only once
    private int tileX;
    private int tileY;
    private int posX;
    private int posY;
    private int buffer;
    private int count;
    private Vector2 bufferVector = new Vector2();

    public WorldPositionManager(int tileSize){
        this.tileSize = tileSize;
    }

    private void convertVectorToTile(Vector2 pos){
        posX = (int) pos.x;
        posY = (int) pos.y;
        if (posX>=0 && posY>=0){
            //----------//
            buffer = posX;
            count = -1;
            while(buffer>=0){
                count++;
                buffer = buffer - tileSize;
            }
            tileX = count;
            //----------//
            buffer = posY;
            count = -1;
            while(buffer>=0){
                count++;
                buffer = buffer - tileSize;
            }
            tileY = count;
            //----------//
        }else {
            tileX = -1;
            tileY = -1;
        }
    }
//    public void convertTileToVector(int x, int y, Vector2 pos){
//        x = (int) pos.x;
//        y = (int) pos.y;
//        pos.x = x * 32;
//        pos.y = y * 32;
//    }
    public void update(Vector2 pos){ //
        convertVectorToTile(pos);
        if (posX>0)
            posX = tileX*32;
        else
            posX = 0;
        if (posY>0)
            posY = tileY*32;
        else
            posY = 0;
    }
    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
    public int getTileX(){
        return tileX;
    }
    public int getTileY() {
        return tileY;
    }
    public Vector2 getPosXYviaVector(){
        bufferVector.set(posX,posY);
        return bufferVector;
    }
}
