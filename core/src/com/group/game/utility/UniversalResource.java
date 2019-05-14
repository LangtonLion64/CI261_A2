package com.group.game.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.HashMap;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class UniversalResource {
    private TweenManager tweenManager;
    private HashMap<String,Sound> noises;
    private BitmapFont font;
    private String screenText ="";

    private static UniversalResource instance;

    public static UniversalResource getInstance(){
        if(instance==null){
            instance= new UniversalResource();
        }
        return instance;
    }

    private UniversalResource(){
        configureTween();
        configureSounds();
        configureFont();
    }

    private void configureFont(){
        font = new BitmapFont(Gdx.files.internal(Constants.fontPath));
    }

    public BitmapFont getFont(){
        return font;
    }

    public void setScreenText(String txt){
        screenText = txt;
        font.getData().setScale(0.2f, 0.2f); //set font scale
    }

    public String getScreenText(){
        return screenText;
    }

    private void configureTween(){
        Tween.setCombinedAttributesLimit(4);
        tweenManager = new TweenManager();
        Tween.registerAccessor(TweenData.class,
                new TweenDataAccessor());
    }

    public TweenManager getTweenManager(){
        return tweenManager;
    }

    public HashMap getNoises(){
        return noises;
    }


    private void configureSounds(){
        noises = new HashMap<String,Sound>();
        for(String key : Constants.LEVEL_SOUNDS.keySet()) {
            noises.put(key,Gdx.audio.newSound(Gdx.files.internal(Constants.LEVEL_SOUNDS.get(key))));
        }
    }
}
