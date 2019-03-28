package com.group.game.bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.audio.Sound;

import java.util.Comparator;
import java.util.HashMap;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.group.game.physics.WorldManager;
import com.group.game.utility.Constants;
import com.group.game.utility.TweenData;
import com.group.game.utility.TweenDataAccessor;
import com.group.game.utility.UniversalResource;

import static com.group.game.utility.Constants.DENSITY;
import static com.group.game.utility.Constants.FRICTION;
import static com.group.game.utility.Constants.RESTITUTION;

//The Sprite class is inherited from the GDX interface
public class PowerUpSprite extends AnimatedSprite {

    //Initialisation of the two different animation graphics, as well as the variable that contains the current animation used.
    private Animation star, explode, animation;
    //Two different atlases need to be used for both the star and smoke graphics.
    private TweenData tweenData;
    private TweenManager tweenManager;
    private HashMap<String, Sound> myNoises;
    private int ttl;
    private float timeCount;
    private boolean displayed = false;
    private TextureAtlas atlas1;
    private Body playerBody;

    public PowerUpSprite(String atlas, Texture t, Vector2 pos){
        super(atlas, t, pos);
        //this.setAlpha(0);
        atlas1 = new TextureAtlas(Gdx.files.internal(Constants.STAR_ATLAS));
        Array<TextureAtlas.AtlasRegion> regions_1 = new Array<TextureAtlas.AtlasRegion>(atlas1.getRegions());
        regions_1.sort(new RegionComparator());
        star = new Animation(Constants.FRAME_DURATION,regions_1,Animation.PlayMode.NORMAL);
        //Starts the animation off with the star graphic.
        animation = star;
        //Because the position is not included in the arguments of the class, it is set here
        this.setPosition(15,5);
        //Calls the event for initialisation of the tween's data
        initTweenData();
        myNoises = UniversalResource.getInstance().getNoises();
        buildBody();
    }

    public void buildBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(tweenData.getXY().x,tweenData.getXY().y);

        playerBody = WorldManager.getInstance().getWorld().createBody(bodyDef);
        playerBody.setUserData(this);
        playerBody.setFixedRotation(true);
        playerBody.createFixture(getFixtureDef(DENSITY,FRICTION,RESTITUTION));
    }

    public FixtureDef getFixtureDef(float density, float friction, float restitution) {
        //prepare for Fixture definition
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth()/2)-.75f,getHeight()/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution=restitution;
        return fixtureDef;
    }

    public void update(float deltaTime){
        this.setRegion((TextureRegion) animation.getKeyFrame(deltaTime));
        updateCollisionData();
        //Updates/syncs the position and size of the object with the tween's
        this.setPosition(tweenData.getXY().x,tweenData.getXY().y);
        this.setScale(tweenData.getScale());
        this.setRegion((TextureAtlas.AtlasRegion)animation.getKeyFrame(deltaTime));
    }

    public void startupRoutine(){
        //Alters the text message on the screen.
        myNoises.get("sndStar").play();
        this.setPosition(15,5);
        //Moves the star to a new position on the screen, which takes 500 frames to complete.
        Tween.to(tweenData, TweenDataAccessor.TYPE_POS,500f)
                .target(0,350).start(tweenManager)
                //After this is finished, it will perform the event contained with in onEvent.
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        //Sets the graphic used to the smoke graphic, and follows up with the endRoutine.
                        animation = explode;
                        endRoutine();
                    }
                })
                .start(tweenManager);
    }

    public void endRoutine(){
        //Alters the text message on the screen to the reflect the disappearance of the star.
        myNoises.get("sndGone").play();
        //After 100 frames, it changes the scale to 0 so that it cannot be seen
        Tween.to(tweenData, TweenDataAccessor.TYPE_SCALE,100f).delay(100f).target(0)
                .start(tweenManager);
    }

    private void initTweenData(){
        tweenData = new TweenData();
        tweenData.setXY(new Vector2(this.getX(),this.getY()));
        tweenData.setColor(this.getColor());
        tweenData.setScale(this.getScaleX());
        tweenManager = UniversalResource.getInstance().getTweenManager();
    }

    private static class RegionComparator implements Comparator<TextureAtlas.AtlasRegion> {
        @Override
        public int compare(TextureAtlas.AtlasRegion region_1, TextureAtlas.AtlasRegion region_2){
            return region_1.name.compareTo(region_2.name);
        }
    }
}
