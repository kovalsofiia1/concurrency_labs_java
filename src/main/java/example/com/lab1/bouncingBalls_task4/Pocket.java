package example.com.lab1.bouncingBalls_task4;

import java.awt.*;

public class Pocket {
    private int x, y, size;

    public Pocket(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public boolean contains(int ballX, int ballY, int ballSize) {
        int centerX = ballX + ballSize / 2;
        int centerY = ballY + ballSize / 2;
        return centerX >= x && centerX <= x + size && centerY >= y && centerY <= y + size;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(x, y, size, size);
    }
}
