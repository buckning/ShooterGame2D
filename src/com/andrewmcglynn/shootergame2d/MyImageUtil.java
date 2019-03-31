package com.andrewmcglynn.shootergame2d;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.*;
import java.awt.AlphaComposite;
import java.awt.RenderingHints;

//import com.sun.image.codec.jpeg.JPEGImageEncoder;
//Ref: Code from http://www.javalobby.org/articles/ultimate-image

public class MyImageUtil {

    public static BufferedImage loadImage(String ref) {
        BufferedImage bimg = null;
        try {

            bimg = ImageIO.read(new File(ref));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bimg;
    }

    //~ public static void saveOldWay(String ref, BufferedImage img) {
    //~ BufferedOutputStream out;
    //~ try {
    //~ out = new BufferedOutputStream(new FileOutputStream(ref));
    //~ JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
    //~ JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(img);
    //~ int quality = 5;
    //~ quality = Math.max(0, Math.min(quality, 100));
    //~ param.setQuality((float) quality / 100.0f, false);
    //~ encoder.setJPEGEncodeParam(param);
    //~ encoder.encode(img);
    //~ out.close();
    //~ } catch (Exception e) {
    //~ e.printStackTrace();
    //~ }
    //~ }

    /**
     * Saves a BufferedImage to the given file, pathname must not have any
     * periods "." in it except for the one before the format, i.e. C:/images/fooimage.png
     *
     * @param img
     * @param saveFile
     */
    public static void saveImage(BufferedImage img, String ref) {
        try {
            String format = (ref.endsWith(".png")) ? "png" : "jpg";
            ImageIO.write(img, format, new File(ref));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage loadTranslucentImage(String url, float transperancy) {
        // Load the image  
        BufferedImage loaded = loadImage(url);
        // Create the image using the   
        BufferedImage aimg = new BufferedImage(loaded.getWidth(), loaded.getHeight(), BufferedImage.TRANSLUCENT);
        // Get the images graphics  
        Graphics2D g = aimg.createGraphics();
        // Set the Graphics composite to Alpha  
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transperancy));
        // Draw the LOADED img into the prepared reciver image  
        g.drawImage(loaded, null, 0, 0);
        // let go of all system resources in this Graphics  
        g.dispose();
        // Return the image  
        return aimg;
    }


    public static BufferedImage makeColorTransparent(String ref, Color color) {
        BufferedImage image = loadImage(ref);
        BufferedImage dimg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dimg.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(image, null, 0, 0);
        g.dispose();
        for (int i = 0; i < dimg.getHeight(); i++) {
            for (int j = 0; j < dimg.getWidth(); j++) {
                if (dimg.getRGB(j, i) == color.getRGB()) {
                    dimg.setRGB(j, i, 0x8F1C1C);
                }
            }
        }
        return dimg;
    }

    public static BufferedImage horizontalflip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();
        return dimg;
    }

    public static BufferedImage verticalflip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, img.getColorModel().getTransparency());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();
        return dimg;
    }


    public static BufferedImage rotate(BufferedImage img, int angle) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.rotate(Math.toRadians(angle), w / 2, h / 2);
        g.drawImage(img, null, 0, 0);
        return dimg;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }

    public static BufferedImage[] splitImage(BufferedImage img, int cols, int rows) {
        int w = img.getWidth() / cols;
        int h = img.getHeight() / rows;
        int num = 0;
        BufferedImage imgs[] = new BufferedImage[w * h];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                imgs[num] = new BufferedImage(w, h, img.getType());
                // Tell the graphics to draw only one block of the image
                Graphics2D g = imgs[num].createGraphics();
                g.drawImage(img, 0, 0, w, h, w * x, h * y, w * x + w, h * y + h, null);
                g.dispose();
                num++;
            }
        }
        return imgs;
    }


}//end class ImageUtil
