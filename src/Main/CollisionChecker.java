package Main;

import Entities.*;
import Object.*;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTitle(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.getSolidArea().x;
        int entityRightWorldX =  entity.worldX + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.worldY + entity.getSolidArea().y;
        int entityBottomWorldY = entity.worldY + entity.getSolidArea().y + entity.getSolidArea().height;

        int entityLeftCol =  (entityLeftWorldX / gp.tileSize);
        int entityRightCol=  (entityRightWorldX / gp.tileSize);
        int entityTopRow =  (entityTopWorldY / gp.tileSize);
        int entityBottomRow =  (entityBottomWorldY / gp.tileSize);

        int tileNum1, tileNum2;

        switch (entity.getDirection()) {
            case "up":
                entityTopRow = (int) ((entityTopWorldY - entity.getSpeed()) / gp.tileSize);
                if(gp.tileManagement.tilesMap[entityLeftCol][entityTopRow].collision ||
                        gp.tileManagement.tilesMap[entityRightCol][entityTopRow].collision) {
                    entity.setCollision(true);
                }
                break;
            case "down":
                entityBottomRow = (int) ((entityBottomWorldY + entity.getSpeed()) / gp.tileSize);

                if(gp.tileManagement.tilesMap[entityLeftCol][entityBottomRow].collision ||
                        gp.tileManagement.tilesMap[entityRightCol][entityBottomRow].collision) {
                    entity.setCollision(true);
                }
                break;
            case "left":
                entityLeftCol = (int) ((entityLeftWorldX - entity.getSpeed()) / gp.tileSize);
                if(gp.tileManagement.tilesMap[entityLeftCol][entityTopRow].collision ||
                        gp.tileManagement.tilesMap[entityLeftCol][entityBottomRow].collision) {
                    entity.setCollision(true);
                }
                break;
            case "right":
                entityRightCol = (int) ((entityRightWorldX + entity.getSpeed()) / gp.tileSize);
                if(gp.tileManagement.tilesMap[entityRightCol][entityTopRow].collision ||
                        gp.tileManagement.tilesMap[entityRightCol][entityBottomRow].collision) {
                    entity.setCollision(true);
                }
                break;
        }
        for (Block block: gp.objectManagement.blockList) {
            switch (entity.getDirection()) {
                case "up":
                    if (entity.worldX == block.worldX && entity.worldY == block.worldY + 32) {
                        entity.setCollision(true);
                        //System.out.println("collide block up");
                    }
                    break;
                case "down":
                    if (entity.worldX == block.worldX && entity.worldY == block.worldY - 96) {
                        entity.setCollision(true);
                        //System.out.println("collide block down");
                    }
                    break;
                case "left":
                    if (entity.worldX == block.worldX + 64 && entity.worldY == block.worldY - 32) {
                        entity.setCollision(true);
                        //System.out.println("collide block left");
                    }
                    break;
                case "right":
                    if (entity.worldX == block.worldX - 64 && entity.worldY == block.worldY - 32) {
                        entity.setCollision(true);
                        //System.out.println("collide block right");
                    }
                    break;
            }
        }

    }

    public int checkObject(Entity entity) {
        int index = 999;
        for (int i = 0; i < gp.objectManagement.obj.size(); i++) {
            if (entity.getName().equals("Player")) {
                if (gp.objectManagement.obj.get(i).getName().equals("Item")) {
                    switch (entity.getDirection()) {
                        case "up":
                            if (entity.worldX == gp.objectManagement.obj.get(i).worldX &&
                                    entity.worldY == gp.objectManagement.obj.get(i).worldY + 32) {

                                return i;
                            }
                            break;
                        case "down":
                            if (entity.worldX == gp.objectManagement.obj.get(i).worldX &&
                                    entity.worldY == gp.objectManagement.obj.get(i).worldY - 96) {

                                return i;
                            }
                            break;
                        case "left":
                            if (entity.worldX == gp.objectManagement.obj.get(i).worldX + 64 &&
                                    entity.worldY == gp.objectManagement.obj.get(i).worldY - 32) {

                                return i;
                            }
                            break;
                        case "right":
                            if (entity.worldX == gp.objectManagement.obj.get(i).worldX - 64 &&
                                    entity.worldY == gp.objectManagement.obj.get(i).worldY - 32) {

                                return i;
                            }
                            break;

                        }

                    } else if (gp.objectManagement.obj.get(i).getName().equals("Bomb")) {
                        switch (entity.getDirection()) {
                            case "up":
                                if (entity.worldX == gp.objectManagement.obj.get(i).worldX &&
                                        entity.worldY == gp.objectManagement.obj.get(i).worldY + 32) {
                                    entity.setCollision(true);
                                }
                                break;
                            case "down":
                                if (entity.worldX == gp.objectManagement.obj.get(i).worldX &&
                                        entity.worldY == gp.objectManagement.obj.get(i).worldY - 96) {
                                    entity.setCollision(true);
                                }
                                break;
                            case "left":
                                if (entity.worldX == gp.objectManagement.obj.get(i).worldX + 64 &&
                                        entity.worldY == gp.objectManagement.obj.get(i).worldY - 32) {
                                    entity.setCollision(true);
                                }
                                break;
                            case "right":
                                if (entity.worldX == gp.objectManagement.obj.get(i).worldX - 64 &&
                                        entity.worldY == gp.objectManagement.obj.get(i).worldY - 32) {
                                    entity.setCollision(true);
                                }
                                break;
                        }
                    }
            } else if (entity.getName().equals("Item")) {
                if (gp.objectManagement.obj.get(i).getName().equals("Flame")) {
                    Flame check = (Flame) gp.objectManagement.obj.get(i);
                    for (FlameSegment fs : check.flameSegments) {
                        if (entity.getBound().intersects(fs.getBound())) {
                            return i;
                        }
                    }
                }
            } else if (entity.getName().equals("Block")) {
                if (gp.objectManagement.obj.get(i).getName().equals("Flame")) {
                    Flame check = (Flame) gp.objectManagement.obj.get(i);
                    for (FlameSegment fs : check.flameSegments) {
                        if (entity.getBound().intersects(fs.getBound())) {
                           
                            return i;
                        }
                    }
                }
                if(gp.objectManagement.obj.get(i).getName().equals("Bullet")
                        && gp.objectManagement.obj.get(i).getBound().intersects(entity.getBound())){
                    return i;
                }
            }

                entity.getSolidArea().x = entity.solidAreaDefaultX;
                entity.getSolidArea().y = entity.solidAreaDefaultY;
                gp.objectManagement.obj.get(i).setSolidAreaDefault();
                //gp.objectManagement.obj.get(i).solidArea.x = gp.objectManagement.get(i).solidAreaDefaultX;
                //gp.objectManagement.obj[i].solidArea.y = gp.objectManagement.obj[i].solidAreaDefaultY;

            }

        return index;
    }

    public int checkEntity(Entity entity, Enemy target) {
        int index = 999;

        if(entity.getBound().intersects(target.getHitBox())) {
            index = 0;
        }
        entity.getSolidArea().x = entity.solidAreaDefaultX;
        entity.getSolidArea().y = entity.solidAreaDefaultY;
        target.getSolidArea().x = target.solidAreaDefaultX;
        target.getSolidArea().y = target.solidAreaDefaultY;


        return index;
    }

    public void checkObjForEnemy(Enemy enemy) {
        int index = 999;
        for (int i = 0; i < gp.objectManagement.obj.size(); i++) {
            if (gp.objectManagement.obj.get(i).getName().equals("Bullet") &&
                    enemy.getBound().intersects(gp.objectManagement.obj.get(i).getBound())) {
                for (int j = 0; j < gp.npc.length; j++) {
                    if (enemy == gp.npc[j]) gp.npc[j] = null;
                }
                //index = i;
                gp.objectManagement.obj.remove(i);
                //return i;
                return;
            } else if (gp.objectManagement.obj.get(i).getName().equals("Flame")) {
                Flame check = (Flame) gp.objectManagement.obj.get(i);
                for (FlameSegment fs: check.flameSegments) {
                    if (enemy.getBound().intersects(fs.getBound())) {
                        for (int j = 0; j < gp.npc.length; j++) {
                            if (enemy == gp.npc[j]) {
                                if (enemy instanceof RedDevil) {
                                    gp.setScore(gp.getScore() + 100);
                                }
                                if (enemy instanceof Balloon) {
                                    gp.setScore(gp.getScore() + 150);
                                }
                                if (enemy instanceof BlackDevil) {
                                    gp.setScore(gp.getScore() + 200);
                                }
                                if (enemy instanceof YellowDevil) {
                                    gp.setScore(gp.getScore() + 300);
                                }
                                gp.npc[j] = null;
                                //return index;
                                return;
                            }
                        }
                    }
                }
            } else if (gp.objectManagement.obj.get(i).getName().equals("Bomb")) {
                switch (enemy.getDirection()) {
                    case "up":
                        if (((enemy.worldX >= gp.objectManagement.obj.get(i).worldX - 1) &&
                                (enemy.worldX <= gp.objectManagement.obj.get(i).worldX + 1)) &&
                                enemy.worldY == gp.objectManagement.obj.get(i).worldY + 64) {
                            enemy.setCollision(true);

                        }
                        break;
                    case "down":
                        if (((enemy.worldX >= gp.objectManagement.obj.get(i).worldX - 1) &&
                                (enemy.worldX <= gp.objectManagement.obj.get(i).worldX + 1)) &&
                                enemy.worldY == gp.objectManagement.obj.get(i).worldY - 64) {
                            enemy.setCollision(true);

                        }
                        break;
                    case "left":
                        if (enemy.worldX == gp.objectManagement.obj.get(i).worldX + 64 &&
                            (enemy.worldY <= gp.objectManagement.obj.get(i).worldY + 1) &&
                            (enemy.worldY >= gp.objectManagement.obj.get(i).worldY - 1)) {
                            enemy.setCollision(true);
                            if (enemy.getName() == "Red") {
                                enemy.setDirection("right");
                                enemy.worldX ++;
                            }

                        }
                        break;
                    case "right":
                        if (enemy.worldX == gp.objectManagement.obj.get(i).worldX - 64 &&
                            (enemy.worldY <= gp.objectManagement.obj.get(i).worldY + 1) &&
                                (enemy.worldY >= gp.objectManagement.obj.get(i).worldY - 1)) {
                            enemy.setCollision(true);
                            if (enemy.getName() == "Red") {
                                enemy.setDirection("left");
                                enemy.worldX--;
                            }
                        }
                        break;
                }

            }
            //gp.objectManagement.obj.get(i).setSolidAreaDefault();
        }
    }

    public void checkBlock(Entity entity) {
        for (int i = 0; i < gp.objectManagement.blockList.size(); i++) {
            if (entity.getName().equals("Flame")) {
                if (entity.worldX == gp.objectManagement.blockList.get(i).worldX &&
                        entity.worldY == gp.objectManagement.blockList.get(i).worldY) {
                    entity.setCollision(true);
                }
            }
        if (entity.getName().equals("Player")) {
            switch (entity.getDirection()) {
                case "up":
                    if (entity.worldX == gp.objectManagement.blockList.get(i).worldX &&
                            entity.worldY == gp.objectManagement.blockList.get(i).worldY + 32) {
                        entity.setCollision(true);
                    }
                    break;
                case "down":
                    if (entity.worldX == gp.objectManagement.blockList.get(i).worldX &&
                            entity.worldY == gp.objectManagement.blockList.get(i).worldY - 96) {
                        entity.setCollision(true);
                    }
                    break;
                case "left":
                    if (entity.worldX == gp.objectManagement.blockList.get(i).worldX + 64 &&
                            entity.worldY == gp.objectManagement.blockList.get(i).worldY - 32) {
                        entity.setCollision(true);
                    }
                    break;
                case "right":
                    if (entity.worldX == gp.objectManagement.blockList.get(i).worldX - 64 &&
                            entity.worldY == gp.objectManagement.blockList.get(i).worldY - 32) {
                        entity.setCollision(true);
                    }
                    break;
                }
            }
        }
    }

    public void checkBlock (Enemy enemy) { //Duy
        for (Block block: gp.objectManagement.blockList) {
            switch (enemy.getDirection()) {
                case "up":

                    if (enemy.getBound().intersects(block.getBound())) {
                        enemy.worldY+= enemy.getSpeed();
                        enemy.setCollision(true);
                    }
                    break;
                case "down":
                    if (enemy.getBound().intersects(block.getBound())) {
                        enemy.setCollision(true);
                        enemy.worldY-= enemy.getSpeed();
                    }
                    break;
                case "left":
                    if (enemy.getBound().intersects(block.getBound())) {
                        enemy.setCollision(true);
                        enemy.worldX+=enemy.getSpeed();
                    }
                    break;
                case "right":
                    if (enemy.getBound().intersects(block.getBound())) {
                        enemy.setCollision(true);
                        enemy.worldX-=enemy.getSpeed();
                    }
                    break;
            }
        }
    }
}
