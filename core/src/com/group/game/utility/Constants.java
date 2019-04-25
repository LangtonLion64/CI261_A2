package com.group.game.utility;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gerard on 09/11/2016.
 * Updated 17/02/18
 */

public class Constants {


    //Screen Size
    public static final float VIRTUAL_WIDTH = Gdx.graphics.getWidth();
    public static final float VIRTUAL_HEIGHT = Gdx.graphics.getHeight();

    //World to screen scale
    public static final float TILE_SIZE   = 32;
    public static final float UNITSCALE = 1.0f / TILE_SIZE;

    //Animation Speed
    public static final float FRAME_DURATION = 1.0f / 30.0f;
    public static final float TIME_STEP=1/60f;
    public static final int LEVEL_TIME = 30;

    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;

    public static final String BACKGROUND = "tileData/assignment_two.tmx";
    public static final String PHYSICS_MATERIALS_PATH = "tileData/physicsData.json";

    public static final float DENSITY=.5f;
    public static final float FRICTION=.5f;
    public static final float RESTITUTION=.5f;

    public static final float COLLISION_RECT_WIDTH=50;
    public static final float COLLISION_RECT_HEIGHT=50;

    //impulse strength
    public static final float FORCE_X=30f;
    public static final float FORCE_Y=30f;

    //Speed
    public static final float MAX_VELOCITY = 1f;
    public static final float MAX_HEIGHT = 18;

    //player body
    public static int PLAYER_WIDTH= 3;
    public static int PLAYER_HEIGHT=4;
    public static float PLAYER_OFFSET_Y=2.15f;
    public static float PLAYER_OFFSET_X=1.5f;

    //player graphics
    public static final String PLAYER_ATLAS_PATH = "atlas/smurf_assets.atlas";
    public static final Texture MEDIUM = new Texture(Gdx.files.internal("gfx/mediumSize.png"));
    public static final Texture SMALL = new Texture(Gdx.files.internal("gfx/smallSize.png"));
    public static final Texture TINY = new Texture(Gdx.files.internal("gfx/tinySize.png"));

    //player start position
    public static final Vector2 START_POSITION = new Vector2(10,10);

    public static int X = 20;
    public static int Y = 5;
    public static final Vector2 BADGE_START_POSITION = new Vector2(X,Y);

    public static final String WesternFont = "font/Western.fnt";
    public static final String BADGE_ATLAS = "atlas/badge_assets.atlas";

    public static final String STAR_ATLAS = "atlas/star.atlas";
    public static final String fontPath = "font/stencil.fnt";

    public static final Map<String, String> LEVEL_SOUNDS =
            Collections.unmodifiableMap(new HashMap<String, String>() {
                {
                    put("sndStar", "sfx/sndStar.ogg");
                    put("sndGone", "sfx/sndGone.ogg");
                    put("sndBump", "sfx/sndBump.ogg");
                }
            });

}
