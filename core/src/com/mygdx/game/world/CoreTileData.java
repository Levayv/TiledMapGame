package com.mygdx.game.world;

class CoreTileData {
    public boolean[][] isOccupied;
    private boolean buffer;
    public CoreTileData(int w,int h){
        isOccupied = new boolean[w][h];
    }
    public boolean canBuildHere(int x, int y){
        buffer = !isOccupied[x][y];
        return buffer;
    }
    public void buildingHere(int x, int y){
        isOccupied[x][y] = true;
    }
}
