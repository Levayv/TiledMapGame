package com.mygdx.game.enums.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.mygdx.game.ants.something.a.AllData;

public class jsonDataLoaderForEntities {
    private AllData allData;
    public jsonDataLoaderForEntities(){

    }
    public AllData getAllData(){
        Gdx.app.debug("jsonDataLoaderForEntities", "Loading files");
        FileHandle fileExample = Gdx.files.local("root/data/prop/example.json"); //todo fix file
        FileHandle file = Gdx.files.local("root/data/prop/data.json"); //todo fix file
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        json.setUsePrototypes(false);
        //output for example only
        AllData dataExample1 = new AllData();
        dataExample1.setExample();
        fileExample.writeString(json.prettyPrint(dataExample1),false);
        //input read json
        AllData dataFromFile = json.fromJson(AllData.class, file);        //read
        Gdx.app.debug("jsonDataLoaderForEntities", file.path()+" Load Succesfull");
        file = null;
        fileExample = null;
        json = null;
        dataExample1 = null;
        return dataFromFile;
    }
}
