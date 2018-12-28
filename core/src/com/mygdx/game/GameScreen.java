package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.ants.AnimatedSomething;
import com.mygdx.game.ants.Something;
import com.mygdx.game.enums.DirConst;
import com.mygdx.game.enums.Entity;


public class GameScreen implements Screen , GestureDetector.GestureListener {
    final MyGdxGame game;

    private boolean debugging = false;
    private boolean colliding = true;
    private Sound dropSound;
    private Music rainMusic;

    private int screen_width = 800;
    private int screen_height = 480;
    private int object_width = 64;
    private int object_height = 64;

//    private Chunk[][] chunks;
//    private int chunkSize = 3;


    TextureAtlas atlas;

    Collider colCheck;

    World world;
    Stage stage;
    Stage stageUI;
    Actor lastHitActor;
    Actor lastHitUIActor;
    Something lastHitSomething;

    Player player;


    TiledMapRenderer tiledMapRenderer;


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
        TextureRegion texRegDrop = atlas.createSprite("droplet");
//      // loading temp images with Texture
        TextureRegion texRegTree = new TextureRegion(new Texture(Gdx.files.internal("tree.png")));
        TextureRegion texRegStone = new TextureRegion(new Texture(Gdx.files.internal("stone.png")));
        TextureRegion texRegOre = new TextureRegion(new Texture(Gdx.files.internal("ore.png")));
        TextureRegion texRegTower = new TextureRegion(new Texture(Gdx.files.internal("tower.png")));
        TextureRegion texRegLever = new TextureRegion(new Texture(Gdx.files.internal("lever.png")));


        // load sound & music
        dropSound = Gdx.audio.newSound(Gdx.files.internal("droplet.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);


        //stage!

        OrthographicCamera camera1 = new OrthographicCamera();
        OrthographicCamera camera2 = new OrthographicCamera();
        camera1.setToOrtho(false, screen_width/2, screen_height/2);
        camera2.setToOrtho(false, screen_width/2, screen_height/2);
        ScreenViewport viewport1 = new ScreenViewport(camera1);
        ScreenViewport viewport2 = new ScreenViewport(camera2);

        stage   = new Stage(viewport1, game.batch);
        stageUI = new Stage(viewport2, game.batch);

        InputMultiplexer inputMultiplexer = new InputMultiplexer(stage,stageUI);
        Gdx.input.setInputProcessor(inputMultiplexer);

        //actors!
        player = new Player(texRegPlayer);
//        player.setBorders();
//        player.setBorders(10,10,20,20);
        player.setBorders(20,0,20,20);
        player.setPosition(screen_width / 2 - object_width / 2,
                screen_height/ 2 - object_height / 2);
        player.setName("player");
        player.entity = Entity.Player;

        // World and map init
        WorldTexRegHandle buffer = new WorldTexRegHandle(100);

        buffer.addTexReg(Entity.Tree,   texRegTree      );
        buffer.addTexReg(Entity.Stone,  texRegStone     );
        buffer.addTexReg(Entity.Ore,    texRegOre       );
        buffer.addTexReg(Entity.Tower,  texRegTower     );

        world = new World(stage , player , buffer, new TextureRegion(texRegLever));

        tiledMapRenderer = new OrthogonalTiledMapRenderer(world.getMap());

        //Colider init
        colCheck = new Collider(3);
        colCheck.add(player.getBorder());
        colCheck.add(world.tree1. getBorder());
        colCheck.add(world.tree2. getBorder());
        colCheck.add(world.tree3. getBorder());

        // UI init
        GraphicalUserInterface ui = new GraphicalUserInterface(stageUI);

        //animation
        animationCreate();

//        testing();
    }



    private void testing() {
        System.out.println("Testing LINE START");
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                world.chunks[i][j].setName("CHUNK" + i + "" +j);
//                System.out.println(
//                        world.chunks[i][j].getName() +
//                                " " +
//                                world.chunks[i][j].getZIndex()
//                );
//            }
//        }
//        System.out.println(tree1.getName() + " " + tree1.getZIndex());
//        System.out.println(tree2.getName() + " " + tree1.getZIndex());
//        System.out.println(tree3.getName() + " " + tree1.getZIndex());
//        System.out.println(player.getName() + " " + tree1.getZIndex());
        System.out.println("Testing LINE END");
    }

    void animationCreate(){
        player.setAnimations();
    }


    @Override
    public void render(float delta) {
        shittyControls(delta);
        shittyRenderer(delta);
    }

    void shittyRenderer(float delta){
        // clear the screen with a dark blue color.
//        world.group.setCullingArea(new Rectangle(
//                stage.getCamera().position.x - stage.getCamera().viewportWidth /2 ,
//                stage.getCamera().position.y - stage.getCamera().viewportHeight/2,
//                stage.getCamera().viewportWidth ,
//                stage.getCamera().viewportHeight));
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        stage.getCamera().update(); //? why
        tiledMapRenderer.setView((OrthographicCamera) stage.getCamera());
        tiledMapRenderer.render();

        stage.act(delta);
        stage.draw();
        stageUI.act(delta);
        stageUI.draw();

        if (debugging){
//        game.batch.setProjectionMatrix(camera.combined); //? why ?
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
            shape.setColor(Color.RED); // Border Colider RED
            shape.rect(player.getBorderX(),
                       player.getBorderY(),
                       player.getBorderW(),
                       player.getBorderH());
            shape.rect( world.tree1.getBorderX(),
                        world.tree1.getBorderY(),
                        world.tree1.getBorderW(),
                        world.tree1.getBorderH());
            shape.rect( world.tree2.getBorderX(),
                        world.tree2.getBorderY(),
                        world.tree2.getBorderW(),
                        world.tree2.getBorderH());
            shape.rect( world.tree3.getBorderX(),
                        world.tree3.getBorderY(),
                        world.tree3.getBorderW(),
                        world.tree3.getBorderH());
//            shape.rect( world.tree1.texReg.getRegionX(),
//                        world.tree1.texReg.getRegionY(),
//                        world.tree1.texReg.getRegionWidth(),
//                        world.tree1.texReg.getRegionHeight());
//            shape.rect( world.tree2.texReg.getRegionX(),
//                        world.tree2.texReg.getRegionY(),
//                        world.tree2.texReg.getRegionWidth(),
//                        world.tree2.texReg.getRegionHeight());
//            shape.rect( world.tree3.texReg.getRegionX(),
//                        world.tree3.texReg.getRegionY(),
//                        world.tree3.texReg.getRegionWidth(),
//                        world.tree3.texReg.getRegionHeight());
            shape.setColor(Color.YELLOW);    // Range Yellow
            shape.circle(player.getRange().x,
                         player.getRange().y,
                         player.getRange().radius);
            shape.end();
        } // debugging if end
    }
    void shittyControls(float delta){
        float buffer;
        // process user input ------------------------------------------
        if (Gdx.input.justTouched()) {
            Vector2 screenPos = new Vector2();
            screenPos.set(Gdx.input.getX(), Gdx.input.getY());
            Vector2 pos = stage.screenToStageCoordinates(screenPos);

            lastHitActor   = stage  .hit(pos.x,pos.y,false);
            lastHitUIActor = stageUI.hit(pos.x,pos.y,true);

            if (lastHitUIActor != null){
                System.out.println("UI HITS");
            }else {
                if (lastHitActor!=null){
                    if (lastHitActor.getClass() == Something.class){
                        lastHitSomething = (Something) lastHitActor ;
                        System.out.print("Hit: ActorName=");
                        System.out.print(lastHitSomething.getName());
                        System.out.print(" EntityID=");
                        System.out.print(lastHitSomething.entity.GetID());
                        System.out.print(" EntityName=");
                        System.out.print(lastHitSomething.entity);
                        System.out.println();
                        // lastHitSomething.setDebug(true);
                    }else {
                        System.out.println("Hit: Unknown Entity");
                    }
                }else {
                    System.out.println("Hit: Void");
                }
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
            if (colliding)
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
        // Camera moves 4 dir
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
        // Camera zoom temp
        if (Gdx.input.isKeyPressed(Keys.E)) {
            if (Gdx.input.isKeyPressed(Keys.ALT_LEFT)){
                ((OrthographicCamera)stage.getCamera()).zoom += 1*delta;
            }
        }
        if (Gdx.input.isKeyPressed(Keys.Q)) {
            if (Gdx.input.isKeyPressed(Keys.ALT_LEFT)){
                ((OrthographicCamera)stage.getCamera()).zoom -= 1*delta;
            }
        }
        if (Gdx.input.isKeyPressed(Keys.TAB)) {
            // todo open close menu
        }
        if (Gdx.input.isKeyJustPressed(Keys.F)) {
            // Some action test todo remove this later
            // try toggling AnimatedSomething animation
            world.door2.setName("exp");
            world.door2.animationTime = 0;
            world.door2.startAnimCycle =  true;
            System.out.println("Start anim"); //

            float tempx =  player.getBorderX()+player.getBorderW()/2;
            float tempy =  player.getBorderY()+player.getBorderH()/2;

            tempx -= 96/2;
            tempy -= 96/2;

            world.door2.setPosition(tempx,tempy);

        }

        if (Gdx.input.isKeyJustPressed(Keys.F5)) {
            saveGame();
        }
        if (Gdx.input.isKeyJustPressed(Keys.F9)) {
            loadGame();
        }
        if (Gdx.input.isKeyJustPressed(Keys.F1)) {
            randomize(); // for colider testing only
        }
        if (Gdx.input.isKeyJustPressed(Keys.F2)) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
        if (Gdx.input.isKeyJustPressed(Keys.F3)) {
            colliding = !colliding;
        }
        if (Gdx.input.isKeyJustPressed(Keys.F4)) {
            debugging = !debugging;
            if (debugging){
                stage.setDebugAll(true);
                Array<Actor> actors = stage.getActors();
                System.out.println("Debug: Enabling borders for ["+actors.size+"] entities");
//                for (int i = 0; i < actors.size; i++) {
//                    actors.items[i].setDebug(true);
//                }
            }else{
                stage.setDebugAll(false);
                Array<Actor> actors = stage.getActors();
                System.out.println("Debug: Disabling borders for ["+actors.size+"] entities");
//                for (int i = 0; i < actors.size; i++) {
//                    actors.items[i].setDebug(false);
//                }
                minDeltaDebug = 999999;
                maxDeltaDebug = 1;
            }
        }

        // framerate to console
//        if (debugging) System.out.println(Gdx.graphics.getFramesPerSecond());
    }

    private void randomize() {
        Random r = new Random();

        world.tree1.setX(r.nextInt(100)     );
        world.tree1.setY(r.nextInt(100)     );
        world.tree2.setX(r.nextInt(200)+100 );
        world.tree2.setY(r.nextInt(200)+100 );
        world.tree3.setX(r.nextInt(300)+200 );
        world.tree3.setY(r.nextInt(100)     );
    }
    private void saveGame() {
        // parse world object to data
//        saveLoadData.getChunksToSave(world.chunks[0][0]);
        // find file to save
        saveLoadFile = Gdx.files.local("saves/save1.txt");
        // save json to file, after parsing data to json
        saveLoadFile.writeString(saveLoadJson.prettyPrint(saveLoadData),false);
        // notify save success
        System.out.println("Save Successful");
    }
    private void loadGame() {
        // find file to load
        saveLoadFile = Gdx.files.local("saves/save1.txt");
        // load file to json , then json to data
        saveLoadData = saveLoadJson.fromJson(SaveLoadData.class, saveLoadFile);
        // parse data to world object
//        saveLoadData.setChunksToLoad(world.chunks[0][0]);
        // notify load success
        System.out.println("Load Successful");
    }
    private static class SaveLoadData{
        private int testInt;
        private float testFloat;
        private String testString;
        private Chunk chunk;
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

        player.texReg.getTexture().dispose();
        world.tree1.texReg.getTexture().dispose();
        world.tree2.texReg.getTexture().dispose();
        world.tree3.texReg.getTexture().dispose();
        // todo dispose atlas
        // todo dispose temp textures
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