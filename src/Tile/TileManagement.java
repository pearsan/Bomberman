package Tile;

import Controller.UtilityTool;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TileManagement {

    GamePanel gp;
    public Tile[] tiles;
    public Tile[][] tilesMap;
    public int mapTileNum[][];
    public int mapCol;
    public int mapRow;
    BufferedImage map;

    public TileManagement(GamePanel gp, BufferedImage image, BufferedImage map) {
        this.gp = gp;

        mapCol = image.getWidth();
        mapRow = image.getHeight();

        gp.maxWorldCol = mapCol;
        gp.maxWorldRow = mapRow;

        tiles = new Tile[20];
        mapTileNum = new int[gp.maxWorldCol + 1][gp.maxWorldRow + 1];
        tilesMap = new Tile[gp.maxWorldCol + 1][gp.maxWorldRow + 1];

        getTileImage();
        loadMap(image);

        this.map = map;
    }

    public void getTileImage() {


        //co
        setUp(0, false, 32, 32, 32, 32);

        //wall_1
        setUp(1, true,0, 0,32,32);

        //wall_2
        setUp(2, true,32, 0,32,32);

        //wall_3
        setUp(3, true,64, 0,32,32);

        //wall_4
        setUp(4, true,0, 32,32,32);

        //wall_5
        setUp(5, true,0, 64,32,32);

        //wall_6
        setUp(6, true,32, 64,32,32);

        //wall_7
        setUp(7, true,64, 64,32,32);

        //wall_8
        setUp(8, true,64, 32,32,32);

        //wall_9
        setUp(9, true,0, 96,32,32);
    }

    public void setUp(int index, boolean collision, int x, int y, int w, int h) {

        UtilityTool uTool = new UtilityTool();
        tiles[index] = new Tile();

        tiles[index].collision = collision;
    }

    public void setTiles(int index, boolean collision, boolean available) {
        tiles[index].collision = collision;

    }

    public void loadMap(BufferedImage image) {
        int x = 0;
        int y = 0;
        while (x < image.getWidth() && y < image.getHeight()) {

            int pixel = image.getRGB(x,y);
            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = pixel & 0xff;

            if ((red ==0 && green == 255 && blue == 255) || (red == 195 && green == 92 && blue == 92) || (red == 174 && green == 187 && blue == 23) ||
                    (red == 255 && green == 128 && blue == 0)) {
                //mapTileNum[x][y] = 1;
                tilesMap[x][y] = tiles[0];
            }
            if ((red == 0 && green == 0 && blue == 0) || (red == 255 && green == 255 && blue == 255) || (red == 204 && green == 204 && blue == 204)) {
                mapTileNum[x][y] = 0;
                tilesMap[x][y] = tiles[0];
            }
            if (red == 255 && green == 0 && blue == 0) {
                mapTileNum[x][y] = 2;
                tilesMap[x][y] = tiles[2];
            }
            if (red == 170) {
                mapTileNum[x][y] = 3;
                tilesMap[x][y] = tiles[3];
            }
            if (red == 234 && green == 255) {
                mapTileNum[x][y] = 4;
                tilesMap[x][y] = tiles[4];
            }
            if (red == 136) {
                mapTileNum[x][y] = 5;
                tilesMap[x][y] = tiles[5];
            }
            if (red == 0 && green == 255 && blue == 0) {
                mapTileNum[x][y] = 6;
                tilesMap[x][y] = tiles[6];
            }
            if (red == 0 && green == 234 && blue == 255) {
                mapTileNum[x][y] = 7;
                tilesMap[x][y] = tiles[7];
            }
            if (red == 234 && green == 0 && blue == 255) {
                mapTileNum[x][y] = 8;
                tilesMap[x][y] = tiles[8];
            }
            if (red == 0 && green == 0 && blue == 255) {
                mapTileNum[x][y] = 9;
                tilesMap[x][y] = tiles[9];
            }
            x += 1;
            if (x == image.getWidth()) {
                x = 0;
                y++;
            }
        }

    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

            //camera
            int worldX = 0;
            int worldY = 0;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

//            //stop camera
            if (gp.player.screenX > gp.player.worldX) {
                screenX = worldX;
            }
            if (gp.player.screenY > gp.player.worldY) {
                screenY = worldY;
            }
            int rightOffset = gp.screenWidth - gp.player.screenX;
            if (rightOffset > (mapCol * gp.tileSize) - gp.player.worldX) {
                screenX = gp.screenWidth - ((mapCol * gp.tileSize) - worldX);
            }
            int bottomOffset = gp.screenHeight - gp.player.screenY;
            if (bottomOffset > (mapRow * gp.tileSize) - gp.player.worldY) {
                screenY = gp.screenHeight - ((mapRow * gp.tileSize) - worldY);
            }

                g2.drawImage(map, screenX, screenY , null);
    }
}
