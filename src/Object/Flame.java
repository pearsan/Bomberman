package Object;

import Main.GamePanel;

import java.awt.*;

public class Flame extends SuperObject {
    private int maxRadius;
    public int explosionTime = 25;
    private int tempX, tempY;
    public boolean isCollidingNearBomb = false;
    public FlameSegment[] flameSegments = new FlameSegment[0];

    public Flame(GamePanel gp, int x, int y, String direction, int maxRadius) {
        super(gp);
        this.worldX = x;
        this.worldY = y;
        tempX = x;
        tempY = y;
        collision = true;
        this.maxRadius = maxRadius;
        this.direction = direction;
        setSpeed(64);
        name = "Flame";
        createFlameSegments();
    }

    private void createFlameSegments() {
        flameSegments = new FlameSegment[calculateFlameDistance()];

        /**tempX, tempY là vị trí đặt bom.*/
        worldX = tempX;
        worldY = tempY;

        boolean last = false;

        int x = worldX;
        int y = worldY;

        for (int i = 0; i < flameSegments.length; i++) {
            if (i == flameSegments.length - 1 && !isCollidingNearBomb) last = true;

            switch (direction) {
                case "down": y += 64; break;
                case "right": x += 64; break;
                case "up": y -= 64; break;
                case "left": x -= 64; break;
            }
            flameSegments[i] = new FlameSegment(gp, x, y, direction, last);
        }
    }

    private int calculateFlameDistance() {
        int radius = 0;
        if(direction.equals("center")) return 1;

        int x = worldX;
        int y = worldY;

        while(radius < maxRadius) {
            switch (direction) {
                case "down": y += 64; break;
                case "right": x += 64; break;
                case "up": y -= 64; break;
                case "left": x -= 64; break;
            }

            if (gp.objectManagement.checkNearBomb(x, y)) {
                isCollidingNearBomb = true;
                break;
            }
            collision = false;
            gp.collisionChecker.checkTitle(this);
            gp.collisionChecker.checkBlock(this);
            if(collision) break;

            /**Gán để kiểm tra collision.*/
            worldX = x;
            worldY = y;

            ++radius;
        }
        return radius;
    }

    public void update() {
        for(FlameSegment fs: flameSegments) {
            fs.update();
        }
        explosionTime--;
    }

    @Override
    public void draw(Graphics2D g2) {
        for (int i = 0; i < flameSegments.length; i++) {
            flameSegments[i].draw(g2);
        }
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(worldX, worldY, solidArea.width , solidArea.height);
    }
}
