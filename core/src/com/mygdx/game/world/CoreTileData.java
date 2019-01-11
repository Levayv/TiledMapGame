package com.mygdx.game.world;

class CoreTileData {
    public boolean[][] isOccupied;
    private boolean buffer;
    private int w;
    private int h;
    public CoreTileData(int w, int h){
        this.w = w;
        this.h = h;
        isOccupied = new boolean[this.w][this.h];
    }
    public boolean canBuildHere(int x, int y){
        if (x>=0 && y>=0 && x<=w && x<=h){
            buffer = !isOccupied[x][y];
        }else
            buffer = false;
        return buffer;
    }
    public void buildingHere(int x, int y){
        if (x>=0 && y>=0 && x<=w && x<=h){
            isOccupied[x][y] = true;
        }
    }
}
