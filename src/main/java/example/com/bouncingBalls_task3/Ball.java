package example.com.bouncingBalls_task3;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ball {
    private Component canvas;
    private static final int XSIZE = 10;
    private static final int YSIZE = 10;
    private int x;
    private int y = 0;
    private int dx = 0; // рух строго вниз
    private int dy = 2;

    public Ball(Component c, int startX) {
        this.canvas = c;
        this.x = startX; // розташовуємо в ряд
    }

    public void draw(Graphics2D g2, Color color) {
        g2.setColor(color);
        g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
    }

    public void move() {
        y += dy;

        // Bounce from top wall
        if (y <= 0) {
            y = 0;
            dy = -dy;
        }

        // Bounce from bottom wall
        if (y + YSIZE >= this.canvas.getHeight()) {
            y = this.canvas.getHeight() - YSIZE;
            dy = -dy;
        }

        this.canvas.repaint();
    }
}
