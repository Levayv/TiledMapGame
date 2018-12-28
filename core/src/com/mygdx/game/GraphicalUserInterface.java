package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class GraphicalUserInterface {
    private boolean debuging = true;
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
        Table tableRoot = new Table();
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
        stage.addActor(tableRoot);
        // other ui items init , add to table
        TextButton button = new TextButton("Click me", skin, "default");
        int buttonW = 200;
        int buttonH = 20;
        button.setName("button shit");
        button.setWidth(buttonW);
        button.setHeight(buttonH);
        tableRoot.add(button);
        // listeners
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.print("UI: ");
                System.out.print("Changed Shit");
                System.out.println();
            }
        });

    }

}
