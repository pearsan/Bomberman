package Entities;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    public GamePanel gp;
    public BufferedImage[] image;
    public int worldX, worldY;
    public int x,y;
    public int spriteCounter = 0;
    public int solidAreaDefaultX, solidAreaDefaultY;

    protected Rectangle solidArea = new Rectangle(0,0,48,48);
    protected boolean collision = false;
    protected String name;
    protected String direction;
    protected int spriteNum = 1;

    int speed;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public abstract void draw(Graphics2D g2);

    abstract public Rectangle getBound();

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public void setSolidArea(Rectangle solidArea) {
        this.solidArea = solidArea;
    }

    public int getSpriteNum() {
        return spriteNum;
    }

    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }
}
