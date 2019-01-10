package com.mygdx.game;

public class CollisionCheckerOLD {
    boolean up;
    boolean down;
    boolean left;
    boolean right;
    boolean temp;

    private int count;
    private int max;
     ColliderOLD[] col;

    CollisionCheckerOLD(int firstChunkEntitiesCount){
        populate(firstChunkEntitiesCount);
    }
    void populate(int chunkEntitiesCount){
        count = 0;
        max = chunkEntitiesCount;
        col = new ColliderOLD[max];
    }
    void add(ColliderOLD newColliderOLD){
        col[count] = newColliderOLD;
        count++;
    }
    void calc(ColliderOLD playerCol){
        boolean xBlock = false;
        boolean yBlock = false;
        for (int i = 0; i < max; i++) {
//            System.out.println("tree X" + i + " = " + col[0].getX());
//            System.out.println("tree Y" + i + " = " + col[0].getY());
//            System.out.println("tree W" + i + " = " + col[0].getW());
//            System.out.println("tree H" + i + " = " + col[0].getH());
//            System.out.println((col[0].getX() +"<="+ (col[i].getX() + col[i].getW())) +"&&"+ ((col[0].getX() + col[0].getW()) +">="+ col[i].getX())) ;
//            System.out.println(col[0].getY() +"<="+ col[i].getY() + col[i].getH() +"&&"+ col[0].getY() + col[0].getH() +">="+ col[i].getY());

//            System.out.println("i"+i);
            if ((playerCol.getX() <= (col[i].getX() + col[i].getW())) && ((playerCol.getX() + playerCol.getW()) >= col[i].getX()) ){
                xBlock = true;
//                System.out.println("!!!");
            }
            if (playerCol.getY() <= col[i].getY() + col[i].getH() && playerCol.getY() + playerCol.getH() >= col[i].getY() ){
                yBlock = true;
//                System.out.println("!!!");

            }
        }

        if (xBlock && yBlock)
            temp = true;
    }



}
