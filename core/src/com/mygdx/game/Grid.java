package com.mygdx.game;

public class Grid {
    private boolean[][] have;
    private int[][] id;
    private int width;
    private int height;
    private int i;
    private int j;
    public Grid (int width , int height){
        this.width = width;
        this.height = height;

        have = new boolean[width][height];
        id = new int[width][height];


    }
//    private void cycle(){
//        for (i = 0; i < width; i++) {
//            for (j = 0; j < height; j++) {
//                have[i][j] = false;
//                id[i][j] = 0;
//            }
//        }
//    }

//    public void addObject(Something something, int x, int y) {
//        have[x][y] = true;
//        id[x][y] = something.entity.getID();
//    }
}
