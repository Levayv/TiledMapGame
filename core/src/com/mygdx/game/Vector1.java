package com.mygdx.game;

// alternative to com.badlogic.gdx.math.Vector2
// simple solution without float overhead
public class Vector1 { //todo check mutation
    public int x;
    public int y;

    public Vector1() {
    }

    public Vector1(Vector1 pos) {
        this.x = pos.x;
        this.y = pos.y;
    }
    public Vector1(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Vector1 newOne(){
        return new Vector1(x,y);
    }
}
