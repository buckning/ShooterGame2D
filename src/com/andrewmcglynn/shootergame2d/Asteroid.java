package com.andrewmcglynn.shootergame2d;

public class Asteroid extends Actor {
    public static final int ASTEROID_SPEED = 3;
    private int vx, vy;

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

    public Asteroid(Stage stage) {
        super(stage);
        setSpriteNames(new String[]{"Asteroid.png"});
        vx = ASTEROID_SPEED;
        vy = ASTEROID_SPEED;
    }

    public void act() {
        super.act();
        y += vy;
        x -= vx;
        if (x < 0 || y > Stage.HEIGHT - Stage.HEIGHT_OFFSET) {
            if (y > Stage.HEIGHT - Stage.HEIGHT_OFFSET) {
                Explosion e = new Explosion(stage);
                e.setX(x - getWidth());
                e.setY(y);
                stage.addActor(e);
            }
            remove();

        }
    }

    public void collision(Actor a) {
        if (a instanceof Bullet || a instanceof Bomb) {
            remove();
            a.remove();
            Explosion e = new Explosion(stage);
            e.setX(x - getWidth());
            e.setY(y);
            stage.addActor(e);
            stage.getPlayer().addScore(5);
        }
        if (a instanceof Trooper) {
            remove();
            Explosion e = new Explosion(stage);
            e.setX(x - getWidth());
            e.setY(y);
            stage.addActor(e);
        }
    }
}

