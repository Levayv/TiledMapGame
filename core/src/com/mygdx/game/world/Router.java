package com.mygdx.game.world;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.mygdx.game.Vector1;
import com.mygdx.game.ants.something.animated.event.npc.a.Npc;
import com.mygdx.game.enums.Events.BasicEvents;

public class Router implements Telegraph {
    private MessageDispatcher dispatcher = new MessageDispatcher();
    public void add(Npc npc){
        this.dispatcher.addListener(npc         , BasicEvents.NPC_MOVED.getID());
        npc. dispatcher.addListener(this , BasicEvents.NPC_MOVED.getID());
    }
    public void remove(Npc npc){
        this.dispatcher.removeListener(npc         , BasicEvents.NPC_MOVED.getID());
        npc. dispatcher.removeListener(this , BasicEvents.NPC_MOVED.getID());

    }
    private void forward(Telegram msg, Object extraInfo){
        dispatcher.dispatchMessage(msg.message,extraInfo);
    }
    @Override
    public boolean handleMessage(Telegram msg) {
        forward(msg, msg.extraInfo);
        return false;
    }
}
