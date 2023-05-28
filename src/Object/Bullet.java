package Object;

import Main.GamePanel;

import java.awt.*;
//Duy
public class Bullet extends SuperObject {
    public boolean down,up,right,left=false;

    public Bullet(GamePanel gp) {
        super(gp);
        worldX = gp.player.worldX ; // vi tri cua nguoi
        worldY = gp.player.worldY ;//
        name = "Bullet";
    }


    public void draw(Graphics2D g2) {
        if(right) worldX = worldX +6;
        if(left) worldX = worldX -6;
        if(up) worldY -= 6;
        if(down) worldY +=6;

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

            g2.setColor(Color.yellow);
            g2.fillOval(screenX+24, screenY+48, 32, 32);
        } else if(gp.player.screenX > gp.player.worldX ||
                gp.player.screenY > gp.player.worldY ||
                rightOffset > gp.maxWorldCol * gp.tileSize - gp.player.worldX ||
                bottomOffset > gp.maxWorldRow * gp.tileSize -gp.player.worldY ) {
            g2.setColor(Color.yellow);
            g2.fillOval(screenX+24, screenY+48, 32, 32);
        }
    }
    public Rectangle getBound() {
        return new Rectangle(worldX + 24, worldY + 48, 32, 32);
    }
}
