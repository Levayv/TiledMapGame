package com.mygdx.game.enums.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.mygdx.game.ants.something.a.AllData;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.ants.something.a.SomethingData;

public class jsonDataLoaderForEntities {
    public jsonDataLoaderForEntities(){

    }
    public AllData getAllData(){
        Gdx.app.debug("jsonDataLoaderForEntities", "Loading files");
        FileHandle fileExample = Gdx.files.local("data/prop/example.txt");
        FileHandle file = Gdx.files.local("data/prop/data.txt");
        Json saveLoadJson = new Json();
        saveLoadJson.setOutputType(JsonWriter.OutputType.json);
        saveLoadJson.setUsePrototypes(false);
        //output for example only
        AllData dataExample1 = new AllData();
        dataExample1.setExample(Entity.values().length);
        fileExample.writeString(saveLoadJson.prettyPrint(dataExample1),false);
        //input read json
        AllData dataFromFile = saveLoadJson.fromJson(AllData.class, file);        //read
        Gdx.app.debug("jsonDataLoaderForEntities", file.path()+" Load Succesfull");
        file = null;
        fileExample = null;
        return dataFromFile;
    }
}
