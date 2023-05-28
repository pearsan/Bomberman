package Graphics;

import java.awt.image.BufferedImage;

public class Animation {

    private BufferedImage[] frames;
    private int currentFrame;
    private int numFrame;

    private int count;
    private int delay;

    private int timesPlayed;
    public Animation(BufferedImage[] frames) {
        timesPlayed = 0;
        setFrames(frames);
    }

    public Animation() {
        timesPlayed = 0;
    }

    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        currentFrame = 0;
        count = 0;
        timesPlayed = 0;
        delay = 2;
        numFrame = frames.length;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setNumFrame(int numFrame) {
        this.numFrame = numFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public void update() {
        if (delay == -1) return;

        count++;

        if (count == delay) {
            currentFrame++;
            count = 0;
        }

        if (currentFrame == numFrame) {
            currentFrame = 0;
            timesPlayed++;
        }
    }

    public int getDelay() {
        return delay;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public int getCount() {
        return count;
    }

    public BufferedImage getFrames() {
        return frames[currentFrame];
    }

    public boolean hasPlayedOnce() {
        return timesPlayed > 0;
    }

    public boolean hasPlayed(int i) {
        return timesPlayed == i;
    }
}