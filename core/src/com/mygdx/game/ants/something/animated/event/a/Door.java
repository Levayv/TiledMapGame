package com.mygdx.game.ants.something.animated.event.a;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;

import com.mygdx.game.enums.Entity;
import com.mygdx.game.enums.EntityAnimation;

public class Door extends AnimatedEventSomething {
    StateMachine<Door, DoorState> stateMachine;
    public Door(Entity entity, EntityAnimation entityAnimation) {
        super(entity, entityAnimation, DoorState.values().length);
        this.animationsCount = 2;
        this.entity = Entity.Temp; //todo fix ASAP
//        System.out.println(Entity.Temp+""+this.entity+"WTF");

        stateMachine = new DefaultStateMachine<Door, DoorState>(this, DoorState.CLOSED);
    }
    public void update (/*float delta*/) {
        stateMachine.update();
//        System.out.println("Door:" + stateMachine.getCurrentState());
//        System.out.println("Door:" + stateMachine.getCurrentState());
        ;

//        System.out.println(this.getClass().toString() + this.getEntityName() ); //todo fix ASAP
    }
    public boolean unhandledSignal = false;

}
