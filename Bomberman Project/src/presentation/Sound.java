package presentation;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

/**
 * Class that allows playing various sound effects in the game for a better user experience.
 */
public class Sound implements Serializable {
    private final URL[] sounds;
    private transient Clip clip = null;

    public Sound() {
        this.sounds = new URL[30];
        loadSounds();
    }

    private void loadSounds() {
        sounds[0] = getUrl("menuSound");
        sounds[1] = getUrl("deathSound");
        sounds[2] = getUrl("gameSound");
        sounds[3] = getUrl("startGameSound");
        sounds[4] = getUrl("powerUpSound");
    }

    private URL getUrl(String audioName) {
        return getClass().getResource("/res/sounds/" + audioName + ".wav");
    }

    public void getSounds(TypeOfSound s) {
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(sounds[s.getSoundIndex()]);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        if (clip == null) {
            return;
        }
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if (clip == null) {
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
            clip.stop();
            return;
        }
        clip.stop();
    }

    public void playEffect(TypeOfSound sounds) {
        getSounds(sounds);
        play();
    }

    public boolean isFinished() {
        if (clip == null) {
            return true;
        }
        return !clip.isRunning();
    }
}
