package Entities;

import Controller.BufferedImageLoader;
import Controller.UtilityTool;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RedDevil extends Enemy {

    public RedDevil(GamePanel gp) {
        super(gp);
        name = "Red";
    }
    public void setEnemyImage() {
        UtilityTool uTool = new UtilityTool();
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage sprite = loader.loadImage("../../Res/red.png");
        for(int i=0;i<4;i++)
            EnemyImage[i] = uTool.scaleImage(sprite.getSubimage(i*16,0,16,16),64,64);
    }
    public void setAction() {
        collision = false;  // sau khi va cham collision true nen can chuyen thanh false de no di chuyen dc
        gp.collisionChecker.checkTitle(this); // ban Tuan Anh
        gp.collisionChecker.checkBlock(this); // ban Su
        actionLockCounter++;
        if(actionLockCounter%30==0) {
            spriteNum++;
            if(spriteNum>=3) spriteNum=0;
        }
        if(actionLockCounter==1) {
            direction = "left";
        }
        else if(actionLockCounter==128) {
            direction= "right";
        }
        else if(actionLockCounter==384) {
            direction = "left";
        }
        else if(actionLockCounter==512) {
            direction = "right";
            actionLockCounter=0;
        }
        moving = true;
        collision = false;
        gp.collisionChecker.checkTitle(this);
        gp.collisionChecker.checkBlock(this);
    }

    public void update() {
        setAction();
        collision = false;
        gp.collisionChecker.checkBlock(this);
        gp.collisionChecker.checkTitle(this);
        //int objIndex=gp.collisionChecker.checkObject(this);
        // check Block,Bullet,...
        gp.collisionChecker.checkObjForEnemy(this);
        if (moving && collision == false) {
            switch (direction) {
                case "left":
                    worldX -= (speed);

                    break;
                case "right":
                    worldX += (speed);

                    break;
            }

            pixelCounter += speed;
            gp.collisionChecker.checkObjForEnemy(this);
            gp.collisionChecker.checkBlock(this);
            if(gp.collisionChecker.checkEntity(gp.player, this)==0){
                if (!collidingPlayer && gp.player.getRelievingTime() == 100) {
                    collidePlayer(gp.player);
                    collidingPlayer = true;
                }
            }
            else {
                if (gp.player.getRelievingTime() == 100) collidingPlayer = false;
            }
            gp.collisionChecker.checkBlock(this);
            if (pixelCounter == 64) {
                gp.collisionChecker.checkBlock(this);
                moving = false;
                pixelCounter = 0;
            }
        }
        if (collision) {
            setAction();
        }

    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        if (spriteNum == 0) {
            image = EnemyImage[0];
        }
        if (spriteNum == 1) {
            image = EnemyImage[1];
        }
        if (spriteNum == 2) {
            image = EnemyImage[2];
        }

        // Tuan Anh - camera
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