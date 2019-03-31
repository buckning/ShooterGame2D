package com.andrewmcglynn.shootergame2d;

public class Monster extends Actor {
    protected int vx;
    protected static final double FIRING_FREQUENCY = 0.001;
    protected boolean endingSequenceEnabled = false;
    protected String[] endingSequence = {"exp0.gif", "exp1.gif", "exp2.gif", "exp3.gif", "exp4.gif", "exp5.gif", "exp6.gif", "exp7.gif", "exp8.gif", "exp9.gif", "exp10.gif", "exp11.gif", "exp12.gif", "exp13.gif", "exp14.gif", "exp15.gif"};

    public Monster(Stage stage) {
        super(stage);
        setSpriteNames(new String[]{"bicho.gif"});
        setFrameSpeed(35);
    }

    public void act() {
        super.act();
        x += vx;
        if (x < 0 || x > Stage.WIDTH)
            vx = -vx;
        if (Math.random() < FIRING_FREQUENCY)
            fire();
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void collision(Actor a) {
        if (a instanceof Bullet || a instanceof Bomb) {
            remove();
        }
    }

    public void spawn() {
        Monster m = new Monster(stage);
        m.setX((int) (Math.random() * Stage.WIDTH));
        m.setY((int) (Math.random() * Stage.HEIGHT / 2));
        m.setVx((int) (Math.random() * 20 - 10));
        stage.addActor(m);
    }

    public void fire() {
        Laser m = new Laser(stage);
        m.setX(x + getWidth() / 2);
        m.setY(y + getHeight());
        stage.addActor(m);
        stage.getSoundCache().playSound("photon.wav");
    }
}
