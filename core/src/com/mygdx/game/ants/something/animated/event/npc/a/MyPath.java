package com.mygdx.game.ants.something.animated.event.npc.a;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

public class MyPath {
    float speed = 0.15f;
    float current = 0;
    public CatmullRomSpline<Vector2> myCatmull;
    private int k ; //increase k for more fidelity to the spline
    public Vector2[] points  ;
    private Vector2 first;
    private Vector2 second;
    private float timeDecimalPart;
    private float place;
    public Vector2[] findSpline(Vector2[] binarPath , int argK){
        this.k = argK;
//        CatmullRomSpline<Vector2> myCatmull = new CatmullRomSpline<Vector2>(dataSet, true);
//        Vector2 out = new Vector2();
//        myCatmull.valueAt(out, t);
//        myCatmull.derivativeAt(out, t);

        /*members*/
//        k = 10000; //increase k for more fidelity to the spline
        points = new Vector2[k];
//        Vector2[] dataSet = {
//                new Vector2(50,150), new Vector2(50,150),
//                new Vector2(400,400), new Vector2(600,150), new Vector2(700,400),
//                new Vector2(860,150), new Vector2(860,150)
//        };
        myCatmull = new CatmullRomSpline<Vector2>(binarPath, false);
        for(int i = 0; i < k; ++i)
        {
            points[i] = new Vector2();
            myCatmull.valueAt(points[i], ((float)i)/((float)k-1));
        }
        return points;
    }
    public boolean nextDot(float delta){
        current += delta * speed;
        if(current >= 1)
            current -= 1;
        place = current * k;
        first = points[(int)place];
        if(((int)place+1) < k)
        {
            second = points[(int)place+1];
        }
        else
        {
            second = points[0]; //or finish, in case it does not loop.
            return true; // stop
        }
        timeDecimalPart = place - ((int)place); //the decimal part of place
        return false; // don't stop
    }
    float getNextX(){
        return first.x + (second.x - first.x) * timeDecimalPart;
    }
    float getNextY(){
        return first.y + (second.y - first.y) * timeDecimalPart;
    }
}
