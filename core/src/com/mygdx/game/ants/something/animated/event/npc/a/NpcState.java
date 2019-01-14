package com.mygdx.game.ants.something.animated.event.npc.a;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum NpcState implements State<Npc> { //todo Animation will blow if FSM first stem missed ?
    IDLE(){
        @Override
        public void enter(Npc entity) {

        }
        @Override
        public void update(Npc entity) {

        }
        @Override
        public void exit(Npc entity) {

        }
        @Override
        public boolean onMessage(Npc entity, Telegram telegram) {
            return false;
        }
    },
    MOVEING(){
        @Override
        public void enter(Npc entity) {

        }
        @Override
        public void update(Npc entity) {

        }
        @Override
        public void exit(Npc entity) {

        }
        @Override
        public boolean onMessage(Npc entity, Telegram telegram) {
            return false;
        }
    },
    RESTING(){
        @Override
        public void enter(Npc entity) {

        }
        @Override
        public void update(Npc entity) {

        }
        @Override
        public void exit(Npc entity) {

        }
        @Override
        public boolean onMessage(Npc entity, Telegram telegram) {
            return false;
        }
    };
    @Override
    public void enter(Npc entity) {

    }
    @Override
    public void update(Npc entity) {

    }
    @Override
    public void exit(Npc entity) {

    }
    @Override
    public boolean onMessage(Npc entity, Telegram telegram) {
        return false;
    }
}
