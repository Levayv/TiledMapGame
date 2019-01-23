package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Vector1;
import com.mygdx.game.ants.something.a.Something;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyPathFinder {
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
    private boolean[][] processed;
    private int[][] distance;
    private boolean go; // loop controller
    private WorldPositionManager positionManager;

    MyPathFinder(int mapWidth, int mapHeight,
                 WorldPositionManager positionManager,
                 boolean[][] road
    ) {
        this.positionManager = positionManager;
        this.tileSize = positionManager.getTileSize();
        this.road = road;
        processed = new boolean[mapWidth][mapHeight];
        distance = new int[mapWidth][mapHeight];
    }

    private void getNext(List<Vector1> stack, Vector1 posC) { // posC Current
        Vector1[] posDir = new Vector1[4]; //todo check bounds
        posDir[0] = new Vector1(posC);
        posDir[1] = new Vector1(posC);
        posDir[2] = new Vector1(posC);
        posDir[3] = new Vector1(posC);
        posDir[0].x++;
        posDir[1].x--;
        posDir[2].y++;
        posDir[3].y--;
        for (int i = 0; i < posDir.length; i++) {
            if (road[posDir[i].x][posDir[i].y] && !processed[posDir[i].x][posDir[i].y]) {
                distance[posDir[i].x][posDir[i].y] = distance[posC.x][posC.y] + 1;
                stack.add(new Vector1(posDir[i].x, posDir[i].y));
                processed[posDir[i].x][posDir[i].y] = true;
                if (posDir[i].x == destination.x && posDir[i].y == destination.y) {
                    reachable = true;
                    go = false;
                    Gdx.app.log("MyPathFinder", "Destination is Reachable");
                }
            }
        }
    }

    private Vector1 getPrev(Vector1 posC) {
        Gdx.app.debug("MyPathFinder", "posC=" + posC.x + "/" + posC.y);
        Vector1[] posDir = new Vector1[4];
        posDir[0] = new Vector1(posC);
        posDir[1] = new Vector1(posC);
        posDir[2] = new Vector1(posC);
        posDir[3] = new Vector1(posC);
        posDir[0].x++;
        posDir[1].x--;
        posDir[2].y++;
        posDir[3].y--;
        int minBuffer = 999999;
        int buffer = 0;
        Vector1 posBuffer = new Vector1();
        for (int i = 0; i < 4; i++) {
            Gdx.app.debug("MyPathFinder", "posC=" + posC.x + "/" + posC.y);
            if (road[posDir[i].x][posDir[i].y] && processed[posDir[i].x][posDir[i].y]) {
                buffer = distance[posDir[i].x][posDir[i].y];
                if (buffer < minBuffer) {
                    minBuffer = buffer;
                    posBuffer = posDir[i];
                }
            }
            Gdx.app.debug(
                    "MyPathFinder.getPrev()",
                    "buffer=" + buffer);
            Gdx.app.debug(
                    "MyPathFinder.getPrev()",
                    "minBuffer=" + minBuffer);
            Gdx.app.debug(
                    "MyPathFinder.getPrev()",
                    "posDir[" + i + "]=" + posDir[i].x + "/" + posDir[i].y);
            Gdx.app.debug(
                    "MyPathFinder.getPrev()",
                    "posBuffer=" + posBuffer.x + "/" + posBuffer.y);
            Gdx.app.debug(
                    "MyPathFinder.getPrev()",
                    "posDirDISTANCE=" + distance[posDir[i].x][posDir[i].y]);
        }

        return new Vector1(posBuffer);
    }

    /**
     * Calculate and store path
     * using simplified Dijkstra's algorithm
     *
     * @param who   Something's border
     * @param where binary Position
     **/
    public boolean calculate(Something who, Vector1 where) {
        //nullify previous data
        reachable = false;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                processed[i][j] = false;
                distance[i][j] = 0;
            }
        }
        this.objWidth = (int) who.getBorderW();
        this.objHeight = (int) who.getBorderH();
        this.objBorderXdelta = who.borderXdelta;
        this.objBorderYdelta = who.borderYdelta;

        return calc333(positionManager.getTileVector1((int) who.getBorderX(), (int) who.getBorderY()),
                where
        );
    }

    /**
     * @param from binary Position
     * @param to   binary Position
     * @return reachable
     **/
    private boolean calc333(Vector1 from, Vector1 to) { // returns this.reachable
        // case 1: from == to
        if ((from.x == to.x) && (from.y == to.y)) {
            reachable = false;
            Gdx.app.log("MyPathFinder", "Destination is the same tile");
            return reachable;
        }
        //case 2: from != to , distance = 1;
        if ((from.y - to.y) == 1) {
            if ((from.x == to.x)) {
                reachable = true;
                Gdx.app.debug("MyPathFinder",
                        "Destination is nearest bottom cell");
            }
        }
        if ((from.y - to.y) == -1) {
            if ((from.x == to.x)) {
                reachable = true;
                Gdx.app.debug("MyPathFinder",
                        "Destination is nearest upper cell");
            }
        }
        if ((from.x - to.x) == 1) {
            if ((from.y == to.y)) {
                reachable = true;
                Gdx.app.debug("MyPathFinder",
                        "Destination is nearest left cell");
            }
        }
        if ((from.x - to.x) == -1) {
            if ((from.y == to.y)) {
                reachable = true;
                Gdx.app.debug("MyPathFinder",
                        "Destination is nearest right cell");
            }
        }
        if (reachable) {
            path5 = new Vector2[4];
            path5[0] = tileXYCorrector(from.x, from.y);
            path5[1] = tileXYCorrector(from.x, from.y);
            path5[2] = tileXYCorrector(to.x, to.y);
            path5[3] = tileXYCorrector(to.x, to.y);
            return true;
        }
        //case 3: from != to , distance = 2..999
        start = from;
        destination = to;

        List<Vector2> path25 = new ArrayList<Vector2>(); // non binary path buffer
        List<Vector1> stack = new ArrayList<Vector1>(); // one-way binary path buffer

        pos = new Vector1(start);
        distance[pos.x][pos.y] = 0;
        stack.add(new Vector1(pos));
        processed[pos.x][pos.y] = true;
        getNext(stack, pos);

        int watchdog;
        watchdog = 0;
        go = true;
        while (go) {
            if (stack.isEmpty()) {
                Gdx.app.log("MyPathFinder", "Destination is not Reachable");
                go = false;
                return reachable;
            } else {
                getNext(stack, stack.remove(0));
            }
            watchdog++;
            if (watchdog > 10000) {
                go = false;
                Gdx.app.log("MyPathFinder", "WatchDog Bug");
            }
        }
//        pos = new Vector1(destination);
        pos.x = destination.x;
        pos.y = destination.y;

        path25.add(tileXYCorrector(pos.x, pos.y));
        path25.add(tileXYCorrector(pos.x, pos.y));

        watchdog = 0;
        go = true;
        while (go) {
            Gdx.app.debug("MyPathFinder while2 step=" + watchdog,
                    "pos=" + pos.x + "/" + pos.y);
            Gdx.app.debug("MyPathFinder while2 step=" + watchdog,
                    "des=" + destination.x + "/" + destination.y);
            Gdx.app.debug("MyPathFinder while2 step=" + watchdog,
                    "des=" + destination.x + "/" + destination.y);
            pos = getPrev(pos);
            path25.add(tileXYCorrector(pos.x, pos.y));
            if (pos.x == start.x && pos.y == start.y) {
                go = false;
                Gdx.app.debug("MyPathFinder", "Destination reconnected to Start");
                path25.add(tileXYCorrector(pos.x, pos.y));
            }
            watchdog++;
            if (watchdog > 10000) {
                go = false;
                Gdx.app.log("MyPathFinder", "WatchDog Bug");
            }
        }
        path5 = new Vector2[path25.size()];
        path5 = path25.toArray(path5);
        Collections.reverse(Arrays.asList(path5));
        //debug info
        if (Gdx.app.getLogLevel() > 0) {
            Gdx.app.debug("Pathfinder", "calc() method *START* ------------" +
                    " ------------ ------------ ------------ ------------ ------------");
            String bufferString = "";
            for (int i = processed.length - 1; i >= 0; i--) {
                for (int j = 0; j < processed.length; j++) {
                    bufferString = bufferString.concat(distance[j][i] + ",");
                }
                Gdx.app.debug("MyPathFinder", bufferString);
                bufferString = "";
            }
            bufferString = "";
            for (int i = processed.length - 1; i >= 0; i--) {
                for (int j = 0; j < processed.length; j++) {
                    if (processed[j][i]) {
                        bufferString = bufferString.concat("+");
                    } else {
                        bufferString = bufferString.concat("-");
                    }
                }
                Gdx.app.debug("MyPathFinder", bufferString);
                bufferString = "";
            }
            for (int i = 0; i < path5.length; i++) {
                Gdx.app.debug("MyPathFinder",
                        "path5[" + i + "]=" + path5[i].x + "/" + path5[i].y);
            }
            Gdx.app.debug("Pathfinder", "calc() method * END * ------------" +
                    " ------------ ------------ ------------ ------------ ------------");
        }
        return reachable;
    }

    private Vector2 tileXYCorrector(int x, int y) {
        Vector2 rv = new Vector2();
        // aligning object border (NOT BOUNDS) center to tile center
        rv.set(
                x * tileSize + tileSize / 2 - objBorderXdelta - objWidth / 2,
                y * tileSize + tileSize / 2 - objBorderYdelta - objHeight / 2);
        Gdx.app.debug("MyPathFinder.tileXYCorrector", rv.x + "/" + rv.y);
        return rv;
    }

    public Vector2[] getPath5() {
        return path5;
    }


    //    public boolean[][] getVisited(){
//        return processed;
//    }

}
