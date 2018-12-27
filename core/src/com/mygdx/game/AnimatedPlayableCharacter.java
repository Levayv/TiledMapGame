package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedPlayableCharacter extends AnimatedNPC {


    public AnimatedPlayableCharacter(TextureRegion texReg, String file, int rows) {
        super(texReg, file, rows);
    }
}
