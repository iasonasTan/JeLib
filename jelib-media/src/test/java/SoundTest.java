import com.je.core.JeLib;
import com.je.io.IOUtils;
import com.je.media.Sound;
import org.junit.Test;

import javax.sound.sampled.AudioInputStream;

public class SoundTest {
    @Test
    public void sfx() {
        AudioInputStream audioInputStream = IOUtils.loadAudioInputStream("/sfx.wav", SoundTest.class);

        Sound.playSFX(audioInputStream);
        JeLib.sleep(500);

        Sound.playSFX(audioInputStream);
        JeLib.sleep(1_000);
    }

    @Test
    public void music() {
        AudioInputStream audioInputStream1 = IOUtils.loadAudioInputStream("/music1.wav", SoundTest.class);
        Sound.playMusic(audioInputStream1);
        JeLib.sleep(4_000);

        AudioInputStream audioInputStream2 = IOUtils.loadAudioInputStream("/music2.wav", SoundTest.class);
        Sound.playMusic(audioInputStream2);
        JeLib.sleep(30_000);
    }
}
