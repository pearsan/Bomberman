package Entities;

import Controller.BufferedImageLoader;
import Controller.UtilityTool;
import Graphics.Sprite;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Balloon extends Enemy {

    BufferedImage balloonImage[][];

    public Balloon(GamePanel gp) {
        super(gp);
        spriteNum = 0;
        Random random = new Random();
        int i = random.nextInt(4) + 1;
        if (i <= 1) {
            direction = "up";
        } else if (i <= 2) {
            direction = "down";
        } else if (i <= 3) {
            direction = "right";
        } else {
            direction = "left";
        }
    }

    @Override
    public void setEnemyImage() {
        name = "Balloon";
        UtilityTool uTool = new UtilityTool();
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage spriteSheet = loader.loadImage("../../Res/balloon_sprite.png");
        Sprite sprite = new Sprite(spriteSheet, 16, 24);
        balloonImage = sprite.getSpriteArray2();

        for (int i = 0; i < balloonImage.length; i++) {
            for (int j = 0; j < balloonImage[0].length; j++) {
                balloonImage[i][j] = uTool.scaleImage(balloonImage[i][j], 64, 48*2);
            }
        }


    }

    @Override
    public void setAction() {

        actionLockCounter++;
        if(actionLockCounter%10==0) {
            switch (direction) {
                case "up":
                    direction = "right";
                    break;
                case "down":
                    direction = "left";
                    break;
                case "left":
                    direction = "up";
                    break;
                case "right":
                    direction = "down";
                    break;
            }
            collision = false;
            actionLockCounter = 0;
        }
    }

    @Override
    public void update() {

        gp.collisionChecker.checkTitle(this);
        gp.collisionChecker.checkObject(this);
        gp.collisionChecker.checkBlock(this);
        gp.collisionChecker.checkObjForEnemy(this);
        gp.collisionChecker.checkObjForEnemy(this);

        if (collision) {
            setAction();
        }
        if (!collision) {
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
            gp.collisionChecker.checkBlock(this);
            gp.collisionChecker.checkObjForEnemy(this);
            gp.collisionChecker.checkTitle(this);
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

            if (pixelCounter == 64) {
                gp.collisionChecker.checkBlock(this);
                collision = true;
                pixelCounter = 0;
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 0) {
                    spriteNum = 1;
                } else if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 0;
                }
                spriteCounter = 0;
            }

        }

    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(worldX + 1 , worldY + 1 , 62, 62);
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 0) {
                    image = balloonImage[1][0];
                }
                if (spriteNum == 1) {
                    image = balloonImage[1][1];
                }
                if (spriteNum == 2) {
                    image = balloonImage[1][2];
                }
                if (spriteNum == 3) {
                    image = balloonImage[1][3];
                }
                break;
            case "down":
                if (spriteNum == 0) {
                    image = balloonImage[0][0];
                }
                if (spriteNum == 1) {
                    image = balloonImage[0][1];
                }
                if (spriteNum == 2) {
                    image = balloonImage[0][2];
                }
                if (spriteNum == 3) {
                    image = balloonImage[0][3];
                }

                break;
            case "left":
                if (spriteNum == 0) {
                    image = balloonImage[2][3];
                }
                if (spriteNum == 1) {
                    image = balloonImage[2][2];
                }
                if (spriteNum == 2) {
                    image = balloonImage[2][1];
                }
                if (spriteNum == 3) {
                    image = balloonImage[2][0];
                }

                break;
            case "right":
                if (spriteNum == 0) {
                    image = balloonImage[3][0];
                }
                if (spriteNum == 1) {
                    image = balloonImage[3][1];
                }
                if (spriteNum == 2) {
                    image = balloonImage[3][2];
                }
                if (spriteNum == 3) {
                    image = balloonImage[3][3];
                }

                break;
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
            g2.drawImage(image, screenX, screenY - 16, null);
//            g2.setColor(Color.red);
//            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        } else if(gp.player.screenX > gp.player.worldX ||
                gp.player.screenY > gp.player.worldY ||
                rightOffset > gp.maxWorldCol * gp.tileSize - gp.player.worldX ||
                bottomOffset > gp.maxWorldRow * gp.tileSize -gp.player.worldY ) {
            g2.drawImage(image, screenX, screenY - 16, null);
//            g2.setColor(Color.red);
//            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }
}