package Entities;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;


//Duy
public abstract class Enemy extends Entity{

    BufferedImage EnemyImage[] = new BufferedImage[10];
    public int actionLockCounter=0;
    boolean moving = false;
    boolean collidingPlayer = false;
    int pixelCounter = 0;
    int screenX,screenY;
    public Enemy(GamePanel gp) {
        super(gp);
        direction = "down";
        name = "Enemy";
        speed = 1;
        setEnemyImage();
        collision = true;
        solidArea = new Rectangle(1, 1, 62, 62);
    }

    public abstract void setEnemyImage();
    public abstract void setAction();
    public abstract void update();

    public void collidePlayer(Player player) {
        if (player.getPlayerLives() > 1) {
            if (player.getRelievingTime() == 100) {
                player.setPlayerLives(player.getPlayerLives() - 1);
                player.setRelievingTime(player.getRelievingTime() -1);
            }
        } else gp.lose = true;
        System.out.println(player.getPlayerLives());

    }
    public void draw(Graphics2D g2) {

    }
    public Rectangle getBound() {
        return new Rectangle(worldX + 1 , worldY + 1 , 62, 62);
    }

    public Rectangle getHitBox() {return new Rectangle(worldX + 16 , worldY + 16 , 32, 32);}
}
