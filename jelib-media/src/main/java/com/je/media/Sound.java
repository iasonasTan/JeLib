package com.je.media;

import com.je.core.JeLib;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * Wraps {@code javax.sound} package and makes it easier to use.
 */
public final class Sound {
    /**
     * Music clip, stored so stop is possible.
     */
    private static Clip mMusicClip;

    /**
     * Plays given audio one time.
     * @param audioInputStream Audio to play as {@link AudioInputStream}.
     */
    public static void playSFX(AudioInputStream audioInputStream) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.addLineListener(lineEvent -> {
                if(lineEvent.getType()==LineEvent.Type.STOP &&
                        clip.getFramePosition() == clip.getFrameLength()) {
                    clip.close();
                }
            });
            clip.open(audioInputStream);
            if (clip.isRunning())
                clip.stop();
            clip.setFramePosition(0);
            clip.start();
        } catch (LineUnavailableException | IOException e) {
            JeLib.console().error("Cannot play audio input stream.");
            JeLib.console().exception(e);
        }
    }

    /**
     * Plays given audio continuously forever until method is
     * re-called with another audio.
     * @param audioInputStream Audio to play as {@link AudioInputStream}.
     */
    public static void playMusic(AudioInputStream audioInputStream) {
        try {
            if(mMusicClip!=null && mMusicClip.isRunning())
                mMusicClip.stop();
            mMusicClip = AudioSystem.getClip();
            mMusicClip.addLineListener(lineEvent -> {
                if(lineEvent.getType()==LineEvent.Type.STOP &&
                        mMusicClip.getFramePosition() == mMusicClip.getFrameLength()) {
                    mMusicClip.close();
                }
            });
            mMusicClip.open(audioInputStream);
            mMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
            mMusicClip.start();
        } catch (LineUnavailableException | IOException e) {
            JeLib.console().error("Cannot play audio input stream.");
            JeLib.console().exception(e);
        }
    }

    /**
     * Private constructor prevents instantiation.
     */
    private Sound() {}
}
