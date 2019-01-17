package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Clock {
    private List<Job>  jobs;
    private Job  lastJob;
//    private long last;
    private long diff;
    private long raw;
    private int sec;
    private int min;
    private int hrs;
    private int day;
    private Date date;
    private DecimalFormat print = new DecimalFormat("##,###");
    private SimpleDateFormat outDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//    private SimpleDateFormat outDate = new SimpleDateFormat("HH:mm:ss");
    Clock(){
        date = new Date();
        date.setTime(946684800000L-14400000);
    }
    public void update(float deltaFloat){
        diff = (int) (deltaFloat*1000*1000);
        raw =  raw + diff ;

//        diff = (int) (deltaFloat*1000*1000);
//        raw =  raw + diff ;
//        System.out.println("!!!"+deltaFloat*1000*1000);
//        System.out.println("!!!"+diff);
//        System.out.println("!!!"+raw);
//        date.setTime(date.getTime()+raw+min*60000+hrs*3600000);
//        date.setTime(date.getTime() + raw);
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
//        System.out.println( outDate.format(date)+"+++"+date.getTime());
//        System.out.println(raw);
//        return outDate.format(date)+" +++ ";
        return " Day=" + day + " Time= " + hrs + ":" + min + " ";
    }
    public void shedule(Job job){
        jobs = new ArrayList<Job>();
        jobs.add(job);
        System.out.println("jobs.size()");
    }
    private void minUpdate(int min){
        if (jobs.size()>0){

        }
    }
    private void hourUpdate(int hrs){
        if (jobs.size()>0){
            lastJob = jobs.get(jobs.size()-1);
            if (!lastJob.endless){
                jobs.remove(jobs.size()-1);
            }
            lastJob.start();
        }
    }
    private void dayUpdate(int day){

    }
}
