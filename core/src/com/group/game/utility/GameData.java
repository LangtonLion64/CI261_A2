package com.group.game.utility;

import com.badlogic.gdx.Gdx;

/**
 * Created by gerard on 23/04/2017.
 */

public class GameData {
    private float time;
    private int score;
    private String playerName;
    private static GameData INSTANCE;

    private GameData(){}

    public static GameData getInstance(){
        if(INSTANCE==null){
            return new GameData();
        }
        return INSTANCE;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time=time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int s){
        this.score = this.score + s;
        Gdx.app.log("TAG", "SCORE = " + this.score);
    }

    public void resetGameData(){
        time=0;
        score=0;
        playerName="";
    }
}
