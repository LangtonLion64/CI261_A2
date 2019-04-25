package com.group.game.bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.group.game.utility.Constants;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Comparator;

import static com.group.game.utility.Constants.BADGE_ATLAS;
import static com.group.game.utility.Constants.COLLISION_RECT_HEIGHT;
import static com.group.game.utility.Constants.COLLISION_RECT_WIDTH;
import static com.group.game.utility.Constants.FRAME_DURATION;
import static com.group.game.utility.Constants.PLAYER_HEIGHT;
import static com.group.game.utility.Constants.PLAYER_WIDTH;
import static com.group.game.utility.Constants.X;
import static com.group.game.utility.Constants.Y;


/**
 * Created by gerard on 09/11/2016.
 * updated 02/03/18
 */

public abstract class AnimatedSprite extends Sprite {
    protected Animation animation;
    protected Animation.PlayMode playmode;
    private TextureAtlas atlas;
    private Rectangle collisionRectangle;

    public AnimatedSprite(String atlasString, Texture t, Vector2 pos){
        super(t,PLAYER_WIDTH,PLAYER_HEIGHT);
        this.setX(pos.x);
        this.setY(pos.y);
        playmode = Animation.PlayMode.NORMAL;
        initAtlas(atlasString);
        createCollisionRect();
        Array<TextureAtlas.AtlasRegion> regions = new
                Array<TextureAtlas.AtlasRegion>(atlas.getRegions());
        regions.sort(new RegionComparator());
    }

    public void setAnimation(Animation.PlayMode mode){
        animation.setPlayMode(mode);
    }

    public void createCollisionRect(){
        this.collisionRectangle = new Rectangle(this.getX(),this.getY(), COLLISION_RECT_WIDTH, COLLISION_RECT_HEIGHT);
    }

    public void updateCollisionData() {
        collisionRectangle.setX(this.getX());
        collisionRectangle.setY(this.getY());
    }

    private Rectangle getCollisionRectangle() { return collisionRectangle; }

    public void update(float animationTime){
        this.setRegion((TextureRegion) animation.getKeyFrame(animationTime));
        updateCollisionData();
    }

    private void initAtlas(String atlasString){
        atlas = new TextureAtlas(Gdx.files.internal(atlasString));
        //load animations
        Array<TextureAtlas.AtlasRegion> regions = new
                Array<TextureAtlas.AtlasRegion>(atlas.getRegions());
        regions.sort(new RegionComparator());
        animation = new Animation(FRAME_DURATION,regions, Animation.PlayMode.NORMAL);
    }

    private static class RegionComparator implements Comparator<TextureAtlas.AtlasRegion> {
        @Override
        public int compare(TextureAtlas.AtlasRegion region1, TextureAtlas.AtlasRegion region2) {
            return region1.name.compareTo(region2.name);
        }
    }

}