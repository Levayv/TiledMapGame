package com.mygdx.game.ants.something.animated.event.a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.mygdx.game.enums.EntityTex;
import com.mygdx.game.enums.EntityAnimation;

public class Door extends AnimatedEventSomething implements Telegraph {
    StateMachine<Door, DoorState> stateMachine;
    public Door(EntityTex entityTex, EntityAnimation entityAnimation) {
        super(entityTex, entityAnimation, DoorState.values().length);
        this.animationsCount = 2;
        this.entityTex = EntityTex.Temp; //todo fix ASAP
//        System.out.println(EntityTex.Temp+""+this.entityTex+"WTF");

        stateMachine = new DefaultStateMachine<Door, DoorState>(this, DoorState.CLOSED);
    }
    public void update (/*float delta*/) {
        stateMachine.update();
//        System.out.println("Door:" + stateMachine.getCurrentState());
//        System.out.println("Door:" + stateMachine.getCurrentState());
        ;

//        System.out.println(this.getClass().toString() + this.getEntityName() ); //todo fix ASAP
    }
//    public boolean unhandledSignal = false; todo WIPE !

    @Override
    public boolean handleMessage(Telegram msg) {
            Gdx.app.debug("FSM",
                    "EntityID:"+this.getEntityName()+
                            " state:"+this.stateMachine.getCurrentState().name()+
                            " handleMessage: "+msg.message);
        return stateMachine.getCurrentState().onMessage(this, msg);
    }
}
