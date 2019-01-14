package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Vector1;

class Node {
//    public Vector1 pos;
//    public boolean root;
//    public boolean full;
//    public boolean hasChild;
//    public Node child;

    public int data;
    Node childA;
    Node childB;

    Node(int data) {
        this.data = data;
        childA = null;
        childB = null;
    }

}
