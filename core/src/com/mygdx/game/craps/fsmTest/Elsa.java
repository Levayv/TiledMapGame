package com.mygdx.game.craps.fsmTest;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.ai.msg.Telegram;

/** @author davebaol */
public class Elsa implements Telegraph {

    // an instance of the state machine class
    private StateMachine<Elsa, ElsaState> stateMachine;

    // is she presently cooking?
    boolean cooking;

    com.mygdx.game.craps.fsmTest.Bob bob;

    public Elsa () {
        this(null);
    }

    public Elsa (com.mygdx.game.craps.fsmTest.Bob bob) {
        stateMachine = new DefaultStateMachine<Elsa, ElsaState>(this, ElsaState.DO_HOUSE_WORK, ElsaState.GLOBAL_STATE);
        this.bob = bob;
    }

    @Override
    public boolean handleMessage (Telegram msg) {
        return stateMachine.handleMessage(msg);
    }

    public void update (float delta) {
        stateMachine.update();
    }

    public StateMachine<Elsa, ElsaState> getStateMachine () {
        return stateMachine;
    }

    public com.mygdx.game.craps.fsmTest.Bob getBob () {
        return bob;
    }

    public void setBob (com.mygdx.game.craps.fsmTest.Bob bob) {
        this.bob = bob;
    }

    public boolean isCooking () {
        return cooking;
    }

    public void setCooking (boolean cooking) {
        this.cooking = cooking;
    }

}