package com.andrewmcglynn.shootergame2d;

public class Trooper extends Actor {
    protected static final int BULLET_SPEED = 1;
    protected static final double FIRING_FREQUENCY = 0.001;

    int vx = 0, vy = 0;
    int shields = 20;

    public Trooper(Stage stage) {
        super(stage);
        this.setSpriteNames(new String[]{"trooper.gif"});
        vy = 1;
    }

    public void act() {
        super.act();
        y += vy;
        //if the trooper hits the ground
        if (y >= Stage.PLAYER_YPOSITION + getHeight()) {
            vy = 0;
            if (stage.getPlayer().getX() > x) setSpriteNames(new String[]{"trooperIdleFacingLeft.gif"});
            else setSpriteNames(new String[]{"trooperIdle.gif"});
            if (x < 0 || x > Stage.WIDTH)
                vx = -vx;
            if (Math.random() < FIRING_FREQUENCY)
                fire();
        } else {
            if (Math.random() < FIRING_FREQUENCY)
                fire();
        }

        if (y > Stage.HEIGHT) {
            this.remove();
        }
    }


    public void collision(Actor a) {
        if (a instanceof Bomb || a instanceof Asteroid) {
            shields -= 10;
            a.remove();
            if (a instanceof Bomb) stage.getPlayer().addScore(500);

            if (shields <= 0) {
                remove();
                Explosion e = new Explosion(stage);
                stage.addActor(e);
                e.setX(this.getX() + this.getWidth() / 3);
                e.setY(this.getY());
            }
        }
    }

    public void fire() {
        if (y >= Stage.PLAYER_YPOSITION + getHeight()) {
            Laser m = new Laser(stage);
            m.setX(x + getWidth() / 2);
            m.setY(y + getHeight() / 2);
            if (stage.getPlayer().getX() > x) {
                m.setVx(1);
            } else {
                m.setVx(-1);
            }
            m.setVy(0);
            stage.addActor(m);
            stage.getSoundCache().playSound("photon.wav");

        } else {
            Laser m = new Laser(stage);
            m.setX(x + getWidth() / 2);
            m.setY(y + getHeight() / 2);
            m.setVx(0);
            m.setVy(2);
            stage.addActor(m);
            stage.getSoundCache().playSound("photon.wav");

        }
    }
}
