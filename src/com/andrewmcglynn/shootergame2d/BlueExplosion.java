package com.andrewmcglynn.shootergame2d;

public class BlueExplosion extends Actor {
    private String[] explosion = {"blueExpl0.gif", "blueExpl1.gif", "blueExpl2.gif", "blueExpl3.gif", "blueExpl4.gif", "blueExpl5.gif", "blueExpl6.gif"
            , "blueExpl7.gif", "blueExpl8.gif", "blueExpl9.gif", "blueExpl10.gif", "blueExpl11.gif"};

    public BlueExplosion(Stage stage) {
        super(stage);
        setSpriteNames(explosion);
        setFrameSpeed(5);
        stage.getSoundCache().playSound("bomb.wav");
    }

    public void act() {
        super.act();
        if (currentFrame == explosion.length - 1) {
            remove();
        }
    }
}
