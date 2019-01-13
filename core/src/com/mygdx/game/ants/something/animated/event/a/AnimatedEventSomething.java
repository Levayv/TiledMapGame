package com.mygdx.game.ants.something.animated.event.a;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.world.WorldResAnimManager;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.ants.something.animated.a.MyAnimation;
import com.mygdx.game.enums.entity.Entity;
import com.mygdx.game.enums.entity.EntityAnimation;
public class AnimatedEventSomething extends Something {
    int stateCount;
    public MyAnimation animation; // can be manipulated by children only
    protected EntityAnimation entityAnim;
    public AnimatedEventSomething(Entity entity,
                                  EntityAnimation entityAnim,
                                  int stateCount) {
        super(entity);
        this.entityAnim = entityAnim;
        this.stateCount = stateCount;
        animation = new MyAnimation(true);
    }

    public void setAnim(WorldResAnimManager animManager){
        animation.setCoreAnimation(animManager.getAnimationByID(this.entityAnim));
//        coreFrameDur = 0.015f; // 60 fps !
    }
    @Override
    public void act(float delta) {
        texReg = animation.updateFrame(delta);
//        if (loopingEndless || startAnimCycle){
//            setVisible(false);
//        }else
//            setVisible(true);
    }
    @Override
    public void draw (Batch batch, float parentAlpha) {
            batch.draw(texReg, getX(), getY());
    }
}
