package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.enums.Events.BasicEvents;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Clock {
    private MessageDispatcher dispatcher = new MessageDispatcher();
    private List<Job>  jobs;
    private Job  lastJob;
    private long raw;
    private int sec;
    private int min;
    private int hrs;
    private int day;
    private Date date;
    Clock(Factory factory){
        jobs = new ArrayList<Job>();
        date = new Date();
        date.setTime(946684800000L-14400000);
        dispatcher.addListener(factory , BasicEvents.FACTORY_AAA.getID());
    }
    public void update(float deltaFloat){
        long diff;
        diff = (int) (1 * 1000 * 1000);
        raw = raw + diff;
        if (raw>=60000){
            raw-=60000;
            min++;
            minUpdate(min);
            if (min>=60){
                min = 0;
//                raw-=3600000;
                hrs++;
                hourUpdate(hrs);
                if (hrs>=24){
                    hrs = 0;
//                    raw-=86400000;
                    day++;
                    dayUpdate(day);
                }
            }
        }
    }
    public String updateTimeNearFps(){ //todo temp

        return " Day=" + day + " Time= " + hrs + ":" + min + " ";
    }
    public void shedule(Job job){ //todo mek multi , min hrs day
        jobs.add(job);
    }
    private void minUpdate(int min){
        if (jobs.size()>0){

        }
    }
    private void hourUpdate(int hrs){
        if (jobs.size()>0){
//            System.out.println("!!! " + "hourUpdate");
//            lastJob = jobs.get(jobs.size()-1);
//            if (!lastJob.endless){
//                jobs.remove(jobs.size()-1);
//            }
//            lastJob.start();
            dispatcher.dispatchMessage(BasicEvents.FACTORY_AAA.getID());
        }
    }
    private void dayUpdate(int day){
        if (jobs.size()>0){
//            System.out.println("!!! " + "dayUpdate");

        }
    }
}
