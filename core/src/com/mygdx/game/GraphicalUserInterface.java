package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.Random;

public class GraphicalUserInterface {
    private boolean debuging = true;
    public Table tableRoot;
    GraphicalUserInterface(Stage stage){

        //skin init
        String fileLoc = "data/neon.json"; // skin location
        if (debuging){
            if  (new FileHandle(fileLoc).exists())
                System.out.println("file OK");
            else
                System.out.println("file Missing");
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
        tableRoot.right().top(); //! adding future actors default alignment
//        tableRoot.setTouchable(Touchable.childrenOnly);
        stage.addActor(tableRoot);
        // other ui items init , add to table
        TextButton button1 = new TextButton("Click me", skin, "default");
        TextButton button2 = new TextButton("Click me too", skin, "default");
        int buttonW = 200;
        int buttonH = 20;
        button1.setName("button1 shit");
        button2.setName("button2 shit");
        button1.setWidth(buttonW);
        button2.setWidth(buttonW);
        button1.setHeight(buttonH);
        button2.setHeight(buttonH);
        button1.setTouchable(Touchable.enabled);
        button2.setTouchable(Touchable.enabled);
//        WidgetGroup widgetGroup = new WidgetGroup();
//        widgetGroup.setFillParent(true);
//        widgetGroup.addActor(button1);
//        widgetGroup.addActor(button2);
//        tableRoot.add(widgetGroup);
        Table table2 = new Table();
        table2.add(button1);
        table2.add(button2);
        Label label1 = new Label("123",skin);
        tableRoot.add(label1);
        tableRoot.add(table2);
//        tableRoot.add(button1);
//        tableRoot.add(button2);
//         listeners
        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                System.out.print("GUI: ");
//                System.out.print("Changed 1 Shit");
//                System.out.println();
//                System.out.println(actor.getName());
            }
        });
        button2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                System.out.print("GUI: ");
//                System.out.print("Changed 2 Shit");
//                System.out.println();
                return false;
            }
        });
    }

}
