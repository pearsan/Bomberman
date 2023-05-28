package Graphics;

import Controller.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite {

    private BufferedImage SPRITESHEET = null;
    private BufferedImage[][] spriteArray;
    private final int TILE_SIZE = 32;
    public int w;
    public int h;
    private int wSprite;
    private int hSprite;

    public Sprite(String file) {
        w = TILE_SIZE;
        h = TILE_SIZE;

        System.out.println("Loading: " + file + "...");
        SPRITESHEET = loadSprite(file);

        wSprite = SPRITESHEET.getWidth() / w;
        hSprite = SPRITESHEET.getHeight() / h;
        loadSpriteArray();
    }

    public Sprite(String file, int w, int h) {
        this.w = w;
        this.h = h;

        SPRITESHEET = loadSprite(file);

        wSprite = SPRITESHEET.getWidth() / w;
        hSprite = SPRITESHEET.getHeight() / h;
        loadSpriteArray();
    }

    public Sprite(BufferedImage image, int w, int h) {
        this.w = w;
        this.h = h;

        SPRITESHEET = image;

        wSprite = SPRITESHEET.getWidth() / w;
        hSprite = SPRITESHEET.getHeight() / h;
        loadSpriteArray();
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(int i) {
        w = i;
        wSprite = SPRITESHEET.getWidth() / w;
    }

    public void setHeight(int i) {
        h = i;
        hSprite = SPRITESHEET.getHeight() / h;
    }

    private BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        BufferedImageLoader loader = new BufferedImageLoader();
        sprite = loader.loadImage(file);
        return sprite;
    }

    public void loadSpriteArray() {
        spriteArray = new BufferedImage[hSprite][wSprite];

        for (int y = 0; y < hSprite; y++) {
            for (int x = 0; x < wSprite; x++) {
                spriteArray[y][x] = getSprite(x, y);
            }
        }
    }

    public BufferedImage getSprite(int x, int y) {
        return SPRITESHEET.getSubimage(x * w, y * h, w, h);
    }

    public BufferedImage getSPRITESHEET() { return SPRITESHEET; }

    public BufferedImage[] getSpriteArray(int i) {
        return spriteArray[i];
    }

    public BufferedImage[] getSpriteArray() {
        int count = 0;
        BufferedImage[] image = new BufferedImage[hSprite * wSprite];
        for (int y = 0; y < hSprite; y++) {
            for (int x = 0; x < wSprite; x++) {
                image[count] = spriteArray[y][x];
                count++;
            }
        }
        return image;
    }

    public BufferedImage[][] getSpriteArray2() {
        return spriteArray;
    }

    public static void drawArray(Graphics2D g, ArrayList<BufferedImage> img, int x, int y, int width, int height, int xOffset, int yOffset) {
        for (int i = 0; i < img.size(); i++) {
            if (img.get(i) != null) {
                g.drawImage(img.get(i), x, y, width, height, null);
            }
            x += xOffset;
            y += yOffset;
        }
    }

    public static void drawArray(Graphics2D g, Font f, String word, int x, int y, int width, int height, int xOffset, int yOffset) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != 32) {
                g.drawImage(f.getFont(word.charAt(i)), x, y, width, height, null);
                x += xOffset;
                y += yOffset;
            }

        }
    }
}
