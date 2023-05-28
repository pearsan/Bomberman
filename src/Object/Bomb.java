package Object;

import Controller.UtilityTool;
import Graphics.Sprite;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;


public class Bomb extends SuperObject {
    public int countdown = 210;
    public int explosionTime = 150;
    public Flame[] flames;
    private BufferedImage bomb;
    protected boolean exploded = false;
    protected int maxRadius;
    int cycle = 0;
    int second = 0;
    Timer timer;

    public Bomb(GamePanel gp) {
        super(gp);
        collision = true;
        this.maxRadius = gp.objectManagement.maxBombRadius;
        UtilityTool uTool = new UtilityTool();
        name = "Bomb";
        image = new BufferedImage[3];
        Sprite sprite = new Sprite("../../Res/bomb_sprite_sheet.png", 16, 16);
        image = sprite.getSpriteArray();
        for (int i = 0; i < image.length; i++) {
            image[i] = uTool.scaleImage(image[i], 64, 64);
        }
//        BufferedImageLoader loader = new BufferedImageLoader();
//        image = loader.loadImage("../../Res/bomb_sprite_sheet.png");
//        image = image.getSubimage(32, 0, 16, 16);
//        image = uTool.scaleImage(image, 64, 64);
    }

    public void setBomb(GamePanel gp) {
        this.worldX = gp.player.worldX + gp.player.getSolidArea().x;
        this.worldY = gp.player.worldY + gp.player.getSolidArea().y;

    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public void update() {
        if(countdown > 0) {
            countdown--;
            spriteCounter++;
            if (spriteCounter > 15) {
                if (spriteNum == 0) {
                    spriteNum = 1;
                } else if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 0;
                }
                spriteCounter = 0;
            }
        }
        else {
            if(!exploded)
                explode();

            explosionTime--;
        }
    }

    public void explode() {
        exploded = true;


        flames = new Flame[5];
        flames[0] = new Flame(gp, worldX, worldY, "center", maxRadius);
        flames[1] = new Flame(gp, worldX, worldY, "down", maxRadius);
        flames[2] = new Flame(gp, worldX, worldY, "right", maxRadius);
        flames[3] = new Flame(gp, worldX, worldY, "up", maxRadius);
        flames[4] = new Flame(gp, worldX, worldY, "left", maxRadius);

        //gp.playMusic(2);


    }

    @Override
    public void draw(Graphics2D g2) {
        if (spriteNum == 0) {
            bomb = image[0];
        }
        if (spriteNum == 1) {
            bomb = image[1];
        }
        if (spriteNum == 2) {
            bomb = image[2];
        }
        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (gp.player.screenX > gp.player.worldX) {
            screenX = worldX;
        }
        if (gp.player.screenY > gp.player.worldY) {
            screenY = worldY;
        }
        int rightOffset = gp.screenWidth - gp.player.screenX;
        if (rightOffset > (gp.maxWorldCol * gp.tileSize) - gp.player.worldX) {
            screenX = gp.screenWidth - ((gp.maxWorldCol * gp.tileSize) - worldX);
        }
        int bottomOffset = gp.screenHeight - gp.player.screenY;
        if (bottomOffset > (gp.maxWorldRow * gp.tileSize) - gp.player.worldY) {
            screenY = gp.screenHeight - ((gp.maxWorldRow * gp.tileSize) - worldY);
        }

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(bomb, screenX, screenY, null);
//            g2.setColor(Color.red);
//            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
//            //Animation animation = new Animation(10, image);
        } else if(gp.player.screenX > gp.player.worldX ||
                gp.player.screenY > gp.player.worldY ||
                rightOffset > gp.maxWorldCol * gp.tileSize - gp.player.worldX ||
                bottomOffset > gp.maxWorldRow * gp.tileSize -gp.player.worldY ) {
            g2.drawImage(bomb, screenX, screenY , null);
//            g2.setColor(Color.red);
//            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
//            //Animation animation = new Animation(10, image);
        }
    }
    public Rectangle getBound() {
        return new Rectangle(worldX, worldY, 64, 64);
    }
}
