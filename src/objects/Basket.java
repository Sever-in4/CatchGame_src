package objects;

import java.awt.*;

public class Basket {
    private int x, y;
    private final int width = 60, height = 20;

    public Basket(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int dx) {
        x += dx;
        if (x < 0) x = 0;
        if (x > 440) x = 440;
    }

    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}