package com.mygdx.game.ants.something.animated.event.npc.a;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.Vector1;
import com.mygdx.game.ants.something.animated.event.a.AnimatedEventSomething;
import com.mygdx.game.enums.events.BasicEvents;
import com.mygdx.game.enums.entity.EntityAnimation;
import com.mygdx.game.world.WorldResAnimManager;

public class Npc extends AnimatedEventSomething implements Telegraph {
    StateMachine<Npc, NpcState> stateMachine;
    public MessageDispatcher dispatcher = new MessageDispatcher();
    private NpcTalk extraInfo = new NpcTalk();
    private NpcDistance distance = new NpcDistance();
    public float lastDelta;
    private boolean go;
    private float lastX;
    private float lastY;
    private float time = 0;
//    private Job job;
//    public void setJob(Clock clock){
//        job = new Job();
//        job.hrs = 1;
//        job.task = "Peasent: Job Done";
//        clock.shedule(job);
//    }
    public void setGo(){
        go = true;
    }
    public boolean getGo(){
        return go;
    }
    public Npc() { //todo redactor this sh*
        super();
    }
    @Override
    public void set12Anim(WorldResAnimManager animManager){
        super.set12Anim(animManager);
        // changes in animation logic //todo fix this
        if (entityAnim == EntityAnimation.TOWER)
            this.extraInfo.faction.id = 1;
        if (entityAnim == EntityAnimation.PUMPKIN)
            this.extraInfo.faction.id = 3;
        animation.setLoopingEndless(true);
        // fsm init
        stateMachine = new DefaultStateMachine<Npc, NpcState>(this, NpcState.IDLE);
    }
    @Override
    public void set23Range() { //todo fix this
        super.set23Range();
        distance.setMyCenter(this.getCenter());
        distance.setMyRange(this.getRange().radius);
    }
    @Override
    public void set23Range(int diff) { //todo fix this
        super.set23Range(diff);
        distance.setMyCenter(this.getCenter());
        distance.setMyRange(this.getRange().radius);
    }
    @Override
    public void act(float delta){
//        super.act(delta); // todo , just changing in logic
        this.lastDelta = delta;
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
        if (go) { //todo fix move testing
//            if  (stateMachine.getCurrentState().equals(NpcState.IDLE))
//                stateMachine.changeState(NpcState.MOVING_BEGIN);
//            int ppp = (int) current * k;
//            current += delta * speed / points[ppp].len() *1000;
//            current += 1000* (delta * speed / myCatmull.spanCount) / points[ppp].len();
//            current += (delta * speed / myCatmull.spanCount) / points[0].len();

            if (myPath.nextDot(delta)){
                go = false;
//                if  (stateMachine.getCurrentState().equals(NpcState.MOVING))
//                    stateMachine.changeState(NpcState.MOVING_END);

            }else {
                lastX = myPath.getNextX();
                lastY = myPath.getNextY();
//                extraInfo.center = new Vector1(getCenter().x,getCenter().y);
                extraInfo.setCenter(this.getCenter());
                extraInfo.outerBorder = new Vector1((int)getBorderW(),(int)getBorderH());
                dispatcher.dispatchMessage(BasicEvents.NPC_MOVED.getID(), extraInfo);
                set32Position(lastX , lastY);
                time += delta;
                if (time > 30) { //stop after 3 sec
//                    if  (stateMachine.getCurrentState().equals(NpcState.MOVING))
//                        stateMachine.changeState(NpcState.MOVING_END);
                    go = false;
                    time = 0;
                }
            }

        }

    }
    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);
        // changes in draw behavior, like hide
    }
    @Override
    protected void drawDebugBounds (ShapeRenderer shapes) {
        super.drawDebugBounds(shapes);
        if (distance.inRangeB){
            shapes.setColor(Color.CYAN);
            shapes.line(
                    distance.center.x,distance.center.y,
                    distance.targetCenter.x,distance.targetCenter.y
                    );
        }
        if (path5 != null){
            if (path5.length>1){
                shapes.setColor(Color.BLACK);
                for (int i = 1; i < path5.length; i++) {
                    shapes.line(path5[i-1].x,path5[i-1].y,
                            path5[i].x,path5[i].y);
                }
            }
        }
    }
    @Override
    public boolean handleMessage(Telegram msg) {
        Gdx.app.debug("FSM",
                "EntityID:"+this.getEntityName()+
                        " state:"+this.stateMachine.getCurrentState().name()+
                        " handleMessage: "+msg.message+"="+BasicEvents.values()[msg.message]);
        if (msg.message == BasicEvents.NPC_MOVED.getID()){
            NpcTalk extraInfoBuffer;
            extraInfoBuffer = (NpcTalk) msg.extraInfo ;
            //Gdx.app.debug("Npc", "hndleMsg msg = "+this.entityAnim.name()+","+this.getName());
            //Gdx.app.debug("Npc", "hndleMsg msg = "+msg.message);
            //Gdx.app.debug("Npc", "hndleMsg msg = "+BasicEvents.values()[msg.message]);
            //Gdx.app.debug("Npc", "hndleMsg msg = "+extraInfoBuffer.faction.id);
            //Gdx.app.debug("Npc", "hndleMsg msg = "+extraInfoBuffer.center.x);
            //Gdx.app.debug("Npc", "hndleMsg msg = "+extraInfoBuffer.center.y);
            if (extraInfoBuffer.faction.id != this.extraInfo.faction.id){
                Gdx.app.debug("Npc", "hndleMsg exI is Enemy");
                if (this.distance.inRange(extraInfoBuffer.getCenter(), extraInfoBuffer.outerBorder)){
                    Gdx.app.debug("Npc", "hndleMsg exI is in range");
                }
            }
        }

        return stateMachine.getCurrentState().onMessage(this, msg);
    }
    //-------------------------------------------------------------------------------------------//
    public MyPath myPath = new MyPath();
    Vector2[] path5;
    public void moveToPosition(Vector2[] vectorArg){
        int x = 100;
        int y = 100;

        lastX = this.getBorderX();
        lastY = this.getBorderY();

        path5 = vectorArg;

        myPath.speed *= 20.25 / (path5.length-1);
        myPath.findSpline(path5,path5.length * 10 );
//        go = true;
    }
    //-------------------------------------------------------------------------------------------//
//    float speed = 0.15f;
//    float current = 0;
//    CatmullRomSpline<Vector2> myCatmull;
//    private int k ; //increase k for more fidelity to the spline
//    private Vector2[] points  ;
//    public Vector2[] findPath(){
////        CatmullRomSpline<Vector2> myCatmull = new CatmullRomSpline<Vector2>(dataSet, true);
////        Vector2 out = new Vector2();
////        myCatmull.valueAt(out, t);
////        myCatmull.derivativeAt(out, t);
//
//        /*members*/
//         k = 100; //increase k for more fidelity to the spline
//         points = new Vector2[k];
////        Vector2[] dataSet = new Vector2[3];
////        dataSet[0] = new Vector2(0,0);
////        dataSet[1] = new Vector2(500,500);
////        dataSet[2] = new Vector2(1000,1000);
//        Vector2[] dataSet = {
//                new Vector2(50,150), new Vector2(50,150),
//                new Vector2(400,400), new Vector2(600,150), new Vector2(700,400),
//                new Vector2(860,150), new Vector2(860,150)
//        };
//        myCatmull = new CatmullRomSpline<Vector2>(path5, false);
//        for(int i = 0; i < k; ++i)
//        {
//            points[i] = new Vector2();
//            myCatmull.valueAt(points[i], ((float)i)/((float)k-1));
//        }
//        return points;
//    }

}
