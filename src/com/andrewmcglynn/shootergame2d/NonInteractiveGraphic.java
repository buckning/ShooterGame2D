package com.andrewmcglynn.shootergame2d;

public class NonInteractiveGraphic extends Actor {
    int vx;
    int vy;

    public NonInteractiveGraphic(Stage stage) {
        super(stage);
        vx = 0;
        vy = 0;
    }

    public void act() {
        super.act();
        x += vx;
        y += vy;
        if (x > Stage.WIDTH || x < 0 || y > Stage.HEIGHT || y < 0) remove();
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }


}
