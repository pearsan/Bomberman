package Entities;

import Controller.BufferedImageLoader;
import Controller.UtilityTool;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BlackDevil extends Enemy{
    public BlackDevil(GamePanel gp) {
        super(gp);
        speed = 2;
    }

    public void setEnemyImage() {
        UtilityTool uTool = new UtilityTool();
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage sprite = loader.loadImage("../../Res/sprite_sheet.png");
        for(int i=0;i<3;i++)
            EnemyImage[i] = uTool.scaleImage(sprite.getSubimage(96+i*32,0,32,32),64,64);
    }
    public void setAction() {
        collision = false;  // sau khi va cham collision true nen can chuyen thanh false de no di chuyen dc
        gp.collisionChecker.checkTitle(this); // ban Tuan Anh
        gp.collisionChecker.checkBlock(this); // ban Su

        Random random = new Random();
        int i = random.nextInt(100) + 1;
        if (i <= 25) {
            direction = "up";
            moving = true;
        } else if (i <= 50) {
            direction = "down";
            moving = true;
        } else if (i <= 75) {
            direction = "right";
            moving = true;
        } else {
            direction = "left";
            moving = true;
        }
        spriteNum++;

        //int objIndex = gp.collisionChecker.checkObject(this);
        if(spriteNum>1) spriteNum=0;
    }

    public void update() {
        collision = false;
        gp.collisionChecker.checkBlock(this);
        gp.collisionChecker.checkTitle(this);
        gp.collisionChecker.checkObject(this);

        if (!moving) {
            gp.collisionChecker.checkObjForEnemy(this);
            gp.collisionChecker.checkTitle(this);
            gp.collisionChecker.checkBlock(this);
            if(gp.collisionChecker.checkEntity(gp.player, this) == 0){
                //System.out.println("error black");
                collidePlayer(gp.player);
            }
            setAction();
        }
        if (moving) {
            if (collision == false) {
                switch (direction) {
                    case "up":
                        worldY -= (speed);
                        break;
                    case "down":
                        worldY += (speed);
                        break;
                    case "left":
                        worldX -= (speed);
                        break;
                    case "right":
                        worldX += (speed);
                        break;
                }
            }
            gp.collisionChecker.checkBlock(this);
            pixelCounter += speed;
            if (pixelCounter == 64) {
                gp.collisionChecker.checkBlock(this);
                gp.collisionChecker.checkObjForEnemy(this);
                moving = false;
                pixelCounter = 0;
            }
            gp.collisionChecker.checkBlock(this);
            gp.collisionChecker.checkObjForEnemy(this);

            if(gp.collisionChecker.checkEntity(gp.player, this) == 0){
                //gp.lose = true;
               // System.out.println("error black");
                if (!collidingPlayer && gp.player.getRelievingTime() == 100) { // cham roi
                    collidePlayer(gp.player);    // time dem sau khi va cham => va cham lan dau
                    collidingPlayer = true;
                }
            }
            else {
                if (gp.player.getRelievingTime() == 100) collidingPlayer = false; // chua cham neu reset roi thi moi la false
            }
        }
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        if (spriteNum == 0) {
            image = EnemyImage[1];
        }
        if (spriteNum == 1) {
            image = EnemyImage[2];
        }
        if (spriteNum == 2) {
            image = EnemyImage[0];
        }

        // ban Tuan Anh - camera
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
    public Rectangle getBound() {
        return new Rectangle(worldX + 1 , worldY + 1 , 64, 64);
    }
}