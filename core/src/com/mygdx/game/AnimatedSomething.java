package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedSomething extends Something{
    TextureRegion[] animFrames;
    public AnimatedSomething(TextureRegion texReg) {
        super(texReg);
        Texture walkSheet = new Texture(Gdx.files.internal("animation/male_sprite_model.png"));
    }
}
