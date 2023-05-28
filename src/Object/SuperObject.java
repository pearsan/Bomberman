package Object;

import Entities.Entity;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class SuperObject extends Entity {
    //public String name;
    public int screenX, screenY;
    public int mapPosition;
    public int solidAreaDefaultX = 16;
    public int solidAreaDefaultY = 16;

    public SuperObject(GamePanel gp) {
        super(gp);
        solidArea = new Rectangle(16, 16, 32, 32);
    }

    public void setSolidAreaDefault() {
        this.solidArea.x = solidAreaDefaultX;
        this.solidArea.y = solidAreaDefaultY;
    }

}

