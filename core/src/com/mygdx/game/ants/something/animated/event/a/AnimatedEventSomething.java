package com.mygdx.game.ants.something.animated.event.a;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.enums.entity.EntityClass;
import com.mygdx.game.enums.entity.EntityTex;
import com.mygdx.game.world.WorldResAnimManager;
import com.mygdx.game.ants.something.a.Something;
import com.mygdx.game.ants.something.animated.a.MyAnimation;
import com.mygdx.game.enums.entity.EntityTex;
import com.mygdx.game.enums.entity.EntityAnimation;
public class AnimatedEventSomething extends Something {
    public MyAnimation animation; // can be manipulated by children only
//    protected EntityAnimation entityAnim;
    public AnimatedEventSomething() {
        super();
        animation = new MyAnimation(true);
    }
    @Override
    public void set0Entity(EntityClass entityClass) {
        super.set0Entity(entityClass);
    }
    @Override
    public void set01EntityTex(EntityTex entityTex) {
        super.set01EntityTex(entityTex);
    }
    @Override
    public void set02EntityAnim(EntityAnimation entityAnim) {
        super.set02EntityAnim(entityAnim);
    }

    public void setAnim(WorldResAnimManager animManager){
        animation.setCoreAnimation(animManager.getAnimationByID(this.entityAnim));
        animation.setToFirstFrame(); //otherwise texReg will be null

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
