package Main;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound implements Runnable {

        Clip clip;
        URL soundURL[] = new URL[30];

    public Sound() {

            soundURL[0] = getClass().getResource("/sound/StartGame.wav");
            soundURL[1] = getClass().getResource("/sound/press.wav");
            soundURL[2] = getClass().getResource("/sound/step.wav");
            soundURL[3] = getClass().getResource("/sound/boom.wav");
            soundURL[4] = getClass().getResource("/Res/sound/eat_bombs.wav");
            soundURL[5] = getClass().getResource("/Res/sound/eat_flame.wav");
            soundURL[6] = getClass().getResource("/Res/sound/eat_speed.wav");
            soundURL[7] = getClass().getResource("/Res/sound/eat_crate.wav");
            soundURL[8] = getClass().getResource("/Res/sound/shooting.wav");
            soundURL[9] = getClass().getResource("/Res/sound/boom_time.wav");
            soundURL[10] = getClass().getResource("/Res/sound/die.wav");

        }
        public void setFile ( int i){
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
                clip = AudioSystem.getClip();
                clip.open(ais);

            } catch (Exception e) {

            }

        }
        public void play () {
            clip.start();
        }
        public void loop () {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        public void stop () {
            clip.stop();
        }

    @Override
    public void run() {

    }
}

