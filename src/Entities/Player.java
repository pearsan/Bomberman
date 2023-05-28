package Entities;

import Controller.KeyHandler;
import Controller.UtilityTool;
import Graphics.Sprite;
import Main.GamePanel;
import Tile.TileManagement;
import Object.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    KeyHandler keyH;
    TileManagement tileManagement;
    int standCounter = 0;
    public boolean moving = false;
    int pixelCounter = 0;

    private int relievingTime = 100;
    private int tempSpriteNum = 6;
    public int x,y;

    public final int screenX;
    public final int screenY;
    public int count = 0;
    private int playerLives;

    BufferedImage playerImage[][];

    public Player(GamePanel gp, KeyHandler keyH, TileManagement tileManagement, BufferedImage spriteSheet) {
        super(gp);
        this.keyH = keyH;
        this.tileManagement = tileManagement;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        name = "Player";
        Sprite sprite = new Sprite(spriteSheet, 32, 48);
        solidArea = new Rectangle(1, 33, 60, 60);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefautValue();
        setPlayerImage(sprite);
    }


    public void setDefautValue() {
        moving = false;
        pixelCounter = 0;
        worldX = 64 * 1;
        worldY = 32 * 1;
        speed = 4;
        direction = "down";
        setPlayerLives(3);
    }

    public void setPlayerImage(Sprite sprite) {

        playerImage = sprite.getSpriteArray2();

        UtilityTool uTool = new UtilityTool();

        for (int i = 0; i < playerImage.length; i++) {
            for (int j = 0; j < playerImage[0].length; j++) {
                playerImage[i][j] = uTool.scaleImage(playerImage[i][j], 64, 48 * 2);
            }
        }
    }


    public void update() {
        if (getRelievingTime() < 100) {
            if (getRelievingTime() > 0) setRelievingTime(getRelievingTime() - 1);
            else setRelievingTime(100);
        }

        count++;
        if (!moving) {
            if(gp.player.count%10==0) {
                gp.playStop(2);
            }
               if(count%20==0) count=0;
//            }
            if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
                if (keyH.upPressed == true) {
                    direction = "up";
                    moving = true;
                } else if (keyH.downPressed == true) {
                    direction = "down";
                    moving = true;

                } else if (keyH.leftPressed == true) {
                    direction = "left";
                    moving = true;

                } else if (keyH.rightPressed == true) {
                    direction = "right";
                    moving = true;
                }

                //kiem tra va cham
                collision = false;
                gp.collisionChecker.checkTitle(this);
                gp.collisionChecker.checkBlock(this);
                // npc collision
                //  int npcIndex = gp.collisionChecker.(this,gp.npc);

                //check object collision
                int objIndex = gp.collisionChecker.checkObject(this);
                pickUpObject(objIndex);

            } else {
                standCounter++;
                if (standCounter == 20) {
                    spriteNum = 0;
                    standCounter = 0;
                }
            }
        }

        if (moving) {
            //false
//            if(count%20==0) {
//                System.out.println(count+ "play");
//                gp.playSE(2);
//                count=0;
//            }
            if (collision == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            spriteCounter++;
            if (getRelievingTime() != 100) {
                if (spriteCounter > 15) {
                    if (spriteNum != 6) {
                        tempSpriteNum = spriteNum;
                        spriteNum = 6;
                    } else {
                        spriteNum = tempSpriteNum;
                    }
                    spriteCounter = 0;
                }
            } else {
                if (spriteCounter > 10) {
                    if (spriteNum == 6) {
                        spriteNum = tempSpriteNum;
                    }
                    if (spriteNum == 0) {
                        spriteNum = 1;
                    }
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 3;
                    } else if (spriteNum == 3) {
                        spriteNum = 4;
                    } else if (spriteNum == 4) {
                        spriteNum = 5;
                    } else if (spriteNum == 5) {
                        spriteNum = 0;
                    }
                    spriteCounter = 0;
                }
            }

            pixelCounter += speed;
            if (pixelCounter == 64) {
                moving = false;
                pixelCounter = 0;
            }
        }

    }

    public void pickUpObject(int index) {
        if(index != 999) {
            Item objCheck = (Item) gp.objectManagement.obj.get(index);
                if (objCheck instanceof Portal) {
                    if (gp.numOfEnemies == 0) {
                        gp.win = true;
                        gp.setScore(gp.getScore() + 500);
                    }
                    System.out.println("true");
                }
                //System.out.println("hi");
                if (objCheck instanceof BombItem) {
                    gp.playSE(4);
                    gp.objectManagement.maxBombNum++;
                    gp.setScore(gp.getScore() + 50);
                } else if (objCheck instanceof FlameItem) {
                    gp.playSE(5);
                    gp.objectManagement.maxBombRadius++;
                    gp.setScore(gp.getScore() + 50);
                } else if (objCheck instanceof SpeedItem) {
                    gp.playSE(6);
                    gp.player.speed += 4;
                    gp.setScore(gp.getScore() + 50);
                }
                else if(objCheck instanceof CrateItem) {
                    gp.playSE(7);
                    gp.objectManagement.currentBullets+=2;
                    gp.setScore(gp.getScore() + 50);
                }
                else if (objCheck instanceof HealItem) {
                    gp.player.setPlayerLives(gp.player.getPlayerLives() + 1);
                    gp.setScore(gp.getScore() + 50);
                }
                if (objCheck instanceof Portal) {
                }
                else gp.objectManagement.obj.remove(index);
            }
    }


    public void draw(Graphics2D g2) {

/*        g2.setColor(Color.white);

        g2.fillRect(x, y, gp.titleSize, gp.titleSize);*/

        BufferedImage player_image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 0) {
                    player_image = playerImage[1][0];
                }
                if (spriteNum == 1) {
                    player_image = playerImage[1][1];
                }
                if (spriteNum == 2) {
                    player_image = playerImage[1][2];
                }
                if (spriteNum == 3) {
                    player_image = playerImage[1][3];
                }
                if (spriteNum == 4) {
                    player_image = playerImage[1][4];
                }
                if (spriteNum == 5) {
                    player_image = playerImage[1][5];
                }
                break;
            case "down":
                if (spriteNum == 0) {
                    player_image = playerImage[0][0];
                }
                if (spriteNum == 1) {
                    player_image = playerImage[0][1];
                }
                if (spriteNum == 2) {
                    player_image = playerImage[0][2];
                }
                if (spriteNum == 3) {
                    player_image = playerImage[0][3];
                }
                if (spriteNum == 4) {
                    player_image = playerImage[0][4];
                }
                if (spriteNum == 5) {
                    player_image = playerImage[0][5];
                }
                break;
            case "left":
                if (spriteNum == 0) {
                    player_image = playerImage[3][5];
                }
                if (spriteNum == 1) {
                    player_image = playerImage[3][4];
                }
                if (spriteNum == 2) {
                    player_image = playerImage[3][3];
                }
                if (spriteNum == 3) {
                    player_image = playerImage[3][2];
                }
                if (spriteNum == 4) {
                    player_image = playerImage[3][1];
                }
                if (spriteNum == 5) {
                    player_image = playerImage[3][0];
                }
                break;
            case "right":
                if (spriteNum == 0) {
                    player_image = playerImage[2][0];
                }
                if (spriteNum == 1) {
                    player_image = playerImage[2][1];
                }
                if (spriteNum == 2) {
                    player_image = playerImage[2][2];
                }
                if (spriteNum == 3) {
                    player_image = playerImage[2][3];
                }
                if (spriteNum == 4) {
                    player_image = playerImage[2][4];
                }
                if (spriteNum == 5) {
                    player_image = playerImage[2][5];
                }
                break;
        }

        x = screenX;
        y = screenY;
        if (screenX > worldX) {
            x = worldX;
        }
        if (screenY > worldY) {
            y = worldY;
        }
        int rightOffset = gp.screenWidth - screenX;
        if (rightOffset > tileManagement.mapCol * gp.tileSize - worldX) {
            x = gp.screenWidth - (tileManagement.mapCol * gp.tileSize - worldX);
        }
        int bottomOffset = gp.screenHeight - screenY;
        if (bottomOffset > tileManagement.mapRow * gp.tileSize - worldY) {
            y = gp.screenHeight - (tileManagement.mapRow * gp.tileSize - worldY);
        }

        g2.drawImage(player_image, x, y, null);
//        g2.setColor(Color.red);
//        g2.drawRect(x + solidArea.x, y + solidArea.y, solidArea.width, solidArea.height);
        //System.out.println(worldX + " " + worldY);
    }
    public Rectangle getBound() {
        return new Rectangle(worldX + 1 , worldY + 33 , 60, 60);
    }

    public int getRelievingTime() {
        return relievingTime;
    }

    public void setRelievingTime(int relievingTime) {
        this.relievingTime = relievingTime;
    }

    public int getPlayerLives() {
        return playerLives;
    }

    public void setPlayerLives(int playerLives) {
        this.playerLives = playerLives;
    }
}
