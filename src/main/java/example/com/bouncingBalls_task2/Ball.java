package example.com.bouncingBalls_task2;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Random;

public class Ball {
    private Component canvas;
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    private int x = 0;
    private int y = 0;
    private int dx = 2;
    private int dy = 2;
    private boolean running = true;
    private List<Pocket> pockets; // Лунки

    public Ball(Component c, List<Pocket> pockets, int pocketSize) {
        this.canvas = c;
        this.pockets = pockets;

        if (Math.random() < 0.5) {
            x = new Random().nextInt(pocketSize, this.canvas.getWidth()-pocketSize);
            y = 0;
        } else {
            x = 0;
            y = new Random().nextInt(pocketSize, this.canvas.getHeight()-pocketSize);
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void draw(Graphics2D g2) {
        if (running) {
            g2.setColor(Color.darkGray);
            g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
        }
    }

    public void move() {
        if (!running) return; // Якщо кулька вже "зникла", не рухаємось

        x += dx;
        y += dy;

        // Відбивання від стін
        if (x < 0) {
            x = 0;
            dx = -dx;
        }
        if (x + XSIZE >= this.canvas.getWidth()) {
            x = this.canvas.getWidth() - XSIZE;
            dx = -dx;
        }
        if (y < 0) {
            y = 0;
            dy = -dy;
        }
        if (y + YSIZE >= this.canvas.getHeight()) {
            y = this.canvas.getHeight() - YSIZE;
            dy = -dy;
        }

        // Перевірка на потрапляння в лунку
        for (Pocket pocket : pockets) {
            if (pocket.contains(x, y, XSIZE)) {
                running = false; // Кулька зникла
                return;
            }
        }

        this.canvas.repaint();
    }
}



//package example.com.bouncingBalls_task2;
//
//import java.awt.*;
//import java.awt.geom.Ellipse2D;
//import java.util.Random;
//
//public class Ball {
//    private Component canvas;
//    private static final int XSIZE = 20;
//    private static final int YSIZE = 20;
//    private int x = 0;
//    private int y= 0;
//    private int dx = 2;
//    private int dy = 2;
//
//
//    public Ball(Component c){
//        this.canvas = c;
//
//
//        if(Math.random()<0.5){
//            x = new Random().nextInt(this.canvas.getWidth());
//            y = 0;
//        }else{
//            x = 0;
//            y = new Random().nextInt(this.canvas.getHeight());
//        }
//    }
//
//    public static void f(){
//        int a = 0;
//    }
//
//    public void draw (Graphics2D g2){
//        g2.setColor(Color.darkGray);
//        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));
//
//    }
//
//    public void move(){
//        x+=dx;
//        y+=dy;
//        if(x<0){
//            x = 0;
//            dx = -dx;
//        }
//        if(x+XSIZE>=this.canvas.getWidth()){
//            x = this.canvas.getWidth()-XSIZE;
//            dx = -dx;
//        }
//        if(y<0){
//            y=0;
//            dy = -dy;
//        }
//        if(y+YSIZE>=this.canvas.getHeight()){
//            y = this.canvas.getHeight()-YSIZE;
//            dy = -dy;
//        }
//        this.canvas.repaint();
//    }
//
//}
