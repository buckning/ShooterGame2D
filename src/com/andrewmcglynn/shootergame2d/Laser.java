package com.andrewmcglynn.shootergame2d;

public class Laser extends Actor {
    protected static final int BULLET_SPEED = 3;
    int vx, vy;

    public Laser(Stage stage) {
        super(stage);
        setSpriteNames(new String[]{"bullet0.gif"});
        setFrameSpeed(10);
        vx = 0;
        vy = BULLET_SPEED;
    }

    public void act() {
        super.act();
        y += vy;
        x += vx;
        if (y > stage.PLAY_HEIGHT || x > Stage.WIDTH || x < 0)
            remove();
    }

    public void setVx(int VX) {
        vx = VX;
        if (vx == 0) {
            setSpriteNames(new String[]{"bullet0.gif"});
        }
    }

    public void setVy(int i) {
        this.vy = i;
        if (vy == 0) {
            setSpriteNames(new String[]{"bullet.gif"});
        }
    }
}
