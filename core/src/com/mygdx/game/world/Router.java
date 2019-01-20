package com.mygdx.game.world;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.mygdx.game.ants.something.animated.event.npc.a.Npc;
import com.mygdx.game.enums.Events.NpcEvents;

public class Router implements Telegraph {
    private MessageDispatcher dispatcher = new MessageDispatcher();
    public void add(Npc npc){
        this.dispatcher.addListener(npc         , NpcEvents.iMoved.getID());
        npc. dispatcher.addListener(this , NpcEvents.iMoved.getID());
    }
    public void remove(Npc npc){
        this.dispatcher.removeListener(npc         , NpcEvents.iMoved.getID());
        npc. dispatcher.removeListener(this , NpcEvents.iMoved.getID());

    }
    private void forward(Telegram msg){
        dispatcher.dispatchMessage(msg.message);
    }
    @Override
    public boolean handleMessage(Telegram msg) {
        forward(msg);
        return false;
    }
}
