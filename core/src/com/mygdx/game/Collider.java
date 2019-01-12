package com.mygdx.game;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.ants.something.animated.event.pc.a.Player;

import java.util.ArrayList;
import java.util.List;

public class Collider {
    boolean upLockFinal;
    boolean downLockFinal;
    boolean leftLockFinal;
    boolean rightLockFinal;
//    private int idOfNextAddedElement;
//    private int max;
//    private Rectangle[] rectangles;
    private List<Rectangle> rectXXX;
    private Rectangle intersection;

    Collider(int firstChunkEntitiesCount) {
        populate(firstChunkEntitiesCount);
    }

    void populate(int chunkEntitiesCount) { // public for further integrations
//        idOfNextAddedElement = 0;
//        max = chunkEntitiesCount;
        rectXXX = new ArrayList<Rectangle>();
//        rectangles = new Rectangle[max + 1];
        intersection = new Rectangle();

    }

    public void add(Rectangle newRect) {
//        System.out.println(newRect.getX());
//        System.out.println(newRect.getY());
//        System.out.println(newRect.getWidth());
//        System.out.println(newRect.getHeight());
        rectXXX.add(newRect);
//        rectangles[idOfNextAddedElement] = newRect;
//        idOfNextAddedElement++;
    }
    public void del(Rectangle newRect){
        rectXXX.remove(newRect);
    }

    void calc(Player player) {

        boolean upLock;
        boolean downLock;
        boolean leftLock;
        boolean rightLock;
        leftLock  = leftLockFinal  = false; // unlock all dir moves
        rightLock = rightLockFinal = false; // unlock all dir moves
        upLock    = upLockFinal    = false; // unlock all dir moves
        downLock  = downLockFinal  = false; // unlock all dir moves

        if (true){
            for (int i = 1; i < rectXXX.size(); i++) {
                if (rectXXX.get(0).overlaps(rectXXX.get(i))) {
                    intersection = new Rectangle();
                    Intersector.intersectRectangles(rectXXX.get(0), rectXXX.get(i), intersection);
//System.out.println("tree X" + i + " = " + rectangles[i].getX());
//System.out.println("tree Y" + i + " = " + rectangles[i].getY());
//System.out.println("tree W" + i + " = " + rectangles[i].getWidth());
//System.out.println("tree H" + i + " = " + rectangles[i].getHeight());
//System.out.println("player X" + 7 + " = " + rectangles[0].getX());
//System.out.println("player Y" + 7 + " = " + rectangles[0].getY());
//System.out.println("player W" + 7 + " = " + rectangles[0].getWidth());
//System.out.println("player H" + 7 + " = " + rectangles[0].getHeight());
//System.out.println("inter" + 7 + " = " + intersection.getX());
//System.out.println("inter" + 7 + " = " + intersection.getY());
//System.out.println("inter" + 7 + " = " + intersection.getWidth());
//System.out.println("inter" + 7 + " = " + intersection.getHeight());
//System.out.println("overlaps"+rectangles[i].x+"-"+rectangles[i].y);
                    if (intersection.x > rectXXX.get(0).x) {
                        rightLock = true;
                    }
                    if (intersection.y > rectXXX.get(0).y) {
                        upLock = true;
                    }
                    if (intersection.x + intersection.width < rectXXX.get(0).x + rectXXX.get(0).width) {
                        leftLock = true;
                    }
                    if (intersection.y + intersection.height < rectXXX.get(0).y + rectXXX.get(0).height) {
                        downLock = true;

                    }
                    if (intersection.width < 1 || intersection.height < 1)
                        leftLock = rightLock = upLock = downLock = false;

                    if (intersection.width > intersection.height) {
                        leftLock = false;
                        rightLock = false;
                    } else {
                        upLock = false;
                        downLock = false;
                    }
                    // correct over border collision cases & limit speed
                    if (leftLock){
                        if (intersection.height >= 1){
                            player.setX(player.getX()+intersection.width-1);
                            player.moveSpeedL = 50;
                        }
                    }
                    if (rightLock){
                        if (intersection.height >= 1){
                            player.setX(player.getX()-intersection.width+1);
                            player.moveSpeedR = 50;
                        }
                    }
                    if (upLock){
                        if (intersection.height >= 1){
                            player.setY(player.getY()-intersection.height+1);
                            player.moveSpeedU = 50;
                        }
                    }
                    if (downLock){
                        if (intersection.height >= 1){
                            player.setY(player.getY()+intersection.height-1);
                            player.moveSpeedD = 50;
                        }
                    }
                    leftLockFinal  = leftLock  || leftLockFinal  ;
                    rightLockFinal = rightLock || rightLockFinal ;
                    upLockFinal    = upLock    || upLockFinal    ;
                    downLockFinal  = downLock  || downLockFinal  ;



                }
            }
        }

        player.setLeftLock(leftLockFinal   ) ;
        player.setRightLock(rightLockFinal  ) ;
        player.setUpLock(upLockFinal     ) ;
        player.setDownLock(downLockFinal   ) ;
    }
}
