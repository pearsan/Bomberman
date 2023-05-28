package Controller;

import Main.GamePanel;
import Object.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManagement {
    GamePanel gp;
    KeyHandler keyH;

    public ArrayList<SuperObject> obj = new ArrayList<>();
    public int currentBomb = 0;
    public int maxBombNum = 3;
    public int maxBombRadius = 1;
    public int currentBullets = 0;
    public boolean getSpeedItem = false;
    private int countFlame = 0;
    private boolean getHealItem = false;
    public Bomb previousBomb = null;
    private ArrayList<Item> waitingItem = new ArrayList<>();
    public ArrayList<Block> blockList = new ArrayList<>();

    public ObjectManagement(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
    }

    public void update() {
        updateBomb();
        updateItem();
        updateBullet();
    }

    private void updateItem() {//Su
        for (int i = 0; i < getWaitingItem().size(); i++) {
            if (getWaitingItem().get(i).waitingTime > 0) getWaitingItem().get(i).waitingTime--;
            else {
                obj.add(getWaitingItem().get(i));
                getWaitingItem().remove(i);
            }
        }

        for (int i = 0; i < obj.size(); i++) {
            int index = gp.collisionChecker.checkObject(obj.get(i));
            if (obj.get(i).getName().equals("Item")) {
                //System.out.println("I" + index);
                Item check = (Item) obj.get(i);
                check.update();
                if (index != 999) {
                    obj.remove(i);
                }

            }
        }

        //ListBlock
        for (int i = 0; i < blockList.size(); i++) {
            Block check = blockList.get(i);
            int index = gp.collisionChecker.checkObject(blockList.get(i));
            collideObj(index);
            blockList.get(i).update();
            if (check.destroyed) {
                if (check.destroyingTime > 0) {
                    check.destroyingTime--;
                } else blockList.remove(i);
            }
            if (index != 999 && !check.destroyed) {
                System.out.println("B" + index);
                if (blockList.get(i).portal) {
                    getWaitingItem().add(new Portal(gp, blockList.get(i).worldX, blockList.get(i).worldY)); //them portal
                } else {
                    Random random = new Random();
                    int func;

                    if (!getSpeedItem && !getHealItem) {
                        func = random.nextInt(20);
                    }
                    else if (getSpeedItem && getHealItem) {
                        func = random.nextInt(20) + 2;
                    } else {
                        func = random.nextInt(20) + 1;
                        if (func == 1) {
                            if (!getSpeedItem) func = 0;
                        }
                    }

                    if (func == 3) {
                        getWaitingItem().add(new BombItem(gp, blockList.get(i).worldX, blockList.get(i).worldY));
                    } else if (func == 2) {
                        getWaitingItem().add(new FlameItem(gp, blockList.get(i).worldX, blockList.get(i).worldY));
                        //countFlame++;
                    } else if (func == 4) {
                        getWaitingItem().add(new CrateItem(gp, blockList.get(i).worldX, blockList.get(i).worldY));
                    } else if (func == 0) {
                        getWaitingItem().add(new SpeedItem(gp, blockList.get(i).worldX, blockList.get(i).worldY));
                        getSpeedItem = true;
                    } else if (func == 1) {
                        getWaitingItem().add(new HealItem(gp, blockList.get(i).worldX, blockList.get(i).worldY));
                        getHealItem = true;
                    }
                }
                //blockList.remove(i);
                check.destroyed = true;
                check.setSpriteNum(3);

            }

        }
    }
    public void collideObj(int index) { //Duy
        if(index != 999) {
            String objName = gp.objectManagement.obj.get(index).getName();
            if(objName.equals("Bullet")) {
                gp.objectManagement.obj.remove(index);
            }
        }
    }

    private void updateBullet() {
        if(keyH.bulletPressed && currentBullets >0) {
            obj.add(new Bullet(gp));
            Bullet k = (Bullet) obj.get(obj.size() - 1);
            if(keyH.facingUp) k.up=true;
            if(keyH.facingDown) k.down = true;    //Duy
            if(keyH.facingRight) k.right = true;
            if(keyH.facingLeft) k.left = true;

            currentBullets--;
            keyH.bulletPressed = false;
        }
    }
    private void updateBomb() {
        updateExistingBombs(); //Su

        //Tuấn Anh
        //DROP BOMB
        if (keyH.bombPressed && currentBomb < maxBombNum) {
            int playerLeftWorldX = gp.player.worldX + gp.player.getSolidArea().x;
            int playerTopWorldY = gp.player.worldY + gp.player.getSolidArea().y;

            int bombTileCol = playerLeftWorldX / gp.tileSize;
            int bombTileRow = playerTopWorldY / gp.tileSize;
            //System.out.println(bombTileCol + " " + bombTileRow);

            int bombTileNum = gp.tileManagement.mapTileNum[bombTileCol][bombTileRow];
            if (previousBomb == null || previousBomb.worldX != (gp.player.worldX + 32) / 64 * 64 ||
                    previousBomb.worldY != (gp.player.worldY + 32) / 64 * 64) {
                //SET BOMB TO PLAYER POSITION
                obj.add(new Bomb(gp));
                obj.get(obj.size() - 1).worldX = (gp.player.worldX + 32) / 64 * 64;
                obj.get(obj.size() - 1).worldY = (gp.player.worldY + 32) / 64 * 64;
                //obj[currentObj].mapPosition = bombTileNum;
                //obj[currentObj].collision = true;
                currentBomb++;
                previousBomb = (Bomb) obj.get(obj.size() - 1);
                //gp.tileManagement.tiles[bombTileNum].available = false;
                //keyH.bombPressed = false;

                System.out.println(previousBomb.worldX);
                System.out.println(previousBomb.worldY);
            }
        }

        if (keyH.bombPressed) keyH.bombPressed = false;
        //System.out.println(currentBomb);
    }

    private void updateExistingBombs() {
        for (int i = 0; i < obj.size(); i++) {
            if (obj.get(i).getName().equals("Bomb")) {
                Bomb check = (Bomb) obj.get(i);
                check.update();
                /** explosionTime mặc định = 150 khi explode se trừ đi 1 -> 149.
                 * Đợi đến tick tiếp theo khi các bom đã check được nearBomb của mình
                 * đồng thời explosionTime bằng 148 thì xóa bomb đi và add Flame để
                 * render Flame -> Để Flame của 2 bomb gần nhau k bị chồng lên nhau.
                 */
                if (check.isExploded() && check.explosionTime == 148) {
                    obj.remove(i);
                    for (Flame flame : check.flames) {
                        obj.add(flame);
                    }
                    currentBomb--;
                    if (currentBomb == 0) previousBomb = null;

                }
            } else if (obj.get(i).getName().equals("Flame")) {
                Flame flame = (Flame) obj.get(i);
                flame.update();
                for (FlameSegment fs : flame.flameSegments) {
                    if (gp.player.getBound().intersects(fs.getBound())) {
                        if (gp.player.getPlayerLives() > 0) {
                            if (gp.player.getRelievingTime() == 100) {
                                /**Khi relievingTime == 100 <=> chưa bị thương
                                 * (va chạm với quái/ trong phạm vi bom nổ).
                                 */
                                gp.player.setPlayerLives(gp.player.getPlayerLives() - 1);
                                gp.player.setRelievingTime(gp.player.getRelievingTime() - 1);
                            }
                        } else gp.lose = true; //thua
                        System.out.println(gp.player.getPlayerLives());
                    }
                }

                if (flame.explosionTime <= 0) {
                    obj.remove(i);
                    gp.playStop(2);
                    gp.playSE(3);
                    if (currentBomb == 0) previousBomb = null;
                }
            }
        }
    }

    public boolean checkNearBomb(int x, int y) {
        for (int i = 0; i < obj.size(); i++) {
            if (obj.get(i).getName().equals("Bomb") &&
                    obj.get(i).worldX == x && obj.get(i).worldY == y) {
                Bomb check = (Bomb) obj.get(i);
                check.countdown = 0; // để cho explode
                return true;
            }
        }
        return false;
    }

    public void render(Graphics2D g2) {
        for (int i = 0; i < blockList.size(); i++) {
            blockList.get(i).draw(g2);
        }

        for (int i = 0; i < obj.size(); i++) {
            if (!obj.get(i).getName().equals("Item"))
                obj.get(i).draw(g2);
        }
        for (int i = 0; i < obj.size(); i++) {
            if (obj.get(i).getName().equals("Item"))
                obj.get(i).draw(g2);
        }

    }

    public ArrayList<Item> getWaitingItem() {
        return waitingItem;
    }

    public void setWaitingItem(ArrayList<Item> waitingItem) {
        this.waitingItem = waitingItem;
    }
}
