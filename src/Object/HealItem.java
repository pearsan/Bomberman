package Object;

import Controller.UtilityTool;
import Main.GamePanel;
import Graphics.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HealItem extends Item {
    // ID: 2
    public HealItem(GamePanel gp, int x, int y) {
        super(gp, x, y);

        image = new BufferedImage[5];
        Sprite sprite = new Sprite("../../Res/Heal_Item.png", 32, 32);
        image = sprite.getSpriteArray();
        UtilityTool uTool = new UtilityTool();
        for (int i = 0; i < image.length; i++) {
            image[i] = uTool.scaleImage(image[i], 64, 64);
        }
    }

    public void update() {
        spriteCounter++;
        if (spriteCounter > 10) {
            if (spriteNum == 0) {
                spriteNum = 1;
            } else if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 3;
            } else if (spriteNum == 3) {
                spriteNum = 4;
            } else if (spriteNum == 4) {
                spriteNum = 0;
            }
            spriteCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage flame_item = null;
        flame_item = image[spriteNum];
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

            g2.drawImage(flame_item, screenX, screenY , null);
        } else if(gp.player.screenX > gp.player.worldX ||
                gp.player.screenY > gp.player.worldY ||
                rightOffset > gp.maxWorldCol * gp.tileSize - gp.player.worldX ||
                bottomOffset > gp.maxWorldRow * gp.tileSize -gp.player.worldY ) {
            g2.drawImage(flame_item, screenX, screenY, null);
        }
    }

    @Override
    public Rectangle getBound() {
        return super.getBound();
    }
}
