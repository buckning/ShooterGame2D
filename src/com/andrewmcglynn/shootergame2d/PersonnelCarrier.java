package com.andrewmcglynn.shootergame2d;

public class PersonnelCarrier extends Actor {
    private int shields = 100;
    protected boolean endingSequenceEnabled = false;
    protected String[] endingSequence = {"blueExpl0.gif", "blueExpl1.gif", "blueExpl2.gif", "blueExpl3.gif", "blueExpl4.gif", "blueExpl5.gif", "blueExpl6.gif"
            , "blueExpl7.gif", "blueExpl8.gif", "blueExpl9.gif", "blueExpl10.gif", "blueExpl11.gif"};
    protected static final int SPEED = 3;
    int numOfTroopers = 6;

    public PersonnelCarrier(Stage stage) {
        super(stage);
        setSpriteNames(new String[]{"harrier.gif"});
        setFrameSpeed(5);
        y = 50;
        x = -getWidth() * 8;        //this is to allow for the sound to match the animation
        stage.getSoundCache().playSound("f16.wav");
    }


    public void act() {
        super.act();


        if (!endingSequenceEnabled) {
            x += SPEED;
            if (x >= 0 && x < Stage.WIDTH) {
                if (x % 50 == 0) {
                    if (numOfTroopers > 0) {
                        numOfTroopers--;
                        Trooper t = new Trooper(stage);
                        stage.addActor(t);
                        t.setY(y);
                        t.setX(this.getX() + this.getWidth() / 2);
                    }

                }
            }
            if (getX() > Stage.WIDTH * 2) {
                remove();
            }
        } else {
            if (getCurrentFrame() == endingSequence.length - 1) {
                remove();
                spawn();
            }
        }
    }

    public void spawn() {
        PersonnelCarrier h = new PersonnelCarrier(stage);
        stage.addActor(h);
    }

    public void fire() {

        stage.getSoundCache().playSound("photon.wav");
    }

    public void collision(Actor a) {
        if (a instanceof Bullet || a instanceof Bomb) {
            a.remove();
            shields -= 10;
            stage.getSoundCache().playSound("explosion.wav");
            if (shields <= 0) {
                if (!endingSequenceEnabled) {
                    endingSequenceEnabled = true;
                    setSpriteNames(endingSequence);
                    setCurrentFrame(0);
                    setFrameSpeed(5);
                    stage.getPlayer().addScore(100);
                    stage.getSoundCache().playSound("explosion.wav");
                    Powerup p = new Powerup(stage);
                    stage.addActor(p);
                    p.setX(this.getX());
                    p.setY(this.getY() + this.getHeight());
                }
            }
        }
    }
}
