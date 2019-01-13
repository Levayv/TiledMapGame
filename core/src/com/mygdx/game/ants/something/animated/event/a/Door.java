package com.mygdx.game.ants.something.animated.event.a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.g2d.Batch;
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
        // changes in animation logic
        animation.setLoopingEndless(false);
        stateMachine = new DefaultStateMachine<Door, DoorState>(this, DoorState.CLOSING);
        stateMachine.changeState(DoorState.CLOSED);
        // test
        animation.setLoopingEndless(false);
    }
    @Override
    public void act(float delta) {
        texReg = animation.updateFrame(delta);
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
