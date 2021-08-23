package muck.client;


import  java.io.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    private Clip forest;
    public Sound(String s) {
        try {
            AudioInputStream forest = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));
            AudioFormat baseFormat = forest.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(), false);

            AudioInputStream dforest = AudioSystem.getAudioInputStream(decodeFormat, forest);
            clip = AudioSystem.getClip();
            clip.open(dforest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    public void playSound() {
        if(clip == null) return;
        stop();
    }

    private void stop() {
        if (clip.isRunning()) clip.stop();
    }

    public void close() {
        stop();
        clip.close();
    }
}