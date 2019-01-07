package com.mygdx.game.ants.something.animated.event.a;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.mygdx.game.enums.BasicEvents;

public enum DoorState implements State<Door> {
    OPEN(){
        @Override
        public void enter(Door door) {

        }

        @Override
        public void update(Door door) {
//            if (door.unhandledSignal){ //todo WIPE
//                door.stateMachine.changeState(DoorState.CLOSING);
//            }
        }

        @Override
        public void exit(Door door) {

        }

        @Override
        public boolean onMessage(Door entity, Telegram telegram) {
            if (telegram.message==BasicEvents.CLOSE.getID()){
                entity.stateMachine.changeState(DoorState.CLOSED);
            }
            return true;
        }
    },
    CLOSED(){
        @Override
        public void enter(Door door) {

        }

        @Override
        public void update(Door door) {

        }

        @Override
        public void exit(Door door) {

        }

        @Override
        public boolean onMessage(Door entity, Telegram telegram) {
            if (telegram.message==BasicEvents.OPEN.getID()){
                entity.stateMachine.changeState(DoorState.OPEN);
            }
            return false;
        }
    },
    CLOSING(){
        @Override
        public void enter(Door door) {

        }

        @Override
        public void update(Door door) {

        }

        @Override
        public void exit(Door door) {

        }

        @Override
        public boolean onMessage(Door entity, Telegram telegram) {
            
            return true;
        }
    },
    OPENING(){
        @Override
        public void enter(Door door) {

        }

        @Override
        public void update(Door door) {

        }

        @Override
        public void exit(Door door) {

        }

        @Override
        public boolean onMessage(Door entity, Telegram telegram) {
            
            return true;
        }
    };
}
