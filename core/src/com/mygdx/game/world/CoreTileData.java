package com.mygdx.game.world;

public class CoreTileData {
    private boolean[][] isOccupied;
    private boolean[][] isDestructible;
//    private boolean[][] isColidable;
    private boolean buffer;
    private int w;
    private int h;
    public CoreTileData(int w, int h){
        this.w = w;
        this.h = h;
        isOccupied = new boolean[this.w][this.h];
        isDestructible = new boolean[this.w][this.h];
    }
    public boolean canBuildHere(int x, int y){
        if (x>=0 && y>=0 && x<w && y<h){
            buffer = !isOccupied[x][y];
        }else
            buffer = false;
        return buffer;
    }
    public void buildingHere(int x, int y){
        if (x>=0 && y>=0 && x<=w && x<=h){
            isOccupied[x][y] = true;
            isDestructible[x][y] = true;
        }
    }
    public boolean canDestroyThis(int x, int y){
        if (x>=0 && y>=0 && x<=w && x<=h){
            buffer = isDestructible[x][y];
        }else
            buffer = false;
        return buffer;
    }
    public void destroyingThis(int x, int y){
        if (x>=0 && y>=0 && x<=w && x<=h){
            isOccupied[x][y] = false;
            isDestructible[x][y]= false;
        }
    }




}
