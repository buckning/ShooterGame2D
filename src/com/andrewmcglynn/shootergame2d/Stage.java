package com.andrewmcglynn.shootergame2d;

import java.awt.image.ImageObserver;

public interface Stage extends ImageObserver {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int SPEED = 10;
    public static final int PLAY_HEIGHT = 500;

    public SpriteCache getSpriteCache();

    public SoundCache getSoundCache();

    public void addActor(Actor a);

    public Player getPlayer();

    public void gameOver();

    public int getNumOfActors();

    public static final int HEIGHT_OFFSET = 200;
    public static final int MAX_METEORS_PER_LEVEL = 50;

    public int getLevel();

    public void setLevel(int i);

    public boolean isOnBonusLevel();

    public void setOnBonusLevel(boolean onBonusLevel);

    public static final int PLAYER_YPOSITION = 365;
}
