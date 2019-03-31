package com.andrewmcglynn.shootergame2d;

public class Bomb extends Actor {

    public static final int UP_LEFT = 0;
    public static final int UP = 1;
    public static final int UP_RIGHT = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    protected static final int BOMB_SPEED = 8;
    protected int vx, vy;

    public Bomb(Stage stage, int heading, int x, int y) {
        super(stage);
        this.x = x;
        this.y = y;
        String sprite = "";
        switch (heading) {
            case UP_LEFT:
                vx = -BOMB_SPEED;
                vy = -BOMB_SPEED;
                sprite = "bombUL.gif";
                break;
            case UP:
                vx = 0;
                vy = -BOMB_SPEED;
                sprite = "bombU.gif";
                break;
            case UP_RIGHT:
                vx = BOMB_SPEED;
                vy = -BOMB_SPEED;
                sprite = "bombUR.gif";
                break;
            case LEFT:
                vx = -BOMB_SPEED;
                vy = 0;
                sprite = "bombL.gif";
                break;
            case RIGHT:
                vx = BOMB_SPEED;
                vy = 0;
                sprite = "bombR.gif";
                break;
        }
        setSpriteNames(new String[]{sprite});
    }

    public void act() {
        super.act();
        y += vy;
        x += vx;
        if (y < 0 || y > Stage.HEIGHT || x < 0 || x > Stage.WIDTH)
            remove();
    }
}
