package com.mygdx.game.ants.something.animated.event.npc.a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.ants.something.animated.event.a.AnimatedEventSomething;
import com.mygdx.game.enums.entity.Entity;
import com.mygdx.game.enums.entity.EntityAnimation;
import com.mygdx.game.world.WorldResAnimManager;

public class Npc extends AnimatedEventSomething implements Telegraph {
    StateMachine<Npc, NpcState> stateMachine;
    private boolean go;
    private float time = 0;
    public void go(){
        go = true;
    }
    private Path path;
    public Npc(Entity entity, EntityAnimation entityAnim) {
        super(entity, entityAnim,NpcState.values().length);
        this.entity = Entity.Temp; //todo fix ASAP
    }
    @Override
    public void setAnim(WorldResAnimManager animManager){
        super.setAnim(animManager);
        // changes in animation logic
        animation.setLoopingEndless(false);
        stateMachine = new DefaultStateMachine<Npc, NpcState>(this, NpcState.RESTING);
        stateMachine.changeState(NpcState.IDLE);
        // test
        animation.setLoopingEndless(false);
//        texReg = animation.getTEMPPP();
    }
    @Override
    public void act(float delta){
        texReg = animation.updateFrame(delta);
        stateMachine.update();
//        if (go){ //todo fix move testing
//            float updateX = 1;
//            float updateY = 0;
//            moveBy(updateX, updateY);
//
//            loads += delta;
//            if (loads > 3){ //stop after 3 sec
//                go = false;
//                loads = 0;
//            }
//        }
        if (go){ //todo fix move testing

            current += delta * speed;
            if(current >= 1)
                current -= 1;
            float place = current * k;
            Vector2 first = points[(int)place];
            Vector2 second;
            if(((int)place+1) < k)
            {
                second = points[(int)place+1];
            }
            else
            {
                second = points[0]; //or finish, in case it does not loop.
            }
            float t = place - ((int)place); //the decimal part of place
            setPosition(first.x + (second.x - first.x) * t, first.y + (second.y - first.y) * t);


            time += delta;
            if (time > 3){ //stop after 3 sec
                go = false;
                time = 0;
            }
        }

    }
    @Override
    public boolean handleMessage(Telegram msg) {
        Gdx.app.debug("FSM",
                "EntityID:"+this.getEntityName()+
                        " state:"+this.stateMachine.getCurrentState().name()+
                        " handleMessage: "+msg.message);
        return stateMachine.getCurrentState().onMessage(this, msg);
    }
    float speed = 0.15f;
    float current = 0;
    CatmullRomSpline<Vector2> myCatmull;
    private int k ; //increase k for more fidelity to the spline
    private Vector2[] points  ;
    public Vector2[] findPath(Stage stage){
//        CatmullRomSpline<Vector2> myCatmull = new CatmullRomSpline<Vector2>(dataSet, true);
//        Vector2 out = new Vector2();
//        myCatmull.valueAt(out, t);
//        myCatmull.derivativeAt(out, t);

        /*members*/
         k = 100; //increase k for more fidelity to the spline
         points = new Vector2[k];
//        Vector2[] dataSet = new Vector2[3];
//        dataSet[0] = new Vector2(0,0);
//        dataSet[1] = new Vector2(500,500);
//        dataSet[2] = new Vector2(1000,1000);
        Vector2[] dataSet = {
                new Vector2(50,150), new Vector2(50,150),
                new Vector2(400,400), new Vector2(600,150), new Vector2(700,400),
                new Vector2(860,150), new Vector2(860,150)
        };
//        Vector2[] dataSet = {
//                new Vector2(50,150), new Vector2(50,150),
//                new Vector2(400,800), new Vector2(600,150), new Vector2(700,400),
//                new Vector2(860,150), new Vector2(860,150)
//        };
//        dataSet[1] = new Vector2(stage.getViewport().getScreenX()/2 , stage.getViewport().getScreenY()/2);
//        dataSet[2] = new Vector2(stage.getViewport().getScreenX(),stage.getViewport().getScreenY());

        /*init()*/

        myCatmull = new CatmullRomSpline<Vector2>(dataSet, false);
        for(int i = 0; i < k; ++i)
        {
            points[i] = new Vector2();
            myCatmull.valueAt(points[i], ((float)i)/((float)k-1));
//            myCatmull.derivativeAt(points[i], ((float)i)/((float)k-1));

            System.out.println("C = " + points[i].x + points[i].y);
        }
        return points;
    }
    @Override
    public void draw (Batch batch, float parentAlpha) {
        batch.draw(texReg, getX(), getY());
    }
}
