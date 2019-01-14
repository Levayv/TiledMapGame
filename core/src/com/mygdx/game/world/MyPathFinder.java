package com.mygdx.game.world;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Vector1;

import java.util.ArrayList;
import java.util.List;

class MyPathFinder {
    private int width;
    private int height;
    private boolean[][] road;
    private boolean[][] visited;
    private Vector2[] path5;
    private Vector1 pos;
    private boolean go;
    MyPathFinder(int width, int height, boolean[][] road){
        this.width = width;
        this.height = height;
        this.road = road;
        pos = new Vector1();
        visited = new boolean[width][height];
    }
    public Vector1 getNext(Vector1 pos){ //todo 2 or 3 directional, switch ?
        visited[pos.x][pos.y] = true;
        int dir = 0;
        if (road[pos.x+1][pos.y] && !visited[pos.x+1][pos.y]){ //right
            dir += 1;
        }
        if (road[pos.x-1][pos.y] && !visited[pos.x-1][pos.y]){ //left
            dir += 3;
        }
        if (road[pos.x][pos.y+1] && !visited[pos.x][pos.y+1]){ //up
            dir += 5;
        }
        if (road[pos.x][pos.y-1] && !visited[pos.x][pos.y-1]){ //down
            dir += 7;
        }
//        System.out.println("!!!"+pos.x+"/"+pos.y+"="+dir);

        if (dir % 2 != 0){
            switch (dir){
                case 1: pos.x++;
                    break;
                case 3: pos.x--;
                    break;
                case 5: pos.y++;
                    break;
                case 7: pos.y--;
                    break;
                    default:
                        System.out.println("!!! + switch multi "+dir);
                        break;

            }
        }else {
            if (dir == 0)
                go = false;
            else
                System.out.println("!!! + if else multi "+dir);
        }
        return new Vector1(pos);
    }
    public void calc(int x, int y){
        // check x<0 x>width etc...
        pos.x = x;
        pos.y = y;
//        Vector1[] stack1 = new Vector1[100]; // change list
        List<Vector1> stack1 = new ArrayList<Vector1>();


        int dog = 0;
        go = true;
        while (go){
            stack1.add(getNext(pos));

            dog++;
            if (dog>1000){
                go = false;
                System.out.println("!!! watchdog");
            }
        }
//        for (int i = 0; i < stack1.size(); i++) {
//            System.out.println("!!! stack"+i+"="+stack1.get(i).x+"/"+stack1.get(i).x);
//        }











        path5 = new Vector2[1];
        path5[0] = new Vector2();
        path5[0].set(1,1);
    }
    public Vector2[] getPath5(){
        return path5;
    }
    public boolean[][] getVisited(){
        return visited;
    }

}
