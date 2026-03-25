import com.je.core.JeLib;
import com.je.io.IOUtils;
import com.je.media.Sound;
import org.junit.Test;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class SoundTest {
    @Test
    public void sfx() throws Throwable {
        try (AudioInputStream audioInputStream = IOUtils.loadAudioInputStream("/sfx.wav", SoundTest.class)) {
            JeLib.console().log("Playing sfx.");
            Sound.playSFX(audioInputStream);
            JeLib.sleep(1_000);
        } catch (IOException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }

        AudioInputStream audioInputStream = IOUtils.loadAudioInputStream("/sfx.wav", SoundTest.class);
        JeLib.console().log("Playing sfx again.");
        Sound.playSFX(audioInputStream);
        JeLib.sleep(1_000);
    }

    @Test
    public void music() throws Throwable {
        JeLib.console().log("Playing music 1.");
        AudioInputStream audioInputStream1 = IOUtils.loadAudioInputStream("/music1.wav", SoundTest.class);
        Sound.playMusic(audioInputStream1);
        JeLib.sleep(4_000);

        JeLib.console().log("Playing music 2.");
        AudioInputStream audioInputStream2 = IOUtils.loadAudioInputStream("/music2.wav", SoundTest.class);
        Sound.playMusic(audioInputStream2);
        JeLib.console().log("Waiting 6 seconds so music repeats at least 1 time.");
        JeLib.sleep(6_000);
    }
}
