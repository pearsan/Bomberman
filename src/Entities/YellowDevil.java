package Entities;

import Controller.BufferedImageLoader;
import Controller.UtilityTool;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class YellowDevil extends Enemy {

    boolean caught=false;
    int extraSpeed = 0;
    public YellowDevil(GamePanel gp) {
        super(gp);
    }

    public void setEnemyImage() {
        name = "YellowDevil";
        UtilityTool uTool = new UtilityTool();
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage sprite = loader.loadImage("../../Res/yellow_sheet.png");
        for (int i = 0; i < 6; i++)
            EnemyImage[i] = uTool.scaleImage(sprite.getSubimage( 6+i * 32, 3, 32, 32), 120, 100);
        EnemyImage[7] = uTool.scaleImage(sprite.getSubimage( 6+6 * 32, 3, 32-6, 32-3), 120, 100);
    }

    public void setAction() {
        actionLockCounter++;
        collision = false;

        if (worldX > gp.player.worldX) {
            direction = "left";
            moving = true;
            speed=1;
            caught = false;
        }
        else if (worldX < gp.player.worldX) {
            direction = "right";
            moving = true;
            caught = false;
        }

        if (worldY -31< gp.player.worldY) {
            direction = "down";
            moving = true;
            caught = false;
        }
        if (worldY -31> gp.player.worldY) {
            direction = "up";
            moving = true;
            caught = false;
        }
        if(Math.abs(worldX-gp.player.worldX)<2 && Math.abs(worldY-31 -gp.player.worldY)<2) {
            direction = "stable";
            caught = true;
        }
        int x = gp.player.worldX -  worldX;
        int y = gp.player.worldY -  worldY;
        if(Math.abs(x*x + y*y)< 64*64*16){
            if(worldY%2==0) extraSpeed =2;
            else extraSpeed = 1;
        }
        else extraSpeed = 0;

        int objIndex = gp.collisionChecker.checkObject(this);


        if (actionLockCounter % 10==0) {
            spriteNum++;
            if (spriteNum > 3) spriteNum = 0;
        }
    }
    public void update() {
        setAction();
        collision = false;
        int objIndex = gp.collisionChecker.checkObject(this);

        if (moving && collision == false) {
            switch (direction) {
                case "up":
                    if(caught==false) worldY -= (speed + extraSpeed);

                    break;
                case "down":
                    if(caught==false) worldY += (speed + extraSpeed);

                    break;
                case "left":
                    if(caught==false) worldX -= (speed + extraSpeed);

                    break;
                case "right":
                    if(caught==false) worldX += (speed + extraSpeed);

                    break;
                case "stable":
                    speed=1;
                    break;
            }

            pixelCounter += (speed+extraSpeed);
            if (pixelCounter == 64) {
                moving = false;
                pixelCounter = 0;
            }
            gp.collisionChecker.checkObjForEnemy(this);

            if(gp.collisionChecker.checkEntity(gp.player, this) == 0){
                if (!collidingPlayer && gp.player.getRelievingTime() == 100) { // time dem sau khi va cham => va cham lan dau
                    collidePlayer(gp.player);
                    collidingPlayer = true;
                }
            }
            else {
                if (gp.player.getRelievingTime() == 100) collidingPlayer = false;
            }
        }

    }
    public void draw(Graphics2D g2) {
        BufferedImage image = EnemyImage[spriteNum];

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
            g2.drawImage(image, screenX, screenY, null);
//            g2.setColor(Color.red);
//            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

        } else if(gp.player.screenX > gp.player.worldX ||
                gp.player.screenY > gp.player.worldY ||
                rightOffset > gp.maxWorldCol * gp.tileSize - gp.player.worldX ||
                bottomOffset > gp.maxWorldRow * gp.tileSize -gp.player.worldY ) {
            g2.drawImage(image, screenX, screenY, null);
//            g2.setColor(Color.red);
//            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }
}

