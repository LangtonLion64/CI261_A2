//package com.group.game.utility;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.group.game.bodies.BonusSprite;
import com.group.game.bodies.PowerUpSprite;
import com.group.game.bodies.PlayerCharacter;

//import static com.group.game.utility.Constants.CLAMP_HEIGHT;
//import static com.group.game.utility.Constants.MAX_BONUS_SPRITES;
//import static com.group.game.utility.Constants.MAX_POS_TO_NEXT_BONUS;
//import static com.group.game.utility.Constants.MAX_TIME_TO_NEXT_BONUS;
//import static com.group.game.utility.Constants.MIN_POS_TO_NEXT_BONUS;
//import static com.group.game.utility.Constants.MIN_TIME_TO_NEXT_BONUS;
//import static com.group.game.utility.Constants.POWERUP_VALUE;
//import static com.group.game.utility.Constants.POWER_UP_ATLAS_PATH;
import static com.group.game.utility.Constants.TINY;

/**
 * Created by gerard on 29/04/2017.
 */

/*public class BonusManager {
    PowerUpSprite[] bonusCollection = new PowerUpSprite[MAX_BONUS_SPRITES];
    PlayerCharacter playerCharacter;
    private int bonusSpriteToDisplay;
    private float timeCount;
    private float timeToNextBonus = MAX_TIME_TO_NEXT_BONUS;
    public static boolean handlingCollision=false;
    private Vector2 previousBonusPosition;
    private float flipX = -3;

    public BonusManager(PlayerCharacter playerCharacter){
        this.playerCharacter = playerCharacter;
        for(int i=0;i<bonusCollection.length;i++){
            bonusCollection[i] = new PowerUpSprite(POWER_UP_ATLAS_PATH,TINY);
        }
    }

    public void draw(SpriteBatch batch){
        for(int i=0;i<bonusCollection.length;i++){
            if(bonusCollection[i].isDisplayed()){
                bonusCollection[i].draw(batch);
            }
        }
    }

    public void update(float stateTime) {
        for(PowerUpSprite ps : bonusCollection){
            ps.update(stateTime);
            if(ps.isDisplayed() && ! handlingCollision){
                if(Intersector.overlaps(ps.getBoundingRectangle(),
                        playerCharacter.getBoundingRectangle())){
                    handlingCollision=true;
                    GameData.getInstance().addScore(POWERUP_VALUE);
                    ps.bonusRoutine();
                }
            }
        }
        nextBonusTimer(stateTime);
    }

    public void positionNextBonus() {
        while (bonusCollection[bonusSpriteToDisplay].isDisplayed()) {
            bonusSpriteToDisplay = (bonusSpriteToDisplay + 1) % bonusCollection.length;
        }
        bonusCollection[bonusSpriteToDisplay].setDisplayed(true);
        bonusCollection[bonusSpriteToDisplay].startRoutine(calcNextPos());
    }


    private Vector2 calcNextPos(){
        Vector2 tempPos = playerCharacter.getWorldPosition();
        Vector2 position = new Vector2(tempPos);
        position.x -= MIN_POS_TO_NEXT_BONUS +(float)
                (Math.random() * (MAX_POS_TO_NEXT_BONUS));
        position.y += MIN_POS_TO_NEXT_BONUS +(float)
                (Math.random() * (MAX_POS_TO_NEXT_BONUS));
        //clamp Y Axis
        position.y=clamp(position.y,tempPos.y,CLAMP_HEIGHT);
        //Unclutter X Axis
        if(previousBonusPosition != null){
            if(checkX(position.x)){
                flipX = flipX  * -flipX;
                position.x+=flipX;
                position.y+=3;
            }
        }
        previousBonusPosition = position;
        return position;
    }


    private void nextBonusTimer(float dt) {
        timeCount += dt;
        if (timeCount >= 1) {
            if (timeToNextBonus > 0) {
                timeToNextBonus--;
            } else {
                positionNextBonus();
                timeToNextBonus = (int)MIN_TIME_TO_NEXT_BONUS +(int)
                        (Math.random() * (MAX_TIME_TO_NEXT_BONUS - MIN_TIME_TO_NEXT_BONUS));
            }
            timeCount = 0;
        }
    }

    private float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    private boolean checkX(float x){
        if(x> previousBonusPosition.x && x <previousBonusPosition.x + 1
                || x< previousBonusPosition.x && x > previousBonusPosition.x -1)
            return true;
        return false;
    }
}*/