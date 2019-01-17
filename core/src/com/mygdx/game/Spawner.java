package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.ants.something.animated.event.a.AnimatedEventSomething;
import com.mygdx.game.ants.something.animated.event.npc.a.MyPath;
import com.mygdx.game.ants.something.animated.event.npc.a.Npc;
import com.mygdx.game.enums.entity.EntityAnimation;
import com.mygdx.game.enums.entity.EntityTex;
import com.mygdx.game.world.Clock;
import com.mygdx.game.world.Factory;
import com.mygdx.game.world.Job;
import com.mygdx.game.world.MyPathFinder;
import com.mygdx.game.world.WorldManager;
import com.mygdx.game.world.WorldResAnimManager;
import com.mygdx.game.world.WorldResTexRegManager;


public class Spawner extends AnimatedEventSomething {
    private Job job;
    private Vector1 destination;
    private Vector2[] vvv;
    private WorldResTexRegManager texRegManager;
    private WorldResAnimManager animManager;
    private Group world;
    private Npc[] pumps;
    public void setJob(Clock clock){
        job = new Job();
        job.hrs = 1;
        job.task = this.getClass().getName() + ": Job Done";
        job.vvv = this.vvv;
        job.npcs = this.pumps;
        job.endless = true;
        clock.shedule(job);
    }
    public Spawner(WorldResTexRegManager texRegManager, WorldResAnimManager animManager, Group world) {
        super();
        this.texRegManager      = texRegManager;
        this.animManager        = animManager;
        this.world              = world;
    }
//    public void create(Factory factory, Vector2 pos){
//        Something mob; //= new Npc(Entity.Temp,EntityAnimation.PUMPKIN);
//        int lastActorIndex = factory.somethingsOnDuty.size();
//        factory.buildOnEvent(EntityTex.Temp,pos);
//        mob = factory.somethingsOnDuty.get(lastActorIndex);
//    }
    public void set33Destination(int x, int y){
        destination = new Vector1(x/32,y/32);

    }
    public void set34Path(MyPathFinder pathFinder,Npc temp){
        pathFinder.calculate(temp, new Vector1(2,10)); //todo this>>npc
        Vector2[] vvv = pathFinder.getPath5();
        this.vvv = vvv;
        pumps = new Npc[30];
        for (int i = 0; i < pumps.length; i++) {
            pumps[i] = new Npc();
            pumps[i].set01EntityTex(EntityTex.Temp);
            pumps[i].set02EntityAnim(EntityAnimation.PUMPKIN);
            pumps[i].set11TexReg(texRegManager);
            pumps[i].set12Anim(animManager);
            pumps[i].set21Bounds();
            pumps[i].set22Borders(0,0);
            pumps[i].set23Range();
            pumps[i].set31World(world);
            pumps[i].set32Position(2*32,2*32);
        }

    }
}
