package com.group.game.utility;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.TweenAccessor;

public class TweenDataAccessor implements TweenAccessor<TweenData> {
    public static final int TYPE_POS=1;
    public static final int TYPE_SCALE = 2;
    public static final int TYPE_COLOUR = 3;
    public static final int TYPE_ROTATION = 4;

    @Override
    public int getValues(TweenData target, int tweenType, float[] returnValues) {
        switch(tweenType){
            case TYPE_POS:
                returnValues[0] = target.getXY().x;
                returnValues[1] = target.getXY().y;
                return 2;
            case TYPE_SCALE:
                returnValues[0] = target.getScale();
                return 2;
            case TYPE_COLOUR:
                returnValues[0] = target.getColor().r;
                returnValues[1] = target.getColor().g;
                returnValues[2] = target.getColor().b;
                returnValues[3] = target.getColor().a;
                return 3;
            case TYPE_ROTATION:
                returnValues[0] = target.getRotation();
                return 4;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(TweenData target, int tweenType, float[] newValues) {
        switch(tweenType) {
            case TYPE_POS:
                target.setXY(new Vector2(newValues[0], newValues[1]));
                break;
            case TYPE_SCALE:
                target.setScale(newValues[0]);
                break;
            case TYPE_COLOUR:
                Color c = target.getColor();
                c.set(newValues[0],newValues[1],newValues[2],newValues[3]);
                target.setColor(c);
                break;
            case TYPE_ROTATION:
                target.setRotation(newValues[0]);
            default:
                assert false;
        }
    }
}