package com.mygdx.game.ants.something.worldObjects;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum DoorState implements State<Door> {
    OPEN(){
        @Override
        public void enter(Door door) {

        }

        @Override
        public void update(Door door) {
            if (door.unhandledSignal){
                door.stateMachine.changeState(DoorState.CLOSING);
            }
        }

        @Override
        public void exit(Door door) {

        }


    },
    CLOSED(){
        @Override
        public void enter(Door door) {

        }

        @Override
        public void update(Door door) {
            if (door.unhandledSignal){
                door.stateMachine.changeState(DoorState.OPENING);
            }
        }

        @Override
        public void exit(Door door) {

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
    };
    @Override
    public boolean onMessage(Door door, Telegram telegram) {
        return false;
    }
}
