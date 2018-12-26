package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class GameScreen implements Screen , GestureDetector.GestureListener {
    final MyGdxGame game;

    private boolean debuging = false;

//    private Actor actor;
//    private Stage stage;


    private Sound dropSound;
    private Music rainMusic;
    //    private OrthographicCamera camera;
    //    private Rectangle bucket;
    private Array<Rectangle> raindrops;
    private long lastDropTime;
    private int dropsGathered;

    private int world_width= 1600;
    private int world_height = 960;
    //    private int world_width  = 1536;
//    private int world_height = 1536;
    private int screen_width = 800;
    private int screen_height = 480;
    private int object_width = 64;
    private int object_height = 64;

//    private Viewport viewport;

    private float rotationSpeed = 0.5f;
//    private int moveSpeed = 200;

    //    private Sprite mapSprite;
//    private Sprite[][] chunks;
    private Chunk[][] chunks;
    private int chunkSize = 3;
    //    private Texture dropImage;
//    private Texture bucketImage;
    private Sprite dropImage;
    //    private Sprite bucketImage;
    private Texture treeSprite;
    TextureAtlas atlas;


//    Tree tree1;
//    Tree tree2;
//    Tree tree3;

    Sprite[] playerSprites = new Sprite[4];
    Collider colCheck;
    public Texture chunkTex;

    Stage stage;

    Player player;
    Tree tree1;
    Something tree2;
    Something tree3;

    // Save Load
    private FileHandle saveLoadFile;
    private SaveLoadData saveLoadData = new SaveLoadData();;
    private Json saveLoadJson = new Json();;

    private float minDeltaDebug = 999999; //temp
    private float maxDeltaDebug = 1; //temp

    public GameScreen(final MyGdxGame game) {
        this.game = game;
        saveLoadJson.setOutputType(JsonWriter.OutputType.json);
        //        boolean isLocAvailable = Gdx.files.isLocalStorageAvailable();
//        if (!isLocAvailable) System.out.println("no space !!!");
//        String locRoot = Gdx.files.getLocalStoragePath();
//        System.out.println(locRoot);

        // loading images with atlas
        atlas = new TextureAtlas(Gdx.files.internal("packed/assets.atlas"));
        TextureRegion texRegPlayer = atlas.createSprite("bucket");
        TextureRegion texRegTree = atlas.createSprite("droplet");
        dropImage = atlas.createSprite("droplet");
        chunks = new Chunk[chunkSize][chunkSize];
        chunkTex = new Texture(Gdx.files.internal("dirt512.png"));
        int chunkRes = 512;
        for (int i = 0; i < chunkSize; i++) {
            for (int j = 0; j < chunkSize; j++) {
                chunks[i][j] = new Chunk();
                chunks[i][j].setRootTexReg(new TextureRegion(chunkTex));
                chunks[i][j].setRootWidth(chunkRes);
                chunks[i][j].setRootHeight(chunkRes);
//                chunks[i][j].texReg.setRegionWidth(chunkRes);
//                chunks[i][j].texReg.setRegionHeight(chunkRes);
                chunks[i][j].setPosition(i*chunkRes,j*chunkRes);
            }
        }
        // load sound & music
        dropSound = Gdx.audio.newSound(Gdx.files.internal("droplet.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);

        //stage!
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, screen_width/2, screen_height/2);
        ScreenViewport viewport1 = new ScreenViewport(camera);
        stage = new Stage(viewport1, game.batch);
        Gdx.input.setInputProcessor(stage);


        //actors!
        tree1 = new Tree(texRegTree);
        tree2 = new Something(texRegTree);
        tree3 = new Something(texRegTree);
        tree1.setBorders();
        tree2.setBorders();
        tree3.setBorders();
        tree1.setPosition(300,100);
        tree2.setPosition(100,200);
        tree3.setPosition(200,100);
        tree1.setName("tree 1");
        tree2.setName("tree 2");
        tree3.setName("tree 3");

//        tree1.border.x = 100;
//        tree1.border.y = 100;
//        tree2.border.x = 100;
//        tree2.border.y = 200;
//        tree3.border.x = 200;
//        tree3.border.y = 100;

        player = new Player(texRegPlayer);
        player.setBorders();
        player.setPosition(screen_width / 2 - object_width / 2,
                screen_height/ 2 - object_height / 2);
//        player = new Player(
//                screen_width / 2 - object_width / 2,
//                screen_height/ 2 - object_height / 2,
//                object_width,
//                object_height
//        );


        //! move to chunk loader
        for (int i = 0; i < chunkSize; i++) {
            for (int j = 0; j < chunkSize; j++) {
                stage.addActor(chunks[i][j]);
            }
        }

//        stage.addActor(tree1);
//        stage.addActor(tree2);
//        stage.addActor(tree3);
        stage.addActor(player);

        chunks[0][0].addActor(tree1);        chunks[0][0].addActor(tree1);
        chunks[0][0].addActor(tree2);
        chunks[0][0].addActor(tree3);


        colCheck = new Collider(3);
        colCheck.add(player.getBorder());
        colCheck.add(tree1. getBorder());
        colCheck.add(tree2. getBorder());
        colCheck.add(tree3. getBorder());

        //animation
        animationCreate();
    }
    void animationCreate(){
        player.setAnimations();
    }


    @Override
    public void render(float delta) {
        shittyControls(delta);
        shittyMechanics(delta);
        shittyRenderer(delta);
    }

    void shittyMechanics(float delta){
    }
    void shittyRenderer(float delta){
        // clear the screen with a dark blue color.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        // tell the camera to update its matrices.
//        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.


        // begin a new batch and draw ------------------------------------


//        game.batch.setProjectionMatrix(camera.combined);

        if (debuging){
            game.batch.begin();
            float currCustomDelta = ((float)Math.round(delta*1000000))/1000;
            if (currCustomDelta < minDeltaDebug){
                minDeltaDebug = currCustomDelta;
            }
            if (currCustomDelta > maxDeltaDebug){
                maxDeltaDebug = currCustomDelta;
            }

            game.font.draw(game.batch, "FPS: "+ Gdx.graphics.getFramesPerSecond()+
                            " Min/Max = "+ minDeltaDebug +"/"+ maxDeltaDebug +" - "+currCustomDelta,
                    stage.getCamera().position.x-screen_width/2,
                    stage.getCamera().position.y+screen_height/2-0);
            game.font.draw(game.batch, "Player Coordinates: "
                            + (int) player.getX() + ":" + (int) player.getY() ,
                    stage.getCamera().position.x-screen_width/2, stage.getCamera().position.y+screen_height/2-20);
            game.font.draw(game.batch, "Camera Coordinates: "
                            + (int) stage.getCamera().position.x + ":" + (int) stage.getCamera().position.y ,
                    stage.getCamera().position.x-screen_width/2, stage.getCamera().position.y+screen_height/2-40);
//            animationRender();
            game.batch.end();

            ShapeRenderer shape = new ShapeRenderer();
            shape.setProjectionMatrix(stage.getCamera().combined);
            shape.begin(ShapeRenderer.ShapeType.Line);
            shape.setColor(Color.RED);
            shape.rect(player.getBorderX(),
                       player.getBorderY(),
                       player.getBorderW(),
                       player.getBorderH());
            shape.rect( tree1.getBorderX(),
                        tree1.getBorderY(),
                        tree1.getBorderW(),
                        tree1.getBorderH());
            shape.rect( tree2.getBorderX(),
                        tree2.getBorderY(),
                        tree2.getBorderW(),
                        tree2.getBorderH());
            shape.rect( tree3.getBorderX(),
                        tree3.getBorderY(),
                        tree3.getBorderW(),
                        tree3.getBorderH());
            shape.end();
        } // debugging if end


        
//
//        game.batch.draw(tree1.sprite, tree1.border.x, tree1.border.y, tree1.border.width, tree1.border.height);
//        game.batch.draw(tree2.sprite, tree2.border.x, tree2.border.y, tree2.border.width, tree2.border.height);
//        game.batch.draw(tree3.sprite, tree3.border.x, tree3.border.y, tree3.border.width, tree3.border.height);
//        game.batch.draw(player.sprite, player.border.x, player.border.y, player.border.width, player.border.height);
//
//
//


    }
    void shittyControls(float delta){
        float buffer;
        // process user input ------------------------------------------
        if (Gdx.input.justTouched()) {
            Vector2 screenPos = new Vector2();
            screenPos.set(Gdx.input.getX(), Gdx.input.getY());
            Vector2 stagePos = stage.screenToStageCoordinates(screenPos);
            Actor hitActor = stage.hit(stagePos.x,stagePos.y,false);
            if (hitActor!=null){
                System.out.println("Hitted: "+hitActor.getName());
                hitActor.setDebug(true);
            }


        }

        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
            player.speedHigh();
            if (Gdx.input.isKeyPressed(Keys.ALT_LEFT))
                player.speedLow();
        }else{
            player.speedMed();
        }
        if (colCheck.leftLockFinal)
            player.moveSpeedL = 200;
        if (colCheck.rightLockFinal)
            player.moveSpeedR = 200;
        if (colCheck.upLockFinal)
            player.moveSpeedU = 200;
        if (colCheck.downLockFinal)
            player.moveSpeedD = 200;

//        System.out.println("tree X" + " = " + player.getCollider().getX());
//        System.out.println("tree Y" + " = " + player.getCollider().getY());
//        System.out.println("tree W" + " = " + player.getCollider().getW());
//        System.out.println("tree H" + " = " + player.getCollider().getH());

        // Moving player and/or camera
//        System.out.println(player.x + " - "+player.y);
//        System.out.println(colCheck.col[0].getX()+" - "+colCheck.col[0].getY());



        if (Gdx.input.isKeyPressed(Keys.ANY_KEY)){
            colCheck.calc(player); // Check colider
        }

        if (Gdx.input.isKeyPressed(Keys.A)) {
            player.setDirX(DirConst.LEFT);
            player.go();

        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            player.setDirX(DirConst.RIGHT);
            player.go();
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            player.setDirY(DirConst.UP);
            player.go();
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            player.setDirY(DirConst.DOWN);
            player.go();
        }
        // Camera moves
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            stage.getCamera().translate(-6, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            stage.getCamera().translate(6, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            stage.getCamera().translate(0, -6, 0);
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            stage.getCamera().translate(0, 6, 0);
        }
        if (Gdx.input.isKeyPressed(Keys.E)) {
            ((OrthographicCamera)stage.getCamera()).zoom += 0.24;
        }
        if (Gdx.input.isKeyPressed(Keys.Q)) {
            ((OrthographicCamera)stage.getCamera()).zoom -= 1*delta;
        }

        // make sure the bucket stays within the screen bounds
//        if (bucket.x < 0)
//            bucket.x = 0;
//        if (bucket.x > 800 - 64)
//            bucket.x = 800 - 64;
//        if (bucket.y < 0)
//            bucket.y = 0;
//        if (bucket.y > 480 - 64)
//            bucket.y = 480 - 64;

        // Border collision
//        if (player.border.x < -world_width/2)
//            player.border.x = -world_width/2;
//        if (player.border.x > +world_width/2 - player.border.width)
//            player.border.x = +world_width/2 - player.border.width;
//        if (player.border.y < -world_height/2)
//            player.border.y = -world_height/2;
//        if (player.border.y > +world_height/2 - player.border.height)
//            player.border.y = +world_height/2 - player.border.height;

//         check if we need to create a new raindrop
//        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
//            spawnRaindrop();

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
//        Iterator<Rectangle> iter = raindrops.iterator();
//        while (iter.hasNext()) {
//            Rectangle raindrop = iter.next();
//            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
//            if (raindrop.y + 64 < 0)
//                iter.remove();
//            if (raindrop.overlaps(player.border)) {
//                dropsGathered++;
//                dropSound.play();
//                iter.remove();
//            }
//        }

        //exit
        if (Gdx.input.isKeyJustPressed(Keys.F5)) {
            saveGame();
        }
        if (Gdx.input.isKeyJustPressed(Keys.F9)) {
            loadGame();
        }
        if (Gdx.input.isKeyJustPressed(Keys.F1)) {
            randomize();

        }
        if (Gdx.input.isKeyJustPressed(Keys.F2)) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
        if (Gdx.input.isKeyJustPressed(Keys.F4)) {
            debuging = !debuging;
            if (debuging){
                Array<Actor> actors = stage.getActors();
                System.out.println("Debug: Enabling borders for ["+actors.size+"] entities");
                for (int i = 0; i < actors.size; i++) {
                    actors.items[i].setDebug(true);
                }
            }else{
                Array<Actor> actors = stage.getActors();
                System.out.println("Debug: Disabling borders for ["+actors.size+"] entities");
                for (int i = 0; i < actors.size; i++) {
                    actors.items[i].setDebug(false);
                }
                minDeltaDebug = 999999;
                maxDeltaDebug = 1;
            }
        }

        // framerate to console
//        if (debuging) System.out.println(Gdx.graphics.getFramesPerSecond());
    }

    private void randomize() {
        Random r = new Random();

        tree1.setX(r.nextInt(100)     );
        tree1.setY(r.nextInt(100)     );
        tree2.setX(r.nextInt(200)+100 );
        tree2.setY(r.nextInt(200)+100 );
        tree3.setX(r.nextInt(300)+200 );
        tree3.setY(r.nextInt(100)     );
    }

    private void saveGame() {
        // parse world object to data
        saveLoadData.getChunksToSave(chunks[0][0]);
        // find file to save
        saveLoadFile = Gdx.files.local("saves/save1.txt");
        // save json to file, after parsing data to json
        saveLoadFile.writeString(saveLoadJson.prettyPrint(saveLoadData),false);
        // notify save success
        System.out.println("Save Successful");
    }

    private static class SaveLoadData{
        private int testInt;
        private float testFloat;
        private String testString;
        void getChunksToSave(Chunk chunk){
            this.testInt = chunk.testInt         ;
            this.testFloat = chunk.testfloat       ;
            this.testString = chunk.testString      ;
        }
        void setChunksToLoad(Chunk chunk){
            chunk.testInt = this.testInt    ;
            chunk.testfloat = this.testFloat;
            chunk.testString = this.testString ;
        }
    }
    private void loadGame() {
        // find file to load
        saveLoadFile = Gdx.files.local("saves/save1.txt");
        // load file to json , then json to data
        saveLoadData = saveLoadJson.fromJson(SaveLoadData.class, saveLoadFile);
        // parse data to world object
        saveLoadData.setChunksToLoad(chunks[0][0]);
        // notify load success
        System.out.println("Load Successful");
    }

    @Override
    public void resize(int width, int height) {
        screen_width = width;
        screen_height = height;
        stage.getViewport().update(width, height);
//        camera.viewportWidth = 30f;
//        camera.viewportHeight = 30f * height/width;
//        camera.update();
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown

        //rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        rainMusic.dispose();
        dropSound.dispose();

        dropImage.getTexture().dispose();
        player.texReg.getTexture().dispose();
        tree1.texReg.getTexture().dispose();
        tree2.texReg.getTexture().dispose();
        tree3.texReg.getTexture().dispose();
        // todo dispose chunk images

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

}

//stage.act( Math.minDeltaDebug( Gdx.graphics.getDeltaTime(), 1/30 ) );