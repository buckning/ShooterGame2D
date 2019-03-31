package com.andrewmcglynn.shootergame2d;

public class Bullet extends Actor {
    protected static final int BULLET_SPEED = 10;
    int vx = 0;

    public Bullet(Stage stage) {
        super(stage);
        setSpriteNames(new String[]{"misil.gif"});
    }

    public Bullet(Stage stage, int vx) {
        super(stage);
        if (vx < 0) setSpriteNames(new String[]{"misil1.gif"});
        else setSpriteNames(new String[]{"misil0.gif"});
        this.vx = vx;
    }

    public void act() {
        super.act();
        y -= BULLET_SPEED;
        x += vx;
        if (y < 0 || y > Stage.HEIGHT || x < 0 || x > Stage.WIDTH)
            remove();
    }
}
