package Object;

import Controller.UtilityTool;
import Main.GamePanel;
import Graphics.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends SuperObject {
        public boolean destroyed = false;
        public int destroyingTime = 25;
        public boolean portal = false;

        public Block(GamePanel gp, int x, int y, boolean portal) {
                super(gp);
                collision = true;
                image = new BufferedImage[10];
                name = "Block";
                UtilityTool uTool = new UtilityTool();
                Sprite sprite = new Sprite("../../Res/Block_sprite.png", 32, 32);
                image = sprite.getSpriteArray(0);
                for (int i = 0; i < image.length; i++) {
                        image[i] = uTool.scaleImage(image[i], 64, 64);
                }

                this.portal = portal;
                worldX = x;
                worldY = y;
        }

        public void update() {
                spriteCounter++;
                if (!destroyed) {
                        if (spriteCounter > 15) {
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
                } else {
                        if (spriteCounter > 10) {
                                if (spriteNum == 3) {
                                        spriteNum = 4;
                                } else if (spriteNum == 4) {
                                        spriteNum = 5;
                                } else if (spriteNum == 5) {
                                        spriteNum = 6;
                                } else if (spriteNum == 6) {
                                        spriteNum = 7;
                                } else if (spriteNum == 7) {
                                        spriteNum = 8;
                                }
                                spriteCounter = 0;
                        }
                }
        }

        @Override
        public void draw(Graphics2D g2) {
                BufferedImage block_image = null;
                block_image = image[spriteNum];

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
                 g2.drawImage(block_image, screenX, screenY , null);
                }
                else if(gp.player.screenX > gp.player.worldX ||
                        gp.player.screenY > gp.player.worldY ||
                        rightOffset > gp.maxWorldCol * gp.tileSize - gp.player.worldX ||
                        bottomOffset > gp.maxWorldRow * gp.tileSize -gp.player.worldY ) {
                g2.drawImage(block_image, screenX, screenY , null);
        }
                //g2.setColor(Color.white);
                //g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
                //Animation animation = new Animation(10, image);
        }
        public Rectangle getBound() {
                //return null;
                return new Rectangle(worldX , worldY , 64, 64);
        }
}