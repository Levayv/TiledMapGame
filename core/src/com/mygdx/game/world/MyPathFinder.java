package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Vector1;
import com.mygdx.game.ants.something.a.Something;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class MyPathFinder {
    private int objWidth;
    private int objHeight;
    private int objBorderXdelta;
    private int objBorderYdelta;
    private int tileSize;
    private boolean[][] road;
    private Vector2[] path5;
    private Vector1 start;
    private Vector1 destination;
    private Vector1 pos;

    private boolean reachable;
    public boolean[][] processed;
    public int[][] distance;
    private boolean go;
    private WorldPositionManager positionManager;
    MyPathFinder(int mapWidth, int mapHeight,
                 WorldPositionManager positionManager,
                 boolean[][] road
                 ){
        this.positionManager = positionManager;
        this.tileSize = positionManager.getTileSize();
        this.road = road;
        processed = new boolean[mapWidth][mapHeight];
        distance = new int[mapWidth][mapHeight];
    }
    public void getNext(List<Vector1> stack,Vector1 posC){ // posC Current
        Vector1[] posDir = new Vector1[4];
        posDir[0] = new Vector1(posC) ;
        posDir[1] = new Vector1(posC) ;
        posDir[2] = new Vector1(posC) ;
        posDir[3] = new Vector1(posC) ;
        posDir[0].x++;
        posDir[1].x--;
        posDir[2].y++;
        posDir[3].y--;

        for (int i = 0; i < posDir.length; i++) {
            if (road[posDir[i].x][posDir[i].y] && !processed[posDir[i].x][posDir[i].y]){
                distance[posDir[i].x][posDir[i].y] = distance[posC.x][posC.y]+1;
                stack.add(new Vector1(posDir[i].x,posDir[i].y));
                processed[posDir[i].x][posDir[i].y] = true;
                if (posDir[i].x == destination.x && posDir[i].y == destination.y){
                    reachable = true;
                    go = false;
                    Gdx.app.log("MyPathFinder", "Destination is Reachable");
                }
            }
        }
    }
    private Vector1 getPrev(Vector1 posC){
        System.out.println("!!! posCposCposCposC "+posC.x+posC.y);

        Vector1[] posDir = new Vector1[4];
        posDir[0] = new Vector1(posC) ;
        posDir[1] = new Vector1(posC) ;
        posDir[2] = new Vector1(posC) ;
        posDir[3] = new Vector1(posC) ;
        posDir[0].x++;
        posDir[1].x--;
        posDir[2].y++;
        posDir[3].y--;
        int minBuffer = 999999;
        int buffer = 0;
        Vector1 posBuffer = new Vector1();
        for (int i = 0; i < 4; i++) {
            System.out.println("!!! 2222222ppggg"+posC.x+"/"+posC.y);
            if (road[posDir[i].x][posDir[i].y] && processed[posDir[i].x][posDir[i].y]){
                buffer = distance[posDir[i].x][posDir[i].y];
                if (buffer<minBuffer){
                    minBuffer = buffer;
                    System.out.println("!!!+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+buffer);
                    posBuffer = posDir[i];
                }
            }
            System.out.println("!!! 2buffer"+buffer);
            System.out.println("!!! 2buffer"+minBuffer);
            System.out.println("!!! posDir["+i+"]"+posDir[i].x+"/"+posDir[i].y);
            System.out.println("!!! 2posBuffer"+posBuffer.x+"/"+posBuffer.y);
            System.out.println("!!! 2posC"+posC.x+"/"+posC.y);
            System.out.println("!!! 2posDirDISTANCE"+distance[posDir[i].x][posDir[i].y]);
            System.out.println("!!! 2posCDISTANCE"+distance[posC.x][posC.y]);


        }

        return new Vector1(posBuffer);
    }
//    public int getMin(int[] arr){
//        int buffer1 = 0;
//        int buffer2 = 0;
//        if (arr[0]<arr[1])
//            buffer1 = arr[0];
//        if (arr[2]<arr[3])
//            buffer2 = arr[2];
//        return buffer1 < buffer2 ? buffer1 : buffer2;
//    }
    public boolean calculate(Something who, Vector1 where){
        return calc(positionManager.getTileVector1((int)who.getBorderX(),(int)who.getBorderY()),
                where, who
                );
    }
    private boolean calc(Vector1 from, Vector1 to, Something who){
        this.objWidth         = (int) who.getBorderW();
        this.objHeight        = (int) who.getBorderH();
        this.objBorderXdelta  = who.borderXdelta;
        this.objBorderYdelta  = who.borderYdelta;
        reachable = false;
        //who = null; //todo research object casting

        // check x<0 x>width etc... , also from != to
        start = from;
        destination = to;

        List<Vector2> path25 = new ArrayList<Vector2>();
        List<Vector1> stack = new ArrayList<Vector1>();


        pos = new Vector1(start);
//        System.out.println("!!! !!! !!!"+pos.x+"/"+pos.y);
        distance[pos.x][pos.y] = -1; //todo research -1 or 0
        stack.add(new Vector1(pos));
        processed[pos.x][pos.y] = true;
        getNext(stack,pos);

        int watchdog;
        watchdog = 0;
        go = true;
        while (go){
            if (stack.isEmpty()){
                Gdx.app.log("MyPathFinder", "Destination UnReachable");
                go = false;
                return reachable;
            }else {
                getNext(stack, stack.remove(0));
            }
            watchdog++;
            if (watchdog>1024){
                go = false;
                Gdx.app.log("MyPathFinder", "WatchDog Bug");
            }
        }
//        pos = new Vector1(destination);
        pos.x = destination.x;
        pos.y = destination.y;

        path25.add(tileXYCorrector(pos.x,pos.y));
        path25.add(tileXYCorrector(pos.x,pos.y));

        watchdog = 0;
        go = true;
        while (go){
            System.out.println("!!! +111111111111111111111111asdasdadsdadsasd");
            System.out.println("!!! 333333 "+watchdog+"-"+pos.x+"/"+pos.y);
            System.out.println("!!! 333333 "+watchdog+"-"+destination.x+"/"+destination.y);
            System.out.println("!!! !!! !!!"+pos.x+"/"+pos.y);
            pos = getPrev(pos);
            System.out.println("!!! !!! !!!"+pos.x+"/"+pos.y);



            path25.add(tileXYCorrector(pos.x,pos.y));
            if (pos.x == start.x && pos.y == start.y){
                go = false;
                Gdx.app.debug("MyPathFinder", "Destination reconnected to Start");
                path25.add(tileXYCorrector(pos.x,pos.y));
            }
            watchdog++;
            if (watchdog>1024){
                go = false;
                Gdx.app.log("MyPathFinder", "WatchDog Bug");
            }
        }
        path5 = new Vector2[path25.size()];
        path5 = path25.toArray(path5);
        Collections.reverse(Arrays.asList(path5));
        return reachable;
    }
    public Vector2 tileXYCorrector(int x, int y){
        Vector2 r = new Vector2();
//        r.set(x*tileSize/*+tileSize/2*/,y*tileSize/*+tileSize/2*/);
//        r.set(x*tileSize+objWidth/2,y*tileSize+objHeight/2);
//        r.set(x*tileSize-objWidth/2,y*tileSize-objHeight/2);


//        r.set(x*tileSize+tileSize/2-objWidth/2,y*tileSize+tileSize/2-objHeight/2);
//        r.set(x*tileSize-objWidth/2,y*tileSize-objHeight/2);
//        r.set(x*tileSize+tileSize/2,y*tileSize+tileSize/2);
//        r.set(x*tileSize,y*tileSize);
        r.set(
                x*tileSize+tileSize/2-objBorderXdelta-objWidth /2 ,
                y*tileSize+tileSize/2-objBorderYdelta-objHeight/2 );

        System.out.println("!!! tileXYCorrector "+r.x+"/"+r.y);
        return r;
    }
    public Vector2[] getPath5(){
        return path5;
    }
    public boolean[][] getVisited(){
        return processed;
    }

}
