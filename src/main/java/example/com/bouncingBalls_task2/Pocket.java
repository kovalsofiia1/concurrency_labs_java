package example.com.bouncingBalls_task2;

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



//package example.com.bouncingBalls_task2;
//
//import java.awt.*;
//
//public class Pocket {
//    private int x, y, size;
//
//    public Pocket(int x, int y, int size) {
//        this.x = x;
//        this.y = y;
//        this.size = size;
//    }
//
//    public boolean contains(int ballX, int ballY, int ballSize) {
//        return ballX + ballSize / 2 >= x && ballX + ballSize / 2 <= x + size &&
//                ballY + ballSize / 2 >= y && ballY + ballSize / 2 <= y + size;
//    }
//
//    public void draw(Graphics2D g2) {
//        g2.setColor(Color.BLACK);
//        g2.fillRect(x, y, size, size);
//    }
//
//    public int getSize() {
//        return size;
//    }
//
//    public void printInfo() {
//        System.out.println("Pocket at (" + x + ", " + y + ") with size " + size);
//    }
//}
