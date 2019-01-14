package com.mygdx.game.ants.something.animated.event.a;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.mygdx.game.enums.Events.BasicDoorEvents;

public enum DoorState implements State<Door> { //todo polish commented prints
    OPEN(){
        @Override
        public void enter(Door door) {
            door.animation.setToLastFrame();
        }

        @Override
        public void update(Door door) {

        }

        @Override
        public void exit(Door door) {

        }
        @Override
        public boolean onMessage(Door door, Telegram telegram) {
            if (telegram.message==BasicDoorEvents.CLOSE.getID()){
                door.stateMachine.changeState(DoorState.CLOSING);
            }
            return false;
        }
    },
    CLOSED(){
        @Override
        public void enter(Door door) {
            door.animation.setToFirstFrame();
        }

        @Override
        public void update(Door door) {

        }

        @Override
        public void exit(Door door) {

        }

        @Override
        public boolean onMessage(Door door, Telegram telegram) {
            if (telegram.message==BasicDoorEvents.OPEN.getID()){
                door.stateMachine.changeState(DoorState.OPENING);
            }
            return false;
        }
    },
    CLOSING(){
        @Override
        public void enter(Door door) {
//            System.out.println("FSM closing enter");
            door.animation.startAnim(true);
        }

        @Override
        public void update(Door door) {
            if (!door.animation.isAlive())
                door.stateMachine.changeState(DoorState.CLOSED);
        }

        @Override
        public void exit(Door door) {
//            System.out.println(door.getClass().getSimpleName()+"FSM closing exit");

        }

        @Override
        public boolean onMessage(Door door, Telegram telegram) {
            
            return true;
        }
    },
    OPENING(){
        @Override
        public void enter(Door door) {
//            System.out.println(door.getClass().getSimpleName()+"FSM opening enter");
            door.animation.startAnim(false);
        }

        @Override
        public void update(Door door) {
            if (!door.animation.isAlive())
                door.stateMachine.changeState(DoorState.OPEN);
        }

        @Override
        public void exit(Door door) {
//            System.out.println("FSM opening exit");
        }

        @Override
        public boolean onMessage(Door door, Telegram telegram) {
            
            return true;
        }
    };
}
