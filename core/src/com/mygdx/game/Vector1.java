package com.mygdx.game;

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
}
