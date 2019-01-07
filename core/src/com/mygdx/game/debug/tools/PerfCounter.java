package com.mygdx.game.debug.tools;

import com.badlogic.gdx.utils.PerformanceCounter;

import java.text.DecimalFormat;

public class PerfCounter extends PerformanceCounter { // todo remove extend add your own data
//    private final static float timeFix = 1f / 1000.0f;
    private final static float deltaFix = 1000000f ;
    private final static float currentFix = 1000f;
    public boolean debugging;

    private DecimalFormat deltaFormatter = new DecimalFormat("##,###");
    private DecimalFormat load_Formatter = new DecimalFormat("##0.00");
    public PerfCounter(ProfilerID profilerID, boolean debugging) {
        super("Profiler["+ profilerID +"]:");
        this.debugging = debugging;
    }
//    @Override
//    public void stop(){
//        super.stop();
//        System.out.println("f");
//    }
    public void stop(float exportNowGiveDelta){
        super.stop();
        if (debugging) System.out.println(export(exportNowGiveDelta));
    }
    public String export(float delta){
        return String.valueOf(name+
//                "[in MCR SEC] Delta:"+
                deltaFormatter.format(delta*deltaFix) +
                        " Task: " + deltaFormatter.format(current*deltaFix) +
                        " Load: " + load_Formatter.format(100*current/delta) + "%"
//                " Average: " + time.average*currentFix +
//                " AvrLoad: " + load.average*currentFix +
//                "% " +
        );
    }
}
