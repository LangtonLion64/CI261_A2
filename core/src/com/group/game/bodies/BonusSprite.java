package com.group.game.bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.group.game.physics.WorldManager;
import com.group.game.screens.GameScreen;
import com.group.game.utility.TweenData;
import com.group.game.utility.TweenDataAccessor;
import com.group.game.utility.UniversalResource;

import java.util.Comparator;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import static com.group.game.utility.Constants.BADGE_ATLAS;
import static com.group.game.utility.Constants.DENSITY;
import static com.group.game.utility.Constants.FRICTION;
import static com.group.game.utility.Constants.RESTITUTION;

public class BonusSprite extends AnimatedSprite
{


    private TweenData tweenData;
    private TweenManager tweenManager;
    private Sound CoinCollect = Gdx.audio.newSound(Gdx.files.internal("sfx/CoinCollect.wav")); //locating the coin collect sound

    public BonusSprite(String atlasString, Texture t, Vector2 pos)
    {
        super(atlasString, t, pos);
        this.setPosition(pos.x,pos.y);
        initTweenData();
    }

    private void initTweenData()
    {
        tweenData = new TweenData();
        tweenData.setXY(new Vector2(this.getX(), this.getY()));
        tweenData.setColor(this.getColor());
        tweenData.setScale(this.getScaleX());
        tweenManager = UniversalResource.getInstance().getTweenManager();
        GameScreen.handleCollision = false; //sets handle collision to false
    }

    private TweenData getTweenData()
    {
        return tweenData;
    }

    @Override
    public void update(float stateTime)
    {
        super.update(stateTime);
        this.setX(tweenData.getXY().x);
        this.setY(tweenData.getXY().y);
        this.setColor(tweenData.getColor());
        this.setScale(tweenData.getScale());
        this.setRotation(tweenData.getRotation());
    }

    public void closeBadgeCollected(){
        Tween.to(tweenData, TweenDataAccessor.TYPE_ROTATION,2000f) //changes rotation over 2000 frames
                .target(360).start(tweenManager); //target to rotate 360 degrees
        Tween.to(tweenData, TweenDataAccessor.TYPE_SCALE, 2000f)//changes scale over 2000 frames
                .target(0f).start(tweenManager) //target to scale to 0
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        setAnimation(Animation.PlayMode.NORMAL);
                        CoinCollect.play(); //Plays the coin collected sound
                        UniversalResource.getInstance().setScreenText(""); //sets text to empty again
                    }
                })
                .start(tweenManager);
    }

    public void BadgeCollected(){
        Timeline.createSequence()
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_SCALE, 1000f) //changes the scale over a duration of 1000 frames
                        .target(1.5f) //the target size for the scale
                        .delay(1000)).start(tweenManager) //delay of 1000 frames then the call back
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        setAnimation(Animation.PlayMode.LOOP);
                        update(2);
                        closeBadgeCollected(); //Calls closeBadgeCollected function
                    }
                });
    }
    public void BadgeWaiting()
    {
        Timeline.createSequence()
                .push(Tween.to(tweenData,TweenDataAccessor.TYPE_POS, 100f)
                        .target(getX(), 10) //get the x position and I manually put the y
                        .repeat(10, 100f) //this repeats it 10 times with a delay 100 frames
                        .delay(200f)).start(tweenManager) //then a delay of 200 frames before running the callback
                .setCallback(new TweenCallback()
                {
                    @Override
                    public void onEvent(int type, BaseTween<?> source)
                    {
                        setAnimation(Animation.PlayMode.LOOP);
                        update(2);
                        BadgeCollected();//calls the badge collected function
                    }
                });
    }

}

