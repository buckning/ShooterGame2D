package com.andrewmcglynn.shootergame2d;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class SpriteCache extends ResourceCache {
    public BufferedImage getSprite(String name) {
        return (BufferedImage) getResource(name);
    }

    protected Object loadResource(URL url) {
        try {
            return ImageIO.read(url);
        } catch (Exception e) {
            System.out.println("No such file exists: " + url);
            return null;
        }

    }
}
