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
    private Sound CoinCollect = Gdx.audio.newSound(Gdx.files.internal("sfx/CoinCollect.wav"));

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
        GameScreen.handleCollision = false;
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
        Tween.to(tweenData, TweenDataAccessor.TYPE_ROTATION,2000f)
                .target(360).start(tweenManager);
        Tween.to(tweenData, TweenDataAccessor.TYPE_SCALE, 2000f)
                .target(0f).start(tweenManager)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        setAnimation(Animation.PlayMode.NORMAL);
                        CoinCollect.play();
                        Gdx.app.log("TAG", "Testerino");
                    }
                })
                .start(tweenManager);
    }

    public void BadgeCollected(){
        Timeline.createSequence()
                .push(Tween.to(tweenData, TweenDataAccessor.TYPE_SCALE, 1000f)
                        .target(1.5f)
                        .delay(1000)).start(tweenManager)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        setAnimation(Animation.PlayMode.LOOP);
                        update(2);
                        closeBadgeCollected();
                    }
                });
    }
    public void BadgeWaiting()
    {
        Timeline.createSequence()
                .push(Tween.to(tweenData,TweenDataAccessor.TYPE_POS, 100f)
                        .target(getX(), 10)
                        .repeat(10, 100f)
                        .delay(200f)).start(tweenManager)
                .setCallback(new TweenCallback()
                {
                    @Override
                    public void onEvent(int type, BaseTween<?> source)
                    {
                        setAnimation(Animation.PlayMode.LOOP);
                        update(2);
                        BadgeCollected();
                    }
                });

    }

}

