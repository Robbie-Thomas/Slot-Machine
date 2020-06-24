import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;

public class sound
{

    public static synchronized void play(final String fileName) {

        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(fileName));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                System.out.println("play sound error: " + e.getMessage() + " for " + fileName);
            }
        }).start();
    }
}

