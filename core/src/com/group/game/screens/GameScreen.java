package com.group.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.group.game.TBWGame;
import com.group.game.bodies.BonusSprite;
import com.group.game.bodies.PlayerCharacter;
import com.group.game.bodies.PowerUpSprite;
import com.group.game.physics.WorldManager;
import com.group.game.utility.CameraManager;
import com.group.game.utility.Constants;
import com.group.game.utility.GameData;
import com.group.game.utility.HUD;
import com.group.game.utility.UniversalResource;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.ArrayList;
import java.util.List;

import static com.group.game.utility.Constants.BADGE_ATLAS;
import static com.group.game.utility.Constants.BADGE_START_POSITION;
import static com.group.game.utility.Constants.MEDIUM;
import static com.group.game.utility.Constants.PLAYER_ATLAS_PATH;
import static com.group.game.utility.Constants.SMALL;
import static com.group.game.utility.Constants.START_POSITION;
import static com.group.game.utility.Constants.STAR_ATLAS;
import static com.group.game.utility.Constants.UNITSCALE;
import static com.group.game.utility.Constants.VIRTUAL_HEIGHT;
import static com.group.game.utility.Constants.VIRTUAL_WIDTH;
import static com.group.game.utility.Constants.X;
import static com.group.game.utility.Constants.Y;


/**
 * Created by gerard on 12/02/2017.
 */

public class GameScreen extends ScreenAdapter {
    private TBWGame game;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private PlayerCharacter smif;
    private BonusSprite badge;
    private PowerUpSprite powerup;
    private HUD gameHUD;
    private CameraManager cameraManager;
    private float frameDelta = 0;
    public static boolean handleCollision = false;

    public GameScreen(TBWGame tbwGame){
        this.game = tbwGame;
    }

    @Override
    public void resize(int width, int height) {
        game.camera.setToOrtho(false, VIRTUAL_WIDTH * UNITSCALE, VIRTUAL_HEIGHT * UNITSCALE);
        game.batch.setProjectionMatrix(game.camera.combined);
    }

    public void update(){
        if (!handleCollision){
            if (Intersector.overlaps(badge.getBoundingRectangle(), smif.getBoundingRectangle())){
                handleCollision = true;
                GameData.getInstance().addScore(10);
                HUD.addScore(10);
                badge.BadgeWaiting();
            }
        }
    }

    @Override
    public void show() {
        super.show();
        tiledMap = game.getAssetManager().get(Constants.BACKGROUND);
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(this.tiledMap,UNITSCALE);
        orthogonalTiledMapRenderer.setView(game.camera);
        if(!WorldManager.isInitialised()){WorldManager.initialise(game,tiledMap);}
        //player
        smif = new PlayerCharacter(PLAYER_ATLAS_PATH,SMALL,START_POSITION);
        badge = new BonusSprite(BADGE_ATLAS, MEDIUM, BADGE_START_POSITION);
        powerup = new PowerUpSprite(STAR_ATLAS,MEDIUM,new Vector2(5,5));
        //powerup.startupRoutine();
        cameraManager = new CameraManager(game.camera,tiledMap);
        cameraManager.setTarget(smif);
        gameHUD = new HUD(game.batch,smif,game);
    }

    @Override
    public void render(float delta) {
        frameDelta += delta;
        smif.update(frameDelta);
        powerup.update(frameDelta);
        badge.update(frameDelta);
        UniversalResource.getInstance().getTweenManager().update(frameDelta);
        gameHUD.update(delta);
        game.batch.setProjectionMatrix(game.camera.combined);
        clearScreen();
        draw();
        update();
        WorldManager.getInstance().doPhysicsStep(delta);

    }

    private void draw() {
       orthogonalTiledMapRenderer.setView(game.camera);
       orthogonalTiledMapRenderer.render();
        cameraManager.update();
        game.batch.begin();
        smif.draw(game.batch);
        powerup.draw(game.batch);
        badge.draw(game.batch);
        game.batch.end();
        gameHUD.stage.draw();
        WorldManager.getInstance().debugRender();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g,
                Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}