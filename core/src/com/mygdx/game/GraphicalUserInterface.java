package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import com.mygdx.game.enums.BasicEvents;
import com.mygdx.game.world.WorldManager;

public class GraphicalUserInterface {
    private boolean debuging = false;
    public Table tableRoot;
    private MessageDispatcher dispatcher = new MessageDispatcher();
    GraphicalUserInterface(Stage stage, final WorldManager worldManager){

        //skin init
//        String fileLoc = "skins/1/neon.json"; // skin location
        String fileLoc = "skins/2/sgx-ui.json"; // ?
//        String fileLoc = "skins/3/glassy-ui.json"; // skin location
        if (debuging){
            if  (new FileHandle(fileLoc).exists())
                System.out.println("GUI: skin file OK");
            else
                System.out.println("GUI: skin file Missing");
        }
        Skin skin;
        skin = new Skin(Gdx.files.internal(fileLoc));

        // table init , add to stage AKA stageUI
        tableRoot = new Table();
        int tablePadX = 20; // for padding , screen vs tableRoot
        int tablePadY = 10; // for padding , screen vs tableRoot
        tableRoot.setName("table shit");
        tableRoot.setDebug(debuging);
        int rootX = stage.getViewport().getScreenX();
        int rootY = stage.getViewport().getScreenY();
        int rootW = stage.getViewport().getScreenWidth();
        int rootH = stage.getViewport().getScreenHeight();
        rootX += tablePadX;   // fix padding , pos and size by tablePadX/Y
        rootY += tablePadY;   // fix padding , pos and size by tablePadX/Y
        rootW -= tablePadX*2; // fix padding , pos and size by tablePadX/Y
        rootH -= tablePadY*2; // fix padding , pos and size by tablePadX/Y
        tableRoot.setPosition(rootX,rootY);
        tableRoot.setSize    (rootW,rootH);
        tableRoot.center(); //! adding future actors default alignment
        stage.addActor(tableRoot);
        // other ui items init , add to table
        TextButton button1 = new TextButton("CLOSE", skin, "default");
        TextButton button2 = new TextButton("OPEN ", skin, "default");
        int buttonW = 200;
        int buttonH = 20;
        button1.setName("button1 sh*t");
        button2.setName("button2 sh*t");
        button1.setWidth(buttonW);
        button2.setWidth(buttonW);
        button1.setHeight(buttonH);
        button2.setHeight(buttonH);
//        button1.setTouchable(Touchable.enabled);
//        button2.setTouchable(Touchable.enabled);

//        widgetGroup.setFillParent(true);
        Table rightPanelButtons = new Table();
        rightPanelButtons.add(button1);
        rightPanelButtons.add(button2);
        Label label1 = new Label("123",skin);

        // inventory table init
        Table leftPanelInventory = new Table();
        int size = 10;
        final TextButton[] buttons = new TextButton[size];
        for (int i = 0; i < size; i++) {
            buttons[i] = new TextButton("B"+i, skin, "default");
            buttons[i].setName("Button"+(i+1));
            buttons[i].setWidth(10);
            buttons[i].setHeight(10);
//            buttons[i].set
            leftPanelInventory.add(buttons[i]).center();
            if (i==4)
                leftPanelInventory.row();
        }

        tableRoot.add(leftPanelInventory).expand().bottom().left();
        tableRoot.add(label1).bottom();
        tableRoot.add(rightPanelButtons).expand().top().right();
        //FIXME listeners
        dispatcher.addListener(worldManager.door1     , BasicEvents.OPEN.getID());
        dispatcher.addListener(worldManager.door1     , BasicEvents.CLOSE.getID());

        for (int i = 0; i < worldManager.mobCount; i++) {
            dispatcher.addListener(worldManager.doorss[i] , BasicEvents.OPEN.getID());
            dispatcher.addListener(worldManager.doorss[i] , BasicEvents.CLOSE.getID());
        }
        for (int i = 1; i < size; i++) {
            buttons[i].addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.log("GUI","Button "+event.getListenerActor().getName()+" pressed");
                    return true;
                }
            });
        }
        buttons[0].addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                buttons[0].setChecked(true);
//                if(buttons[0].isChecked()) System.out.println("checked");
//                else System.out.println("not checked");
////                buttons[0].setChecked(true);
                if(buttons[0].isDisabled()) System.out.println("checked");
                else System.out.println("not checked");
                buttons[0].setDisabled(!buttons[0].isDisabled());
//                System.out.println("!"+this.getClass().getSimpleName()+"!");
//                worldManager
                //FIXME 10.01.2019
                worldManager.isBuilding = !worldManager.isBuilding;
//                worldManager.build(1,150,150);

                return true;
            }
        });
        button1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("GUI",event.getListenerActor().getName()+"  pressed");
                dispatcher.dispatchMessage(BasicEvents.CLOSE.getID());
                return true;
            }
        });
        button2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("GUI",event.getListenerActor().getName()+"  pressed");
                dispatcher.dispatchMessage(BasicEvents.OPEN.getID());
                return true;
            }
        });
        //        button1.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                Gdx.app.log("GUI","Button 1 pressed");
//                dispatcher.dispatchMessage(BasicEvents.CLOSE.getID());
//            }
//        });

    }

}
