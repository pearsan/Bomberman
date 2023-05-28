package Object;

import Entities.Balloon;
import Entities.BlackDevil;
import Entities.RedDevil;
import Entities.YellowDevil;
import Main.GamePanel;

import java.awt.image.BufferedImage;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject(BufferedImage image) {
        int x = 0,y = 0;
        int currentNPC = 0;
        while (x < image.getWidth() && y < image.getHeight()) {

            int pixel = image.getRGB(x,y);
            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = pixel & 0xff;

            if (red == 255 && green == 255 && blue == 255) {
                gp.objectManagement.blockList.add(new Block(gp, x * 64, y * 64, false));
            } else if (red == 204 && green == 204 && blue == 204) {
                gp.objectManagement.blockList.add(new Block(gp, x * 64, y * 64, true));
            } else if (red == 0 && green == 255 && blue == 255) {
                gp.npc[currentNPC] = new BlackDevil(gp);
                gp.npc[currentNPC].worldX = gp.tileSize * x;
                gp.npc[currentNPC].worldY = gp.tileSize * y;;
                currentNPC++;
            } else if (red == 195 && green == 92 && blue == 92) {
                gp.npc[currentNPC] = new RedDevil(gp);
                gp.npc[currentNPC].worldX = gp.tileSize * x;
                gp.npc[currentNPC].worldY = gp.tileSize * y;;
                currentNPC++;
            } else if (red == 174 && green == 187 && blue == 23) {
                gp.npc[currentNPC] = new YellowDevil(gp);
                gp.npc[currentNPC].worldX = gp.tileSize * x;
                gp.npc[currentNPC].worldY = gp.tileSize * y;;
                currentNPC++;
            } else if (red == 255 && green == 128 && blue == 0) {
                gp.npc[currentNPC] = new Balloon(gp);
                gp.npc[currentNPC].worldX = gp.tileSize * x;
                gp.npc[currentNPC].worldY = gp.tileSize * y;;
                currentNPC++;
            }
            x += 1;
            if (x == image.getWidth()) {
                x = 0;
                y+=1;
            }
        }
    }
}
