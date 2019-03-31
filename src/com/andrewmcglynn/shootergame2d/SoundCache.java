package com.andrewmcglynn.shootergame2d;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class SoundCache extends ResourceCache {
    Thread song;
    String previousName;

    protected Object loadResource(URL url) {
        return Applet.newAudioClip(url);
    }

    public AudioClip getAudioClip(String name) {
        return (AudioClip) getResource(name);
    }

    public void playSound(final String name) {
        new Thread(
                new Runnable() {
                    public void run() {
                        getAudioClip(name).play();
                    }
                }
        ).start();
    }

    public void loopSound(final String name) {
        if (song != null) getAudioClip(previousName).stop();
        song = new Thread(new Runnable() {
            public void run() {
                getAudioClip(name).loop();
            }
        });
        song.start();
        previousName = name;
    }


}
