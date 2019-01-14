package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.GameScreen;

public class MainMenuScreen implements Screen {

    final com.mygdx.game.MyGdxGame game;

    OrthographicCamera camera;

    public MainMenuScreen(final com.mygdx.game.MyGdxGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Drop", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin", 100, 100);
        game.font.draw(game.batch, "Log Level:"+Gdx.app.getLogLevel(), 100, 50);
        game.batch.end();

        if (Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F4)) {
            if (Gdx.app.getLogLevel() == 0){
                Gdx.app.setLogLevel(2);
            }else{
                if (Gdx.app.getLogLevel() == 2){
                    Gdx.app.setLogLevel(3);
                }else {
                    if (Gdx.app.getLogLevel() == 3){
                        Gdx.app.setLogLevel(0);
                    }
                }
            }

        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


    //...Rest of class omitted for succinctness.

}