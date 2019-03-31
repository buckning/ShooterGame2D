package com.andrewmcglynn.shootergame2d;

public class Nuke extends Actor {
    protected static final int BULLET_SPEED = 1;
    int vx = 0;
    int shields = 100;

    public Nuke(Stage stage) {
        super(stage);
        setSpriteNames(new String[]{"Nuke0.gif", "Nuke1.gif", "Nuke2.gif", "Nuke3.gif"});
    }

    public void act() {
        super.act();
        y += BULLET_SPEED;
        x += vx;
        //if the nuke hits the ground
        if (y >= Stage.PLAYER_YPOSITION + this.getHeight()) {
            remove();
            BlueExplosion e = new BlueExplosion(stage);
            stage.addActor(e);
            e.setX(this.getX() - this.getWidth() * 2);
            e.setY(this.getY() - this.getHeight());
        }
    }

    public void collision(Actor a) {
        if (a instanceof Bomb) {
            shields -= 20;
            a.remove();
            Explosion e = new Explosion(stage);
            stage.addActor(e);
            e.setX(this.getX() - this.getWidth() * 2);
            e.setY(this.getY() + this.getHeight());
            if (shields <= 0) {
                stage.getPlayer().addScore(500);
                remove();
                BlueExplosion be = new BlueExplosion(stage);
                stage.addActor(be);
                be.setX(this.getX() - this.getWidth() * 2);
                be.setY(this.getY() - this.getHeight());
            }
        }

    }
}
