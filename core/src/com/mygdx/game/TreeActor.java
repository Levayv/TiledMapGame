package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TreeActor extends Actor{
//    public int x;
//    public int y;
//    private Texture image;
////    public TreeActor(Vector2 pos){
//        x = (int)pos.x;
//        y = (int)pos.y;
//
//        image = new Texture(Gdx.files.internal("tree.png"));
//
//
//    }

//    public Texture getTexture() {
//        return image;
//    }

//    TextureRegion texReg;
    Texture tex;

    public TreeActor() {
//        texReg = new TextureRegion(new Texture(
//                Gdx.files.internal("tree.png")));
//        texReg.setRegionX(0);
//        texReg.setRegionY(0);
//        setBounds(texReg.getRegionX(), texReg.getRegionY(),
//                texReg.getRegionWidth(), texReg.getRegionWidth());
        tex = new Texture(Gdx.files.internal("tree.png"));
        setX(200);
        setY(200);
        setWidth(tex.getWidth());
        setHeight(tex.getHeight());
        if (true){
            System.out.println(Gdx.files.internal("tree.png"));
            System.out.println(Gdx.files.internal("tree X = " + getX()));
            System.out.println(Gdx.files.internal("tree Y = " + getY()));
            System.out.println(Gdx.files.internal("tree W = " + getWidth()));
            System.out.println(Gdx.files.internal("tree H = " + getHeight()));
        }

//        texReg.setSprite(tex);

    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        Color color = getColor();

        batch.draw(tex, getX(), getY(), getWidth(), getHeight());
    }
}
