package com.andrewmcglynn.shootergame2d;

public class TruckShooter extends Actor {
    protected static final int BULLET_SPEED = 1;
    protected static final double FIRING_FREQUENCY = 0.005;

    int vx = 0, vy = 0;

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
        setCurrentFrame(0);
        if (vx > 0) {
            setSpriteNames(new String[]{"truckFacingLeft.gif"});
        } else {
            setSpriteNames(new String[]{"truck.gif"});
        }
    }

    int shields = 100;

    public TruckShooter(Stage stage) {
        super(stage);
        vx = 1;
        setSpriteNames(new String[]{"truckFacingLeft.gif"});

    }

    public void act() {
        super.act();
        x += vx;
        if (Math.random() < FIRING_FREQUENCY)
            fire();
        if (x > Stage.WIDTH * 2 || x < -200) {
            this.remove();
        }
    }


    public void collision(Actor a) {
        if (a instanceof Bomb || a instanceof Asteroid) {
            shields -= 10;
            a.remove();
            Explosion e = new Explosion(stage);
            e.setX(a.getX());
            e.setY(a.getY());
            stage.addActor(e);

            if (a instanceof Bomb) stage.getPlayer().addScore(500);

            if (shields <= 0) {

                BlueExplosion b = new BlueExplosion(stage);
                b.setX(x);
                b.setY(y);
                stage.addActor(b);
                remove();
            }
        }
    }

    public void fire() {
        Laser m = new Laser(stage);
        m.setX(x + getWidth() / 2);
        m.setY(y + getHeight() / 2);
        if (stage.getPlayer().getX() > x) {
            m.setVx(vx + 2);
        } else {
            m.setVx((vx * -1) - 4);
        }
        m.setVy(0);
        stage.addActor(m);
        stage.getSoundCache().playSound("photon.wav");

    }
}
