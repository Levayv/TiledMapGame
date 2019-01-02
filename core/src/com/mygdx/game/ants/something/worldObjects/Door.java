package com.mygdx.game.ants.something.worldObjects;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.scenes.scene2d.Group;

import com.mygdx.game.WorldResTexRegManager;
import com.mygdx.game.ants.something.animated.doorAKAeventBased.AnimatedEventSomething;
import com.mygdx.game.enums.Entity;

public class Door extends AnimatedEventSomething implements doorShit {
    public Door(Entity entity, WorldResTexRegManager texRegManager, Group world, String file, int rows) {
        super(entity, texRegManager, world, file, rows, DoorState.values().length);
        this.animationsCount = 2;

        stateMachine = new DefaultStateMachine<Door, DoorState>(this, DoorState.CLOSED);
    }
    StateMachine<Door, DoorState> stateMachine;
    public void update (/*float delta*/) {
        stateMachine.update();
//        System.out.println("Door:" + stateMachine.getCurrentState());
//        System.out.println("Door:" + stateMachine.getCurrentState());
    }
    public boolean unhandledSignal = false;

}
