package com.mygdx.game.ants.something.animated.event.a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;

import com.mygdx.game.enums.entity.EntityClass;
import com.mygdx.game.enums.entity.EntityTex;
import com.mygdx.game.world.WorldResAnimManager;
import com.mygdx.game.enums.entity.EntityAnimation;

public class Door extends AnimatedEventSomething implements Telegraph {
    StateMachine<Door, DoorState> stateMachine;
    public Door() {
        super();
    }
    @Override
    public void set12Anim(WorldResAnimManager animManager){
        super.set12Anim(animManager);
        // changes in animation logic
        animation.setLoopingEndless(false);
        // fsm init
        stateMachine = new DefaultStateMachine<Door, DoorState>(this, DoorState.CLOSED);
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        stateMachine.update();
    }
    @Override
    public boolean handleMessage(Telegram msg) {
            Gdx.app.debug("FSM",
                    "EntityID:"+this.getEntityName()+
                            " state:"+this.stateMachine.getCurrentState().name()+
                            " handleMessage: "+msg.message);
        return stateMachine.getCurrentState().onMessage(this, msg);
    }

}
