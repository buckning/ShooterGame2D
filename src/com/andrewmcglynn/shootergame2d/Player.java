package com.andrewmcglynn.shootergame2d;

import java.awt.event.KeyEvent;

public class Player extends Actor {
    public static final int MAX_SHIELDS = 200;
    public static final int MAX_JUMP_HEIGHT = 30;
    public static final int MAX_BOMBS = 5;
    protected int vx;
    protected int vy;
    protected static final int PLAYER_SPEED = 4;
    private boolean up, left, right, facingLeft, down;
    private int clusterBombs;
    private boolean space;
    private int shields;
    private int score;
    private int invincibleTime = 0;
    private int noOfLives = 3;
    private boolean jumping = false;

    public Player(Stage stage) {
        super(stage);
        setCurrentFrame(0);
        setSpriteNames(new String[]{"jimIdle0.gif"});
        clusterBombs = MAX_BOMBS;
        shields = MAX_SHIELDS;
        score = 0;
        vx = 0;
        y = Stage.PLAYER_YPOSITION;
    }

    public void act() {
        super.act();
        if (invincibleTime != 0) invincibleTime--;
        x += vx;

        y += vy;

        if (x < 0)
            x = 0;
        if (x > Stage.WIDTH - getWidth())
            x = Stage.WIDTH - width;
        if (jumping) {
            if (y <= Stage.PLAYER_YPOSITION - getHeight()) {
                vy += 1;
                setCurrentFrame(0);
                setSpriteNames(new String[]{"intro0.gif"});
            }

            if (y > Stage.PLAYER_YPOSITION) {
                y = Stage.PLAYER_YPOSITION;
                vy = 0;
                jumping = false;
                setCurrentFrame(0);
                setSpriteNames(new String[]{"jimRunRight0.gif"});
            }


        }
        if (stage.isOnBonusLevel()) {
            if (y < 0)
                y = 0;
            if (y > Stage.PLAY_HEIGHT - getHeight())
                y = Stage.PLAY_HEIGHT - getHeight();
        }
    }

    protected void updateSpeed() {
        vx = 0;
        if (stage.isOnBonusLevel()) vy = 0;
        if (left && space) vx = 0;
        else if (right && space) vx = 0;
        else if (left) vx = -PLAYER_SPEED;
        else if (right) vx = PLAYER_SPEED;
        else if (up && stage.isOnBonusLevel()) vy = -PLAYER_SPEED;
        else if (down && stage.isOnBonusLevel()) vy = PLAYER_SPEED;

    }

    protected void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {

            case KeyEvent.VK_RIGHT:
                right = false;

                facingLeft = false;
                if (!stage.isOnBonusLevel()) {
                    setCurrentFrame(0);

                    setSpriteNames(new String[]{"jimRunRight0.gif"});
                    setFrameSpeed(2);
                } else {
                    setFrameSpeed(1);

                    setCurrentFrame(0);

                    setSpriteNames(new String[]{"playerRocket.gif"});
                }
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                facingLeft = true;
                if (!stage.isOnBonusLevel()) {
                    setFrameSpeed(1);

                    setCurrentFrame(0);

                    setSpriteNames(new String[]{"jimRunLeft0.gif"});
                } else {
                    setFrameSpeed(1);

                    setCurrentFrame(0);

                    setSpriteNames(new String[]{"playerRocketLeft.gif"});
                }
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_SPACE:
                space = false;
                if (!stage.isOnBonusLevel()) {
                    setCurrentFrame(0);

                    if (facingLeft) setSpriteNames(new String[]{"jimRunLeft0.gif"});
                    else setSpriteNames(new String[]{"jimRunRight0.gif"});
                }
                break;
            case KeyEvent.VK_UP:
                up = false;
                setCurrentFrame(0);
                break;
        }
        updateSpeed();
    }

    protected void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_CONTROL:
                if (!stage.isOnBonusLevel()) {
                    if (!jumping) {
                        jumping = true;
                        vy = -10;
                    }
                }
                break;

            case KeyEvent.VK_UP:
                up = true;
                setCurrentFrame(0);
                break;
            case KeyEvent.VK_RIGHT:
                right = true;

                if (!stage.isOnBonusLevel()) {
                    setSpriteNames(new String[]{"jimRunRight0.gif", "jimRunRight1.gif", "jimRunRight2.gif",
                            "jimRunRight3.gif", "jimRunRight4.gif", "jimRunRight5.gif",
                            "jimRunRight6.gif", "jimRunRight7.gif", "jimRunRight8.gif",
                            "jimRunRight9.gif", "jimRunRight10.gif", "jimRunRight11.gif"
                            , "jimRunRight12.gif"});
                    setFrameSpeed(10);
                } else {
                    setFrameSpeed(1);

                    setCurrentFrame(0);

                    setSpriteNames(new String[]{"playerRocket.gif"});
                }
                break;
            case KeyEvent.VK_LEFT:
                left = true;

                if (!stage.isOnBonusLevel()) {
                    setSpriteNames(new String[]{"jimRunLeft0.gif", "jimRunLeft1.gif", "jimRunLeft2.gif", "jimRunLeft3.gif",
                            "jimRunLeft4.gif", "jimRunLeft5.gif", "jimRunLeft6.gif", "jimRunLeft7.gif", "jimRunLeft8.gif",
                            "jimRunLeft9.gif", "jimRunLeft10.gif", "jimRunLeft11.gif", "jimRunLeft12.gif"});
                    setFrameSpeed(10);
                } else {
                    setFrameSpeed(1);

                    setCurrentFrame(0);

                    setSpriteNames(new String[]{"playerRocketLeft.gif"});
                }
                break;
            case KeyEvent.VK_SPACE:
                space = true;
                fire();
                break;

        }
        updateSpeed();
    }

    public void fire() {
        setCurrentFrame(0);
        if (!stage.isOnBonusLevel()) {
            if (up) {
                Bomb b = new Bomb(stage, Bomb.UP, this.getX(), this.getY());
                b.setX(x + getWidth() / 4);    // + getWidth/4 is is to align the bullet with the image of the gun
                b.setY(y - b.getHeight());
                stage.addActor(b);
                setCurrentFrame(0);
                setSpriteNames(new String[]{"jimShoot0.gif", "jimShoot1.gif", "jimShoot2.gif", "jimShoot3.gif"});

            } else if (facingLeft) {
                setCurrentFrame(0);
                setSpriteNames(new String[]{"jimShootLeft0.gif", "jimShootLeft1.gif"});
                Bomb b1 = new Bomb(stage, Bomb.LEFT, this.getX(), y + getHeight() / 2);
                stage.addActor(b1);


            } else {
                setCurrentFrame(0);
                setSpriteNames(new String[]{"jimShootRight0.gif", "jimShootRight1.gif"});

                Bomb b1 = new Bomb(stage, Bomb.RIGHT, this.getX() + getWidth(), y + getHeight() / 2);
                stage.addActor(b1);
            }
        } else {
            if (facingLeft) {
                Bomb b1 = new Bomb(stage, Bomb.LEFT, this.getX(), y + getHeight() / 2);
                stage.addActor(b1);


            } else {
                Bomb b1 = new Bomb(stage, Bomb.RIGHT, this.getX() + getWidth(), y + getHeight() / 2);
                stage.addActor(b1);
            }
        }
        stage.getSoundCache().playSound("missile.wav");
    }

    public void collision(Actor a) {
        if (a instanceof Wraith) {
            if (invincibleTime == 0) addShields(-40);
            invincibleTime = 10;
        }
        if (a instanceof Trooper) {
            if (invincibleTime == 0) {
                addShields(-20);
                invincibleTime = 100;
            }
        }
        if (a instanceof Laser) {
            if (invincibleTime == 0) addShields(-10);
            invincibleTime = 10;
            a.remove();
        }
        if (a instanceof Asteroid) {
            if (invincibleTime == 0) addShields(-20);
            invincibleTime = 10;
            Explosion e = new Explosion(stage);
            e.setX(a.getX());
            e.setY(a.getY());
            stage.addActor(e);
            a.remove();
        }
        if (a instanceof BlueExplosion) {
            if (invincibleTime == 0) addShields(-20);
            invincibleTime = 10;

        }
        if (a instanceof Powerup) {
            if (invincibleTime == 0) addShields(40);
            invincibleTime = 10;
            a.remove();
            addScore(20);
        }
        if (a instanceof ExtraLife) {
            a.remove();
            noOfLives++;
            addScore(100);
        }
        if (shields == 0 && noOfLives == 0) {
            stage.gameOver();
        }
    }

    public void addScore(int i) {
        score += i;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int i) {
        score = i;
    }

    public void addShields(int i) {
        if (shields + i < 0) {
            shields = 0;
            if (noOfLives >= 0) {
                noOfLives--;
                setShields(MAX_SHIELDS);
            }
            if (noOfLives == -1) {
                stage.gameOver();
                System.out.println("Game Over");
            }
        } else {
            shields += i;
            if (shields > MAX_SHIELDS) {
                shields = MAX_SHIELDS;
            }
        }
    }

    public int getShields() {
        return shields;
    }

    public void setShields(int i) {
        shields = i;
    }

    public int getClusterBombs() {
        return clusterBombs;
    }

    public void setClusterBombs(int i) {
        clusterBombs = i;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int i) {
        vx = i;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int i) {
        vy = i;
    }

    public int getNoOfLives() {
        return noOfLives;
    }

    public void setNoOfLives(int i) {
        noOfLives = i;
    }
}
