package com.mygdx.game.debug.tools;

import com.badlogic.gdx.math.FloatCounter;
import com.badlogic.gdx.utils.TimeUtils;

import java.text.DecimalFormat;

public class MyProfiler {
//    private final static float timeFix = 1f / 1000000.0f;
//    private final static float deltaFix = 1000f ;
    private final static float timeFix = 1f / 1000.0f;
    private final static float deltaFix = 1000000f ;
    private long startTime = 0L;
    public final FloatCounter time;
    public final FloatCounter loads;
    public final FloatCounter deltas;
    private final String name;
    private float current = 0f;

    private DecimalFormat decFormatter = new DecimalFormat("##,###");
    private DecimalFormat percFormatter = new DecimalFormat("##,###");
    public MyProfiler (final String name) {
        this(name, 120);
    }
    public MyProfiler (final String name, final int windowSize) {
        this.name = name;
        this.time = new FloatCounter(windowSize);
        this.loads = new FloatCounter(windowSize);
        this.deltas = new FloatCounter(windowSize);
//        this.load = new FloatCounter(1);
    }
    public void start(){
        startTime = TimeUtils.nanoTime();
    }
    public void stop(float delta){
        if (startTime > 0L) {
            current = (TimeUtils.nanoTime() - startTime);
//            System.out.println();
//            System.out.println("!"+current);
            loads.put(current);
            deltas.put(delta);
        }
    }
    public void export(boolean profiling){
        if (profiling){
            System.out.println(name +
                    "Delta="+
                    decFormatter.format(deltas.latest*deltaFix) +
                    " load=" +
                    decFormatter.format(loads.average * timeFix) +
                    " min=" +
                    decFormatter.format(loads.min * timeFix) +
                    " max=" +
                    decFormatter.format(loads.max * timeFix) +
                    " ratio= "+
                    decFormatter.format((10000* loads.latest * timeFix)/(deltas.latest*deltaFix))+
                    " %100");
        }
        time.reset();
        loads.reset();
//            System.out.println(name +
//                    "Delta="+
//                    (int)(deltas.latest*deltaFix) +
//                    " load=" +
//                    (int)(loads.latest * timeFix) +
//                    " min=" +
//                    (int)(loads.min * timeFix) +
//                    " max=" +
//                    (int)(loads.max * timeFix) +
//                    " ratio= "+
//                    ((100* loads.latest * timeFix)/(deltas.latest*deltaFix))+
//                    "%");
    }

}
