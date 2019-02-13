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
            if (entity.getGo())
                entity.stateMachine.changeState(NpcState.MOVING_BEGIN);
            else
                entity.texReg = entity.animation.updateFrame(entity.lastDelta,0);
        }
        @Override
        public void exit(Npc entity) {

        }
        @Override
        public boolean onMessage(Npc entity, Telegram telegram) {
            return false;
        }
    },
    MOVING(){
        @Override
        public void enter(Npc entity) {

        }
        @Override
        public void update(Npc entity) {
            if (!entity.getGo())
                entity.stateMachine.changeState(NpcState.MOVING_END);
            else
                entity.texReg = entity.animation.updateFrame(entity.lastDelta,1);
//            System.out.println("!!! MOVING update");
        }
        @Override
        public void exit(Npc entity) {

        }
        @Override
        public boolean onMessage(Npc entity, Telegram telegram) {
            return false;
        }
    },
    MOVING_BEGIN(){
        @Override
        public void enter(Npc entity) {
//            entity.animation.startAnim(false);
//            entity.animation.setLoopingEndless(false);
//            System.out.println("!!! MOVING_BEGIN enter");
        }
        @Override
        public void update(Npc entity) {
//            System.out.println("!!! MOVING_BEGIN update "+(entity.animation.isAlive()?"true":"false"));
//            if (!entity.animation.isAlive()){
                entity.stateMachine.changeState(NpcState.MOVING);
//            }
//            entity.texReg = entity.animation.updateFrame(entity.lastDelta,0);
        }
        @Override
        public void exit(Npc entity) {
//            entity.animation.setLoopingEndless(true);
//            System.out.println("!!! MOVING_BEGIN exit");

        }
        @Override
        public boolean onMessage(Npc entity, Telegram telegram) {
            return false;
        }
    },
    MOVING_END(){
        @Override
        public void enter(Npc entity) {
            entity.animation.startAnim(false);
            entity.animation.setLoopingEndless(false);
            System.out.println("!!! MOVING_END enter");
        }
        @Override
        public void update(Npc entity) {
            if (!entity.animation.isAlive()){
                entity.stateMachine.changeState(NpcState.IDLE);
            }
            entity.texReg = entity.animation.updateFrame(entity.lastDelta,1);
        }
        @Override
        public void exit(Npc entity) {
            entity.animation.setLoopingEndless(true);
            System.out.println("!!! MOVING_END exit");

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
