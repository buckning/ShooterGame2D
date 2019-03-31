package com.andrewmcglynn.shootergame2d;

public class ExtraLife extends Actor {
    int vy, vx;


    public ExtraLife(Stage stage) {
        super(stage);
        vy = 3;
        vx = 0;
        setSpriteNames(new String[]{"life.gif"});
    }

    public void act() {
        super.act();
        y += vy;
        x += vx;
        if (y > Stage.HEIGHT) remove();
        if (x < 0) remove();
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

}
