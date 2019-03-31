package com.andrewmcglynn.shootergame2d;

public class Wolverine extends Actor {
    protected static final double ATTACK_FREQUENCY = 0.05;

    int vx = 0, vy = 0;
    int shields = 1000;
    private boolean startingUp;
    private boolean walking, attacking;

    public Wolverine(Stage stage) {
        super(stage);
        this.setSpriteNames(new String[]{"wolverineFalling.gif"});
        vy = 4;
        x = Stage.WIDTH / 4;
        startingUp = true;
        attacking = false;
    }

    public void act() {
        super.act();
        y += vy;
        x += vx;
        //if he hits the ground
        if (y + getHeight() > Stage.PLAYER_YPOSITION + getHeight() && startingUp == true) {
            vy = 0;
            y = Stage.PLAYER_YPOSITION;
            this.setSpriteNames(new String[]{"wolverineStartup0.gif", "wolverineStartup1.gif", "wolverineStartup2.gif",
                    "wolverineStartup3.gif", "wolverineStartup4.gif", "wolverineStartup5.gif", "wolverineStartup6.gif",
                    "wolverineStartup7.gif", "wolverineStartup8.gif", "wolverineStartup9.gif", "wolverineStartup10.gif",
                    "wolverineStartup11.gif", "wolverineStartup12.gif", "wolverineStartup13.gif", "wolverineStartup14.gif",
                    "wolverineStartup15.gif", "wolverineStartup16.gif", "wolverineStartup17.gif"});
            setFrameSpeed(10);
        }

        if (currentFrame == 16) {
            y = Stage.PLAYER_YPOSITION + 30;
        }
        if (currentFrame == 17) {
            setCurrentFrame(0);
            this.setSpriteNames(new String[]{"wolverineStartup17.gif"});
            y = Stage.PLAYER_YPOSITION + 30;
            startingUp = false;
        }

        if (walking) {
            if (x + getWidth() >= stage.getPlayer().getX() && x <= stage.getPlayer().getX() + stage.getPlayer().getWidth()) {
                vx = 0;
                walking = false;
                setCurrentFrame(0);
                attacking = true;
                if (x + getWidth() >= stage.getPlayer().getX())
                    setSpriteNames(new String[]{"wolverineAttackLeft0.gif", "wolverineAttackLeft1.gif", "wolverineAttackLeft2.gif", "wolverineAttackLeft3.gif",
                            "wolverineAttackLeft4.gif", "wolverineAttackLeft5.gif"});
                else if (x <= stage.getPlayer().getX() + stage.getPlayer().getWidth())
                    setSpriteNames(new String[]{"wolverineAttackRight0.gif", "wolverineAttackRight1.gif",
                            "wolverineAttackRight2.gif", "wolverineAttackRight3.gif", "wolverineAttackRight4.gif", "wolverineAttackRight5.gif"});
                y = Stage.PLAYER_YPOSITION;
            }
        }
        //this is to make sure wolverine hits the player when he attacks
        if (attacking) {
            if (x > stage.getPlayer().getX()) {
                vx = -2;
            }
            if (x == stage.getPlayer().getX() + stage.getPlayer().getWidth() - 10) {
                vx = 0;
            }
            if (x + getWidth() < stage.getPlayer().getX()) {
                vx = 2;
            }
            if (x + getWidth() == stage.getPlayer().getX()) {
                vx = 0;
            }
        }

        //make wolverine attack at a random stage
        if (Math.random() < ATTACK_FREQUENCY && startingUp == false)
            attack();
    }


    public void collision(Actor a) {
        if (a instanceof Bomb || a instanceof Asteroid) {
            if (!startingUp) shields -= 10;
            Explosion ex = new Explosion(stage);
            ex.setX(a.getX());
            ex.setY(a.getY());
            stage.addActor(ex);
            a.remove();

            if (a instanceof Bomb) stage.getPlayer().addScore(500);

            if (shields <= 0) {
                remove();

                BlueExplosion e = new BlueExplosion(stage);
                e.setX(x);
                e.setY(y - 20);
                stage.addActor(e);
                //add a death sequence
                NonInteractiveGraphic wolverineEndSequence = new NonInteractiveGraphic(stage);
                wolverineEndSequence.setSpriteNames(new String[]{"wolverineEnd0.gif", "wolverineEnd1.gif", "wolverineEnd2.gif",
                        "wolverineEnd3.gif", "wolverineEnd4.gif", "wolverineEnd5.gif", "wolverineEnd6.gif", "wolverineEnd7.gif"});
                wolverineEndSequence.setVx(-3);
                wolverineEndSequence.setFrameSpeed(10);
                wolverineEndSequence.setX(x);
                wolverineEndSequence.setY(y);
                stage.addActor(wolverineEndSequence);

            }
        }
        if (a instanceof Player) {
            if (attacking && currentFrame == 5) {
                stage.getPlayer().addShields(-40);
                setCurrentFrame(0);
                this.setSpriteNames(new String[]{"wolverineStartup17.gif"});
                y = Stage.PLAYER_YPOSITION + 30;
                attacking = false;
            }
        }
    }

    public void attack() {
        walking = true;
        if (stage.getPlayer().getX() > x + getWidth()) {
            //walk to x
            vx = 2;
            setY(Stage.PLAYER_YPOSITION - 10);
            setCurrentFrame(0);
            setSpriteNames(new String[]{"wolverineWalkRight0.gif", "wolverineWalkRight1.gif", "wolverineWalkRight2.gif"
                    , "wolverineWalkRight3.gif", "wolverineWalkRight4.gif", "wolverineWalkRight5.gif"});
        } else if (stage.getPlayer().getX() + stage.getPlayer().getWidth() < x) {
            vx = -2;
            setY(Stage.PLAYER_YPOSITION - 10);
            setCurrentFrame(0);
            setSpriteNames(new String[]{"wolverineWalkLeft0.gif", "wolverineWalkLeft1.gif", "wolverineWalkLeft2.gif",
                    "wolverineWalkLeft3.gif", "wolverineWalkLeft4.gif", "wolverineWalkLeft5.gif"});

        }
    }
}
