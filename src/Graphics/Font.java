package Graphics;

import Controller.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Font {

        private BufferedImage FONTSHEET = null;
        private BufferedImage[][] spriteArray;
        private final int TILE_SIZE = 32;
        public int w;
        public int h;
        private int wLetter;
        private int hLetter;

        public Font(String file) {
            w = TILE_SIZE;
            h = TILE_SIZE;

            System.out.println("Loading: " + file + "...");
            FONTSHEET = loadFont(file);

            wLetter = FONTSHEET.getWidth() / w;
            hLetter = FONTSHEET.getHeight() / h;
            loadSpriteArray();
        }

        public Font(String file, int w, int h) {
            this.w = w;
            this.h = h;

            System.out.println("Loading: " + file + "...");
            FONTSHEET = loadFont(file);

            wLetter = FONTSHEET.getWidth() / w;
            hLetter = FONTSHEET.getHeight() / h;
            loadSpriteArray();
        }

        public void setSize(int width, int height) {
            setWidth(width);
            setHeight(height);
        }

        public void setWidth(int i) {
            w = i;
            wLetter = FONTSHEET.getWidth() / w;
        }

        public void setHeight(int i) {
            h = i;
            hLetter = FONTSHEET.getHeight() / h;
        }

        private BufferedImage loadFont(String file) {
            BufferedImage sprite = null;
            BufferedImageLoader loader = new BufferedImageLoader();
            sprite = loader.loadImage(file);
            return sprite;
        }

        public void loadSpriteArray() {
            spriteArray = new BufferedImage[hLetter][wLetter];

            for (int y = 0; y < hLetter; y++) {
                for (int x = 0; x < wLetter; x++) {
                    spriteArray[y][x] = getLetter(x, y);
                }
            }
        }

        public BufferedImage getLetter(int x, int y) {
            return FONTSHEET.getSubimage(x * w, y * h, w, h);
        }

        public BufferedImage getFONTSHEET() { return FONTSHEET; }

        public BufferedImage getFont(char letter) {
            int value = letter + 11;
            if ((value % 11 >=5 && value % 11 <= 10)) {
                value -= 11;
            }
            if ( value == 'w' || value == 'u' || value == 's') {
                value += 11;
            }

            int x = value % wLetter;
            int y = value / hLetter;

            return getLetter(x, y);
        }

        public static void  drawArray(Graphics2D g, ArrayList<BufferedImage> img, int x, int y, int width, int height, int xOffset, int yOffset) {
            for (int i = 0; i < img.size(); i++) {
                if (img.get(i) != null) {
                    g.drawImage(img.get(i), x, y, width, height, null);
                }
                x += xOffset;
                y += yOffset;
            }
        }

        public static void  drawArray(Graphics2D g, Font f, String word, int x, int y, int width, int height, int xOffset, int yOffset) {
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) != 32) {
                    g.drawImage(f.getFont(word.charAt(i)), x, y, width, height, null);
                }
                x += xOffset;
                y += yOffset;
            }
        }

}
