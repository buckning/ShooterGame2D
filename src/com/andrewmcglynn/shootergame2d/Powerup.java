package com.andrewmcglynn.shootergame2d;

public class Powerup extends Actor {
    int vy, vx;

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

    public Powerup(Stage stage) {
        super(stage);
        vy = 3;
        vx = 0;
        setSpriteNames(new String[]{"atom.gif"});
    }

    public void act() {
        super.act();
        y += vy;
        x += vx;
        if (x < 0 || y > Stage.HEIGHT) {
            remove();
        }
    }
}
