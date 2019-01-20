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
import com.mygdx.game.enums.Events.BasicEvents;
import com.mygdx.game.enums.entity.EntityAnimation;
import com.mygdx.game.world.WorldResAnimManager;

import java.util.ArrayList;
import java.util.List;

public class Npc extends AnimatedEventSomething implements Telegraph {
    StateMachine<Npc, NpcState> stateMachine;
    public MessageDispatcher dispatcher = new MessageDispatcher();
    private NpcTalk extraInfo = new NpcTalk();
    private NpcDistance distance = new NpcDistance();
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
    public void go(){
        go = true;
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
        super.act(delta);
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

//            int ppp = (int) current * k;
//            current += delta * speed / points[ppp].len() *1000;
//            current += 1000* (delta * speed / myCatmull.spanCount) / points[ppp].len();
//            current += (delta * speed / myCatmull.spanCount) / points[0].len();

            if (myPath.nextDot(delta)){
                go = false;

            }else {
                lastX = myPath.getNextX();
                lastY = myPath.getNextY();
                extraInfo.center = new Vector1(getCenter().x,getCenter().y);
                dispatcher.dispatchMessage(BasicEvents.NPC_MOVED.getID(), extraInfo);
                set32Position(lastX , lastY);
                time += delta;
                if (time > 30) { //stop after 3 sec
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
        if (distance.center!=null && distance.targetCenter!=null){
            shapes.setColor(Color.BLACK);
            shapes.line(
                    distance.center.x,distance.center.y,
                    distance.targetCenter.x,distance.targetCenter.y
                    );
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
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
            System.out.println("!!! this="+this.entityAnim.name()+","+this.getName());
            System.out.println("!!! msg ="+msg.message);
            System.out.println("!!! msg ="+BasicEvents.values()[msg.message]);
            System.out.println("!!! exI f="+extraInfoBuffer.faction.id);
            System.out.println("!!! exI x="+extraInfoBuffer.center.x);
            System.out.println("!!! exI y="+extraInfoBuffer.center.y);
            if (extraInfoBuffer.faction.id != this.extraInfo.faction.id){
                System.out.println("!!! exI is Enemy");
                if (this.distance.inRange(extraInfoBuffer.center))
                    System.out.println("!!! exI is in range");
            }
        }

        return stateMachine.getCurrentState().onMessage(this, msg);
    }
    //-------------------------------------------------------------------------------------------//
    public MyPath myPath = new MyPath();
    List<Vector2> path25 = new ArrayList<Vector2>();
    Vector2[] path5;
    public void moveToPosition(Vector2[] vectorArg){
        int x = 100;
        int y = 100;
//        Random r = new Random();
//        for (int i = 0; i < 200; i++) {
//            path25.add(new Vector2(x+r.nextInt(500),     y+r.nextInt(300)));
//        }
        //or
//        path25.add(new Vector2(x+0,     y-0));
//        path25.add(new Vector2(x+100,   y-0));
//        path25.add(new Vector2(x+100,   y-0));
//        path25.add(new Vector2(x+300,   y-0));
//        path25.add(new Vector2(x+400,   y-0));
//        path25.add(new Vector2(x+500,   y-0));
//        path25.add(new Vector2(x+600,   y-0));
//        path25.add(new Vector2(x+700,   y-0));
//        path25.add(new Vector2(x+600,   y-100));
//        path25.add(new Vector2(x+500,   y-200));
//        path25.add(new Vector2(x+400,   y-300));
//        path25.add(new Vector2(x+300,   y-400));
//        path25.add(new Vector2(x+200,   y-500));
//        path25.add(new Vector2(x+100,   y-600));
//        path25.add(new Vector2(x+900,   y-0));
        lastX = this.getBorderX();
        lastY = this.getBorderY();

        path5 = vectorArg;

//        this.set32Position(path5[0].x , path5[0].y);
//        path5[0] = new Vector2(lastX,lastY);
//        path5 = new Vector2[path25.size()];
//        path5 = path25.toArray(path5);

        myPath.speed *= 20.25 / (path5.length);
        myPath.findSpline(path5,path5.length * 10 );
        go = true;
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
