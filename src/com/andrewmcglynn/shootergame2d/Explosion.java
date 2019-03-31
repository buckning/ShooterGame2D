package com.andrewmcglynn.shootergame2d;

public class Explosion extends Actor {
    private String[] explosion = {"exp0.gif", "exp1.gif", "exp2.gif", "exp3.gif", "exp4.gif", "exp5.gif", "exp6.gif", "exp7.gif", "exp8.gif"
            , "exp9.gif", "exp10.gif", "exp11.gif", "exp12.gif", "exp13.gif", "exp14.gif", "exp15.gif"};

    public Explosion(Stage stage) {
        super(stage);
        setSpriteNames(explosion);
        setFrameSpeed(5);
        stage.getSoundCache().playSound("explosion.wav");
    }

    public void act() {
        super.act();
        if (currentFrame == explosion.length - 1) {
            remove();
        }
    }

}
