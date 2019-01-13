package com.mygdx.game;

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
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.ants.something.animated.event.pc.a.Player;
import com.mygdx.game.debug.tools.PerfCounter;
import com.mygdx.game.debug.tools.ProfilerID;
import com.mygdx.game.enums.DirConst4;
import com.mygdx.game.enums.DirParser;
import com.mygdx.game.enums.entity.EntityClass;
import com.mygdx.game.enums.entity.EntityTex;
import com.mygdx.game.enums.entity.EntityAnimation;
import com.mygdx.game.hud.GraphicalUserInterface;
import com.mygdx.game.world.WorldManager;
import com.mygdx.game.world.WorldResAnimManager;
import com.mygdx.game.world.WorldResTexRegManager;

public class GameScreen implements Screen {
    final MyGdxGame game;

    private boolean debugging = false;
    private boolean profiling = false;
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

    WorldManager worldManager;
    Stage stage;
    Stage stageUI;
    Actor lastHitActor;
    Actor lastHitUIActor;
    GraphicalUserInterface ui;
    Something lastHitSomething;

    Player player;
//    MyProfiler myProfiler = new MyProfiler ("Profiler: ");
    PerfCounter profiler = new PerfCounter (ProfilerID.GLOBAL,true);

    TiledMapRenderer tiledMapRenderer;
    ShapeRenderer debugShape = new ShapeRenderer();
    // Render
    private Vector2 screenPos1 = new Vector2(); //for stage
    private Vector2 screenPos2 = new Vector2(); //for stageUI
    private Vector2 stagePos1 = new Vector2();
    private Vector2 stagePos2 = new Vector2();
    private int lastInputX;
    private int lastInputY;
    private int lastInputTileX;
    private int lastInputTileY;

    // Save Load
    private FileHandle saveLoadFile;
    private SaveLoadData saveLoadData = new SaveLoadData();
    private Json saveLoadJson = new Json();

    private float minDeltaDebug = 999999; //temp
    private float maxDeltaDebug = 1; //temp
    DirParser dirParser = new DirParser();

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
//      // loading (temp) images with Texture
        TextureRegion texRegTree    = new TextureRegion(new Texture(Gdx.files.internal("tree.png")));
        TextureRegion texRegStone   = new TextureRegion(new Texture(Gdx.files.internal("stone.png")));
        TextureRegion texRegOre     = new TextureRegion(new Texture(Gdx.files.internal("ore.png")));
        TextureRegion texRegTower   = new TextureRegion(new Texture(Gdx.files.internal("tower.png")));
        TextureRegion texRegLever   = new TextureRegion(new Texture(Gdx.files.internal("lever.png")));
        TextureRegion texRegTemp32  = new TextureRegion(new Texture(Gdx.files.internal("Temp32.png")));
        TextureRegion texRegGhost1  = new TextureRegion(new Texture(Gdx.files.internal("ghost1.png")));
        TextureRegion texRegGhost2  = new TextureRegion(new Texture(Gdx.files.internal("ghost2.png")));
//        TextureRegion texRegDoor0  = new TextureRegion(new Texture(Gdx.files.internal("door0.png")));
//        TextureRegion texRegDoor1  = new TextureRegion(new Texture(Gdx.files.internal("door1.png")));
        Texture texAnimPumpkin      = new Texture(Gdx.files.internal("animation/pumpkin.png"));
        Texture texAnimDoor2        = new Texture(Gdx.files.internal("animation/door2.png"));
        Texture texAnimExplosion    = new Texture(Gdx.files.internal("animation/Explosion.png"));
        Texture texAnimSlime1       = new Texture(Gdx.files.internal("animation/slime-blue.png"));
        Texture texAnimSlime2       = new Texture(Gdx.files.internal("animation/slime-green.png"));
        Texture texAnimSlime3       = new Texture(Gdx.files.internal("animation/slime-orange.png"));
        Texture texAnimWarning       = new Texture(Gdx.files.internal("animation/Warning.png"));
//        Texture texAnimSlime1       = new Texture(Gdx.files.internal("animation/knightRun1.png"));
        // WorldResTexRegManager init
        WorldResTexRegManager buffer1 = new WorldResTexRegManager(100);
        buffer1.addTexReg(EntityTex.Tree,texRegTree);
        buffer1.addTexReg(EntityTex.Stone,texRegStone);
        buffer1.addTexReg(EntityTex.Ore,texRegOre);
        buffer1.addTexReg(EntityTex.Tower,texRegTower);
        buffer1.addTexReg(EntityTex.Temp,texRegTemp32);
        buffer1.addTexReg(EntityTex.Player,texRegPlayer);
        buffer1.addTexReg(EntityTex.Ghost1,texRegGhost1);
        buffer1.addTexReg(EntityTex.Ghost2,texRegGhost2);
        buffer1.addTexReg(EntityTex.Ghost2,texRegGhost2);
        WorldResAnimManager buffer2 = new WorldResAnimManager(100);
//        buffer2.addAnimationFromFile(Entity.Temp , texAnimTemp1,8,1);
        buffer2.addAnimationFromFile(EntityAnimation.TEMP,texAnimWarning,44);
        buffer2.addAnimationFromFile(EntityAnimation.PUMPKIN,texAnimPumpkin,8);
        buffer2.addAnimationFromFile(EntityAnimation.DOOR_OPEN,texAnimDoor2,21);
        buffer2.addAnimationFromFile(EntityAnimation.EXPLOSION,texAnimExplosion,9);
        buffer2.addAnimationFromFile(EntityAnimation.SLIME_1,texAnimSlime1,4);
        buffer2.addAnimationFromFile(EntityAnimation.SLIME_2,texAnimSlime2,4);
        buffer2.addAnimationFromFile(EntityAnimation.SLIME_3,texAnimSlime3,4);

        // load sound & music
        dropSound = Gdx.audio.newSound(Gdx.files.internal("droplet.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);

        //temp
        colCheck = new Collider(3);


        //stage!

        OrthographicCamera camera1 = new OrthographicCamera();
        OrthographicCamera camera2 = new OrthographicCamera();
        camera1.setToOrtho(false, screen_width/2, screen_height/2);
        camera2.setToOrtho(false, screen_width/2, screen_height/2);
        ScreenViewport viewport1 = new ScreenViewport(camera1);
        ScreenViewport viewport2 = new ScreenViewport(camera2);

        stage   = new Stage(viewport1, game.batch);
        stageUI = new Stage(viewport2, game.batch);

        InputMultiplexer inputMultiplexer = new InputMultiplexer(stage , stageUI);
        Gdx.input.setInputProcessor(inputMultiplexer);

        // WorldManager init
        player = new Player();
        player.set01EntityTex(EntityTex.Player);
        player.set1TexReg(buffer1);
        player.setBorders(20,0,20,20);

        colCheck.add(player.getBorder());

        worldManager = new WorldManager(stage , buffer1, buffer2, new TextureRegion(texRegLever),colCheck);

        tiledMapRenderer = new OrthogonalTiledMapRenderer(worldManager.getMap());

        //actors!
        player.set2World(worldManager.world);
//        player.setBorders();
//        player.setBorders(10,10,20,20);
        player.setPosition(screen_width / 2 - object_width / 2,
                screen_height/ 2 - object_height / 2); //todo screen_w/h object_w/h are final ?
        player.setAnimations();

//        player.setName("player");
//        world.addActorAfter(player,door2); //! fix

//        player.entity = Entity.Player; redundant

        //Colider init

        // UI init
        ui = new GraphicalUserInterface(stageUI,worldManager);

        //animation
        animationCreate();

        testing();
    }
    private void testing() {
        System.out.println("Testing LINE START");
//        System.out.println("!"+worldManager.world.getChildren().size);

//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                worldManager.chunks[i][j].setName("CHUNK" + i + "" +j);
//                System.out.println(
//                        worldManager.chunks[i][j].getName() +
//                                " " +
//                                worldManager.chunks[i][j].getZIndex()
//                );
//            }
//        }
//        System.out.println(tree1.getName() + " " + tree1.getZIndex());
//        System.out.println(tree2.getName() + " " + tree1.getZIndex());
//        System.out.println(tree3.getName() + " " + tree1.getZIndex());
//        System.out.println(player.getName() + " " + tree1.getZIndex());

//        Obj o1 = new Obj();
//        Obj o2 = new Obj();
//        Obj o3 = new Obj();
//        o1.x = 1;
//        o2.x = 2;
//        o3.x = 3;
//        List<Obj> list = new ArrayList<Obj>();
//        list.add(o1);
//        System.out.println("o1.x="+list.get(0).x);
//        list.remove(o1);
//        System.out.println("o1.x="+list.get(0).x);
        System.out.println("Testing LINE END");
    }

    void animationCreate(){
    }


    @Override
    public void render(float delta) {
        shittyControls(delta);
        shittyMechanics(delta);
        shittyRenderer(delta);
    }
    void shittyMechanics(float delta) {
//        worldManager.door1.update();
//        for (int i = 0; i < worldManager.mobCount; i++) {
//            worldManager.doorss[i].update();
//        }

        //todo builder/destroyer integration
        lastInputX = Gdx.input.getX();
        lastInputY = Gdx.input.getX();
        screenPos1.set(Gdx.input.getX(), Gdx.input.getY());
        screenPos2.set(Gdx.input.getX(), Gdx.input.getY());
        stagePos1 =   stage.screenToStageCoordinates(screenPos1);
        stagePos2 = stageUI.screenToStageCoordinates(screenPos2);
        worldManager.factory.update(stagePos1);
        // worldManager.posManager testing
        //worldManager.posManager.convertPointerToTile(stagePos1);
        //lastInputTileX = worldManager.posManager.getTileX();
        //lastInputTileY = worldManager.posManager.getTileY();
    }
    void shittyControls(float delta){ //todo wtf i did, change all IF's to SWITCH
        if (Gdx.input.justTouched()) {
//            Vector2 screenPos1 = new Vector2(); // todo vector init each click wrong
//            Vector2 screenPos2 = new Vector2(); // todo vector init each click wrong
//            screenPos1.set(Gdx.input.getX(), Gdx.input.getY());
//            screenPos2.set(Gdx.input.getX(), Gdx.input.getY());
//            Vector2 pos1 = stage.screenToStageCoordinates(screenPos1);
//            Vector2 pos2 = stageUI.screenToStageCoordinates(screenPos2);

            lastHitActor   = stage  .hit(stagePos1.x,stagePos1.y,true);
            lastHitUIActor = stageUI.hit(stagePos2.x,stagePos2.y,true);
            if (lastHitUIActor==null){ // no hit on stageUI
                worldManager.factory.buildOnClick(EntityTex.Temp /*,stagePos1.x,stagePos1.y*/);
                if (lastHitActor!=null){ // no hit on stageUI, hit on stage
                    if (lastHitActor.getClass() == Something.class //todo check and remove IF
//                            || lastHitActor.getClass() == Player.class
//                            || lastHitActor.getClass() == Spawner.class
//                            || lastHitActor.getClass() == Something.class
                            ){
                        lastHitSomething = (Something) lastHitActor ;
                        System.out.print("Hit: ActorName=");
                        System.out.print(lastHitSomething.getName());
                        System.out.print(" EntityID=");
                        System.out.print(lastHitSomething.getEntityID());
                        System.out.print(" EntityName=");
                        System.out.print(lastHitSomething.getEntityName());
                        System.out.print(" IndexID=");
                        System.out.print(lastHitSomething.getIndexID());
                        System.out.println();
                        //todo builder/destroyer integration
                        worldManager.factory.destroyOnClick(lastHitSomething.getIndexID());
                    }else {
                        lastHitSomething = (Something) lastHitActor ;
                        System.out.print("Hit: ActorName=");
                        System.out.print(lastHitSomething.getName());
                        System.out.print(" EntityID=");
                        System.out.print(lastHitSomething.getEntityID());
                        System.out.print(" EntityName=");
                        System.out.print(lastHitSomething.getEntityName());
                        System.out.println();
                    }
                }else { // no hit on stageUI, no hit on stage
                    System.out.println("Hit: Void");
                }
            }else{
//                worldManager.factory.stopBuildingPhase();
//                worldManager.factory.stopDestroyingPhase();
            }
            lastHitActor   = null;
            lastHitUIActor = null;
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
            if (colliding){
                colCheck.calc(player); // Check colider

            }
        }

        if (Gdx.input.isKeyPressed(Keys.A)) {
            player.setDirX(DirConst4.LEFT);
            player.go();

        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            player.setDirX(DirConst4.RIGHT);
            player.go();
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            player.setDirY(DirConst4.UP);
            player.go();
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            player.setDirY(DirConst4.DOWN);
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

//            Something s1 = worldManager.tree1;
//            Something s2 = worldManager.tree1;

            // Some action test todo remove ALL OF THIS later
            // todo try toggling AnimatedSomething animation
//            worldManager.door2.setName("exp");
//            worldManager.door2.animationTime = 0;
//            worldManager.door2.startAnimCycle =  true;
//            System.out.println("Start anim"); //
//
//            float tempx =  player.getBorderX()+player.getBorderW()/2;
//            float tempy =  player.getBorderY()+player.getBorderH()/2;
//
//            tempx -= 96/2;
//            tempy -= 96/2;
//            worldManager.door2.setPosition(tempx,tempy);
//
//            DirConst8 dir8 = dirParser.to8(player.dirLast);
//            System.out.println(player.dirLast+" or "+dir8);

              //todo profiler integrate to render
//            if (profiling){
//                profiler.start();
//                for (int i = 0; i < 2000000; i++) {
//                    profiling = !profiling;
//                    profiling = !profiling;
//                }
//                profiler.stop(delta);
//                System.out.println(profiler.export(delta));
//                profiler.tick(delta);
//            }


//            points = worldManager.slime1[0].findPath(stage);
//            worldManager.door1.unhandledSignal = true;
            // todo another test to be removed [EVENTS] or [OBSERVER]
//            MessageDispatcher mDispetcher = new MessageDispatcher();
//            mDispetcher.addListener(worldManager.door1 , BasicDoorEvents.CLOSE.getID());
//            mDispetcher.dispatchMessage(BasicDoorEvents.CLOSE.getID());
//            worldManager.factory.ghost.setColor(Color.RED);
        }
        if (Gdx.input.isKeyJustPressed(Keys.G)) {
            // Some action test todo remove this later
            // try toggling AnimatedSomething animation
//            worldManager.door2.setName("exp");
//            worldManager.door2.animationTime = 0;
//            worldManager.door2.startAnimCycle =  true;
//            System.out.println("Start anim"); //
//
//            float tempx =  player.getBorderX()+player.getBorderW()/2;
//            float tempy =  player.getBorderY()+player.getBorderH()/2;
//
//            tempx -= 96/2;
//            tempy -= 96/2;
//            worldManager.door2.setPosition(tempx,tempy);



//            worldManager.slime1[0].go();


        }

        if (Gdx.input.isKeyJustPressed(Keys.F5)) {
            saveGame();
        }
        if (Gdx.input.isKeyJustPressed(Keys.F9)) {
            loadGame();
        }
        if (Gdx.input.isKeyJustPressed(Keys.F1)) {
            randomize(); //todo remove, this is for colider testing only
        }
        if (Gdx.input.isKeyJustPressed(Keys.F2)) {
            game.setScreen(new MainMenuScreen(game));
            dispose(); //todo add all textures to dispose
        }
        if (Gdx.input.isKeyJustPressed(Keys.F3)) {
            colliding = !colliding;
        }
        if (Gdx.input.isKeyJustPressed(Keys.F4)) {
            debugging = !debugging;
            if (debugging){
                stage.setDebugAll(true);
                stageUI.setDebugAll(true);
                Gdx.app.setLogLevel(3);
                System.out.println("Debug: Enabling borders for ["+stage.getActors().size+"] entities");
//                for (int i = 0; i < actors.size; i++) {
//                    actors.items[i].setDebug(true);
//                }
            }else{
                stage.setDebugAll(false);
                stageUI.setDebugAll(false);
                Gdx.app.setLogLevel(0);
                System.out.println("Debug: Disabling borders for ["+stage.getActors().size+"] entities");
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
    void shittyRenderer(float delta){
        // clear the screen with a dark blue color.
//        worldManager.group.setCullingArea(new Rectangle(
//                stage.getCamera().position.x - stage.getCamera().viewportWidth /2 ,
//                stage.getCamera().position.y - stage.getCamera().viewportHeight/2,
//                stage.getCamera().viewportWidth ,
//                stage.getCamera().viewportHeight));
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        stage.getCamera().update(); //? why
        tiledMapRenderer.setView((OrthographicCamera) stage.getCamera());
        tiledMapRenderer.render();
        stage.act(delta); //todo profile
        if (worldManager.factory.isBuilding()||worldManager.factory.isDestroying()){
            if (worldManager.factory.isBuilding())
                stage.getBatch().setColor(Color.GREEN);
            if (worldManager.factory.isDestroying())
                stage.getBatch().setColor(Color.RED);
        }else {
            stage.getBatch().setColor(Color.WHITE);
        }
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
                    stageUI.getCamera().position.x-screen_width/2,
                    stageUI.getCamera().position.y+screen_height/2-0);
            game.font.draw(game.batch, "Player Coordinates: "
                            + (int) player.getX() + ":" + (int) player.getY() ,
                    stageUI.getCamera().position.x-screen_width/2, stageUI.getCamera().position.y+screen_height/2-20);
            game.font.draw(game.batch, "Mouse Coordinates: "
                            + stagePos1.x + ":" + stagePos1.y + " / " + lastInputTileX + ":" + lastInputTileY,
                    stageUI.getCamera().position.x-screen_width/2, stageUI.getCamera().position.y+screen_height/2-40);
//            game.font.draw(game.batch, "Camera Coordinates: "
//                            + (int) stageUI.getCamera().position.x + ":" + (int) stageUI.getCamera().position.y ,
//                    stageUI.getCamera().position.x-screen_width/2, stageUI.getCamera().position.y+screen_height/2-40);
            game.batch.end();

            // Render Debug shapes ?
//            debugShape ;
            debugShape.setProjectionMatrix(stage.getCamera().combined);
            debugShape.begin(ShapeRenderer.ShapeType.Line);
            debugShape.setColor(Color.RED); // Border Colider RED
            debugShape.rect(player.getBorderX(),
                    player.getBorderY(),
                    player.getBorderW(),
                    player.getBorderH());
//            shape.rect( worldManager.tree1.getBorderX(),
//                    worldManager.tree1.getBorderY(),
//                    worldManager.tree1.getBorderW(),
//                    worldManager.tree1.getBorderH());
//            shape.rect( worldManager.tree2.getBorderX(),
//                    worldManager.tree2.getBorderY(),
//                    worldManager.tree2.getBorderW(),
//                    worldManager.tree2.getBorderH());
//            shape.rect( worldManager.tree3.getBorderX(),
//                    worldManager.tree3.getBorderY(),
//                    worldManager.tree3.getBorderW(),
//                    worldManager.tree3.getBorderH());
            debugShape.setColor(Color.YELLOW);    // Range Yellow
            debugShape.circle(player.getRange().x,
                    player.getRange().y,
                    player.getRange().radius);
            if (points!=null){
                for(int i = 0; i < 99-1; ++i)
                {
                    debugShape.line(points[i], points[i+1]);
                }
            }


            debugShape.end();
            // debugging if end
        }else {
            game.batch.begin();
            game.font.draw(game.batch, "FPS: "+ Gdx.graphics.getFramesPerSecond(),
                    stageUI.getCamera().position.x-screen_width/2,
                    stageUI.getCamera().position.y+screen_height/2-0);
            game.batch.end();
        }
    }

    private void randomize() {
//        Random r = new Random(); //todo to be deleted , or not to be deleted
//        worldManager.tree1.setX(r.nextInt(100)     );
//        worldManager.tree1.setY(r.nextInt(100)     );
//        worldManager.tree2.setX(r.nextInt(200)+100 );
//        worldManager.tree2.setY(r.nextInt(200)+100 );
//        worldManager.tree3.setX(r.nextInt(300)+200 );
//        worldManager.tree3.setY(r.nextInt(100)     );
    }
    private void saveGame() {
        // parse worldManager object to data
//        saveLoadData.getChunksToSave(worldManager.chunks[0][0]);
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
        // parse data to worldManager object
//        saveLoadData.setChunksToLoad(worldManager.chunks[0][0]);
        // notify load success
        System.out.println("Load Successful");
    }
    private static class SaveLoadData{
        private int testInt;
        private float testFloat;
        private String testString;
        private Chunk chunk;
        void getChunksToSave(){
            this.testInt = chunk.testInt         ;
            this.testFloat = chunk.testfloat       ;
            this.testString = chunk.testString      ;
        }
        void setChunksToLoad(){
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
        // todo dispose atlas
        // todo dispose temp textures
        // todo dispose chunk images
    }

    private  Vector2[] points ;
}

    //stage.act( Math.minDeltaDebug( Gdx.graphics.getDeltaTime(), 1/30 ) );

