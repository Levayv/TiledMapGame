package com.mygdx.game.ants.something.animated.event.a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.mygdx.game.world.WorldResAnimManager;
import com.mygdx.game.enums.entity.Entity;
import com.mygdx.game.enums.entity.EntityAnimation;

public class Door extends AnimatedEventSomething implements Telegraph {
    StateMachine<Door, DoorState> stateMachine;
    public Door(Entity entity, EntityAnimation entityAnimation) {
        super(entity, entityAnimation, DoorState.values().length);
        this.entity = Entity.Temp; //todo fix ASAP

    }
    @Override
    public void setAnim(WorldResAnimManager animManager){
        super.setAnim(animManager);
        animation.setLoopingEndless(false);
        stateMachine = new DefaultStateMachine<Door, DoorState>(this, DoorState.CLOSING);
        stateMachine.changeState(DoorState.CLOSED);
        // test
        animation.setLoopingEndless(false);
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
