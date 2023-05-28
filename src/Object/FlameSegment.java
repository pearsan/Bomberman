package Object;

import Controller.UtilityTool;
import Graphics.Sprite;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FlameSegment extends SuperObject {

    boolean last;
    //Animation animation = new Animation();

    public FlameSegment(GamePanel pg, int x, int y, String direction, boolean last) {
        super(pg);
        this.worldX = x;
        this.worldY = y;
        this.direction = direction;
        this.last = last;
        image = new BufferedImage[35];
        Sprite sprite = new Sprite("../../Res/explosion_sheet.png", 16, 16);
        image = sprite.getSpriteArray();
        UtilityTool uTool = new UtilityTool();
        for (int i = 0; i < image.length; i++) {
            image[i] = uTool.scaleImage(image[i], 64, 64);
        }
    }

    public void update() {
        spriteCounter++;
        if (spriteCounter > 5) {
            spriteNum++;
            spriteCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage flame_image = null;
        switch (direction) {
            case "center":
                if (last) {
                    if (spriteNum == 0) {
                        flame_image = image[9];
                    }
                    if (spriteNum == 1) {
                        flame_image = image[8];
                    }
                    if (spriteNum == 2) {
                        flame_image = image[7];
                    }
                    if (spriteNum == 3) {
                        flame_image = image[6];
                    }
                    if (spriteNum == 4) {
                        flame_image = image[5];
                    }
                }
                break;
            case "up":
                if (last) {
                    if (spriteNum == 0) {
                        flame_image = image[4];
                    }
                    if (spriteNum == 1) {
                        flame_image = image[3];
                    }
                    if (spriteNum == 2) {
                        flame_image = image[2];
                    }
                    if (spriteNum == 3) {
                        flame_image = image[1];
                    }
                    if (spriteNum == 4) {
                        flame_image = image[0];
                    }
                } else {
                    if (spriteNum == 0) {
                        flame_image = image[14];
                    }
                    if (spriteNum == 1) {
                        flame_image = image[13];
                    }
                    if (spriteNum == 2) {
                        flame_image = image[12];
                    }
                    if (spriteNum == 3) {
                        flame_image = image[11];
                    }
                    if (spriteNum == 4) {
                        flame_image = image[10];
                    }
                }
                break;
            case "down":
                if (last) {
                    if (spriteNum == 0) {
                        flame_image = image[19];
                    }
                    if (spriteNum == 1) {
                        flame_image = image[18];
                    }
                    if (spriteNum == 2) {
                        flame_image = image[17];
                    }
                    if (spriteNum == 3) {
                        flame_image = image[16];
                    }
                    if (spriteNum == 4) {
                        flame_image = image[15];
                    }
                } else {
                    if (spriteNum == 0) {
                        flame_image = image[14];
                    }
                    if (spriteNum == 1) {
                        flame_image = image[13];
                    }
                    if (spriteNum == 2) {
                        flame_image = image[12];
                    }
                    if (spriteNum == 3) {
                        flame_image = image[11];
                    }
                    if (spriteNum == 4) {
                        flame_image = image[10];
                    }
                }
                break;
            case "left":
                if (last) {
                    if (spriteNum == 0) {
                        flame_image = image[34];
                    }
                    if (spriteNum == 1) {
                        flame_image = image[33];
                    }
                    if (spriteNum == 2) {
                        flame_image = image[32];
                    }
                    if (spriteNum == 3) {
                        flame_image = image[31];
                    }
                    if (spriteNum == 4) {
                        flame_image = image[30];
                    }
                } else {
                    if (spriteNum == 0) {
                        flame_image = image[24];
                    }
                    if (spriteNum == 1) {
                        flame_image = image[23];
                    }
                    if (spriteNum == 2) {
                        flame_image = image[22];
                    }
                    if (spriteNum == 3) {
                        flame_image = image[21];
                    }
                    if (spriteNum == 4) {
                        flame_image = image[20];
                    }
                }
                break;
            case "right":
                if (last) {
                    if (spriteNum == 0) {
                        flame_image = image[29];
                    }
                    if (spriteNum == 1) {
                        flame_image = image[28];
                    }
                    if (spriteNum == 2) {
                        flame_image = image[27];
                    }
                    if (spriteNum == 3) {
                        flame_image = image[26];
                    }
                    if (spriteNum == 4) {
                        flame_image = image[25];
                    }
                } else {
                    if (spriteNum == 0) {
                        flame_image = image[24];
                    }
                    if (spriteNum == 1) {
                        flame_image = image[23];
                    }
                    if (spriteNum == 2) {
                        flame_image = image[22];
                    }
                    if (spriteNum == 3) {
                        flame_image = image[21];
                    }
                    if (spriteNum == 4) {
                        flame_image = image[20];
                    }
                }
                break;

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
            g2.drawImage(flame_image, screenX, screenY, null);
//            g2.setColor(Color.red);
//            g2.drawRect(screenX, screenY, solidArea.width*2-16, solidArea.height*2-16);
//            //Animation animation = new Animation(10, image);
        } else if(gp.player.screenX > gp.player.worldX ||
                gp.player.screenY > gp.player.worldY ||
                rightOffset > gp.maxWorldCol * gp.tileSize - gp.player.worldX ||
                bottomOffset > gp.maxWorldRow * gp.tileSize -gp.player.worldY ) {
            g2.drawImage(flame_image, screenX, screenY , null);
//            g2.setColor(Color.red);
//            g2.drawRect(screenX, screenY, solidArea.width*2 -16, solidArea.height*2-16);
//            //Animation animation = new Animation(10, image);
        }

    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(worldX, worldY, solidArea.width , solidArea.height);

    }
}
