package com.andrewmcglynn.shootergame2d;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Actor {
    protected int x, y;
    protected int width, height;
    protected String[] spriteNames;
    protected int currentFrame;
    protected int frameSpeed;
    protected int t;
    protected BufferedImage spriteSheet;
    protected Stage stage;
    protected SpriteCache spriteCache;
    protected boolean markedForRemoval;

    public Actor(Stage stage) {
        this.stage = stage;
        spriteCache = stage.getSpriteCache();
        currentFrame = 0;
        frameSpeed = 1;
        t = 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void collision(Actor a) {

    }

    public void remove() {
        markedForRemoval = true;
    }

    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }

    public void paint(Graphics2D g) {
        //there is next frame is null, reset the frame to the start
        if (currentFrame == spriteNames.length) {
            currentFrame = 0;
        }
        g.drawImage(spriteCache.getSprite(spriteNames[currentFrame]), x, y, stage);

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getFrameSpeed() {
        return frameSpeed;
    }

    public void setFrameSpeed(int i) {
        frameSpeed = i;
    }


    public String[] getSpriteNames() {
        return spriteNames;
    }

    public void setSpriteNames(String[] names) {
        spriteNames = names;
        height = 0;
        width = 0;
        for (int i = 0; i < names.length; i++) {
            BufferedImage image = spriteCache.getSprite(spriteNames[i]);
            height = Math.max(height, image.getHeight());
            width = Math.max(width, image.getWidth());
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void act() {
        t++;
        if (t % frameSpeed == 0) {
            t = 0;
            currentFrame = (currentFrame + 1) % spriteNames.length;
        }
    }

    public void setCurrentFrame(int frameNo) {
        currentFrame = frameNo;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

}
