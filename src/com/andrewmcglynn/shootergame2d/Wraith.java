package com.andrewmcglynn.shootergame2d;

public class Wraith extends Actor {
    protected int vx;
    protected static final double ATTACK_FREQUENCY = 0.005;
    protected static final int ATTACK_TIME = 50;
    protected static final double TELEPORT_FREQUENCY = 0.005;
    protected static final int TELEPORT_TIME = 30;
    private boolean teleporting, attacking;
    private int teleportTime = TELEPORT_TIME;
    private int attack_time = ATTACK_TIME;
    private int health = 100;

    public Wraith(Stage stage) {
        super(stage);
        setCurrentFrame(0);
        setSpriteNames(new String[]{"wraith0.gif", "wraith1.gif", "wraith2.gif", "wraith3.gif", "wraith4.gif", "wraith5.gif", "wraith6.gif", "wraith0.gif"});//,"wraith8.gif","wraith9.gif","wraith10.gif",});
        setFrameSpeed(15);
        stage.getSoundCache().playSound("MonsterScream.wav");
        teleport();
    }

    public void act() {
        super.act();
        if (health >= 0) {
            x += vx;
            if (x < 0 || (x > Stage.WIDTH - this.getWidth()))
                vx = -vx;
            if (Math.random() < ATTACK_FREQUENCY)
                attack();
            if (teleporting) {
                if (teleportTime-- == 0) {
                    teleporting = false;
                    setCurrentFrame(0);
                    setSpriteNames(new String[]{"wraith0.gif", "wraith1.gif", "wraith2.gif", "wraith3.gif", "wraith4.gif", "wraith5.gif", "wraith6.gif", "wraith0.gif"});//,"wraith8.gif","wraith9.gif","wraith10.gif",});
                    setFrameSpeed(15);
                }
            }
            if (attacking) {
                if (attack_time-- == 0) {
                    attacking = false;
                }
            }
        } else {
            BlueExplosion be = new BlueExplosion(stage);
            stage.addActor(be);
            NonInteractiveGraphic[] e = new NonInteractiveGraphic[5];
            for (int i = 0; i < e.length; i++) {
                e[i] = new NonInteractiveGraphic(stage);
                if (i % 2 == 0) e[i].setVx(i);
                else e[i].setVx(-i * 10);
                e[i].setX(this.getX());
                e[i].setY(this.getY());
                e[i].setVy(15);
                e[i].setSpriteNames(new String[]{"wraithEnd" + i + (".gif")});
                stage.addActor(e[i]);
            }
            this.remove();
            stage.getPlayer().addScore(1000);
            ExtraLife l = new ExtraLife(stage);
            stage.addActor(l);
        }
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void collision(Actor a) {
        if (a instanceof Bomb) {

            Explosion e = new Explosion(stage);
            e.setX(x);
            e.setY(y);
            stage.addActor(e);
            attacking = false;
            teleportTime = TELEPORT_TIME;
            health += -10;
            teleport();

        }
        if (a instanceof Bullet) {

            attacking = false;
            teleportTime = TELEPORT_TIME;
            health += -5;
            teleport();
        }
        if (a instanceof Player) {
            teleport();
            attacking = false;
            stage.getPlayer().addShields(-10);
        }
    }

    public void teleport() {
        teleporting = true;
        setCurrentFrame(0);
        setSpriteNames(new String[]{"wraithTeleport0.gif", "wraithTeleport1.gif"});
        this.setX((int) (Math.random() * (Stage.WIDTH - getWidth())));
        vx = 0;
        setY(20);
    }

    public void teleport(int x, int y) {
        teleporting = true;
        if (x > stage.getPlayer().getX() + stage.getPlayer().getWidth() + 50 || x < stage.getPlayer().getX() - 50) {
            this.setX(x);
            this.setY(y);
            setCurrentFrame(0);
            setSpriteNames(new String[]{"wraithTeleport0.gif", "wraithTeleport1.gif"});
        } else {
            teleport();
        }


    }

    public void attack() {
        attacking = true;
        teleport((int) (Math.random() * (Stage.WIDTH - getWidth())), stage.getPlayer().getY());
        setSpriteNames(new String[]{"wraith8.gif", "wraith9.gif"});
//		stage.getSoundCache().playSound("MonsterScream.wav");	

        if (stage.getPlayer().getX() > this.getX()) vx = 1;
        else vx = -1;
    }
}

