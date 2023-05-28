package Main;

import Controller.BufferedImageLoader;
import Controller.UtilityTool;
import Graphics.Font;
import Graphics.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    public Font font;
    public int commandNum = 0;
    BufferedImage hud;

    public UI(GamePanel gp, BufferedImage hud) {
        this.gp = gp;
        font = new Font("../../Res/font.png", 10, 10);
        this.hud = hud;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        //Title State
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        //play state
        if (gp.gameState == gp.playState) {
            g2.drawImage(hud, 64 * 10, 0, null);
            Sprite.drawArray(g2, font, gp.ddMinute + ":" + gp.ddSecond , gp.maxScreenCol  * gp.tileSize + 32, 32 , 32, 32, 32, 0);
            Sprite.drawArray(g2, font, "X" + gp.player.getPlayerLives() + "" , (gp.maxScreenCol + 2)  * gp.tileSize, 64 * 2 - 32 , 64, 64, 64, 0);
            Sprite.drawArray(g2, font, "^CO]E" , (gp.maxScreenCol + 1)  * gp.tileSize - 32, 64 * 4 - 32 , 32, 32, 32, 0);
            DecimalFormat dFormat = new DecimalFormat("000000");
            gp.ddScore = dFormat.format(gp.getScore());
            Sprite.drawArray(g2, font, gp.ddScore , (gp.maxScreenCol + 1)  * gp.tileSize - 32, 64 * 5 - 32 , 32, 32, 32, 0);

        }
        //
        if (gp.gameState == gp.chooseState) {
            drawChooseState();
        }
        //Pause state
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        //lose state;
        if (gp.gameState == gp.loseState) {
            drawLoseScreen();
        }

        if (gp.gameState == gp.winState) {
            drawWinState();
        }
    }

    public void drawPauseScreen() {
        String text = "PAU^E";
        Sprite.drawArray(g2, font, text , 3 * gp.tileSize, 3 * gp.tileSize , 64, 64, 64, 0);
        if (commandNum == 0) {
            Sprite.drawArray(g2,font, ">", 3 * gp.tileSize + 32, 6 * gp.tileSize, 32, 32, 32, 0);
        }
        Sprite.drawArray(g2, font, "]E^UME" , 4 * gp.tileSize, 6 * gp.tileSize , 32, 32, 32, 0);
        if (commandNum == 1) {
            Sprite.drawArray(g2,font, ">", 3 * gp.tileSize + 32, 7 * gp.tileSize, 32, 32, 32, 0);
        }
        Sprite.drawArray(g2, font, "]E^TA]T" , 4 * gp.tileSize, 7 * gp.tileSize , 32, 32, 32, 0);
        if (commandNum == 2) {
            Sprite.drawArray(g2,font, ">", 3 * gp.tileSize + 32, 8 * gp.tileSize, 32, 32, 32, 0);
        }
        Sprite.drawArray(g2, font, "MENU" , 4 * gp.tileSize, 8 * gp.tileSize , 32, 32, 32, 0);

    }

    public void drawTitleScreen() {
        UtilityTool utool = new UtilityTool();

        //Back ground
        g2.setColor(new Color(69, 195, 253));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        //title
        BufferedImage image;
        BufferedImageLoader loader = new BufferedImageLoader();
        image = loader.loadImage("../../Res/2.png");
        image = utool.scaleImage(image, gp.screenWidth + 64 * 4, gp.screenHeight);
        g2.drawImage(image, 0, 0, null);

        //Menu
        Sprite.drawArray(g2, font, "NEW RAME" , 3 * gp.tileSize, 6 * gp.tileSize , 32, 32, 32, 0);
        if (commandNum == 0) {
            Sprite.drawArray(g2,font, ">", 3 * gp.tileSize - 32, 6 * gp.tileSize, 32, 32, 32, 0);
        }
        Sprite.drawArray(g2, font, "LOAD RAME", 3 * gp.tileSize, 7 * gp.tileSize , 32, 32, 32, 0);
        if (commandNum == 1) {
            Sprite.drawArray(g2,font, ">", 3 * gp.tileSize - 32, 7 * gp.tileSize, 32, 32, 32, 0);
        }

        Sprite.drawArray(g2, font, "QUIT" , 3 * gp.tileSize, 8 * gp.tileSize , 32, 32, 32, 0);

        if (commandNum == 2) {
            Sprite.drawArray(g2,font, ">", 3 * gp.tileSize - 32, 8 * gp.tileSize, 32, 32, 32, 0);
        }


    }

    public void drawLoseScreen() {
        if(gp.lose) {
            g2.setColor(Color.black);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            Sprite.drawArray(g2, font,"You" , gp.tileSize, 3*gp.tileSize , 64, 64, 64, 0);
            Sprite.drawArray(g2, font,"lose" , gp.tileSize*5, 3*gp.tileSize , 64, 64, 64, 0);

            if (commandNum == 0) {
                Sprite.drawArray(g2,font, ">", 2 * gp.tileSize + 32, 7 * gp.tileSize, 32, 32, 32, 0);
            }
            Sprite.drawArray(g2, font, "]E^TA]T" , 3 * gp.tileSize, 7 * gp.tileSize , 32, 32, 32, 0);
            if (commandNum == 1) {
                Sprite.drawArray(g2,font, ">", 2 * gp.tileSize + 32, 8 * gp.tileSize, 32, 32, 32, 0);
            }
            Sprite.drawArray(g2, font, "MENU" , 3 * gp.tileSize, 8 * gp.tileSize , 32, 32, 32, 0);
        }
    }

    public void drawWinState() {
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        Sprite.drawArray(g2, font, "You", gp.tileSize, 3 * gp.tileSize, 64, 64, 64, 0);
        Sprite.drawArray(g2, font, "Won", gp.tileSize * 5, 3 * gp.tileSize, 64, 64, 64, 0);

        if (gp.currentLevel == 1) {
            if (commandNum == 0) {
                Sprite.drawArray(g2, font, ">", 2 * gp.tileSize + 32, 7 * gp.tileSize, 32, 32, 32, 0);
            }
            Sprite.drawArray(g2, font, "NEXT LEVEL", 3 * gp.tileSize, 7 * gp.tileSize, 32, 32, 32, 0);
            if (commandNum == 1) {
                Sprite.drawArray(g2, font, ">", 2 * gp.tileSize + 32, 8 * gp.tileSize, 32, 32, 32, 0);
            }
            Sprite.drawArray(g2, font, "MENU", 3 * gp.tileSize, 8 * gp.tileSize, 32, 32, 32, 0);
        } else {
            Sprite.drawArray(g2, font, "MENU", 3 * gp.tileSize, 8 * gp.tileSize, 32, 32, 32, 0);
        }
    }

    public void drawChooseState() {
        BufferedImageLoader loader = new BufferedImageLoader();
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth + 64*4, gp.screenHeight);
        Sprite.drawArray(g2, font, "^ELECT", 3 * gp.tileSize, 8 * gp.tileSize, 32, 32, 32, 0);
        Sprite.drawArray(g2, font, "CHA]ACTE]", 7 * gp.tileSize, 8 * gp.tileSize, 32, 32, 32, 0);
        Sprite.drawArray(g2, font, "P]E^^", 2 * gp.tileSize + 32, 7 * gp.tileSize, 32, 32, 32, 0);
        Sprite.drawArray(g2, font, "W", 5 * gp.tileSize  + 32, 7 * gp.tileSize, 32, 32, 32, 0);
        Sprite.drawArray(g2, font, "TO", 6 * gp.tileSize + 32, 7 * gp.tileSize, 32, 32, 32, 0);
        Sprite.drawArray(g2, font, "CHOO^E", 8 * gp.tileSize + 32, 7 * gp.tileSize, 32, 32, 32, 0);


        BufferedImage white = loader.loadImage("../../Res/Sprite-0001.png");
        BufferedImage black = loader.loadImage("../../Res/Sprite-0002.png");
        BufferedImage red = loader.loadImage("../../Res/Sprite-0003.png");
        g2.drawImage(white, 64 * 3 + 64 + 32, 64, null);
        g2.drawImage(black,  64 * 3 + 124 + 64 + 32 , 64, null);
        g2.drawImage(red, 64 * 3 + 128 + 64 + 64 + 64 + 32, 64, null);

        if (commandNum == 0) {
            Sprite.drawArray(g2,font, "i", 64 * 3 + 64 + 32 + 16, 64 + 64 + 32, 32, 32, 32, 0);
        }

        if (commandNum == 1) {
            Sprite.drawArray(g2,font, "i", 64 * 3 + 124 + 64 + 32 + 16, 64 + 64 + 32, 32, 32, 32, 0);
        }

        if (commandNum == 2) {
            Sprite.drawArray(g2,font, "i", 64 * 3 + 128 + 64 + 64 + 64 + 32 + 16,  64 + 64 + 32, 32, 32, 32, 0);
        }
    }
}
