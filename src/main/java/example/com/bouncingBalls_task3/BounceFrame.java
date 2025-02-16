package example.com.bouncingBalls_task3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    public static final int WIDTH = 700;
    public static final int HEIGHT = 400;
    private static final int LOW_PRIORITY_BALLS = 300; // Кількість синіх кульок
    private static final int HIGH_PRIORITY_BALLS = 1;
    private static final int BALL_BLUE_SPACING = WIDTH/LOW_PRIORITY_BALLS;
    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce program");
        this.canvas = new BallCanvas();
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        JButton buttonManyBlue = new JButton("Many blue");
        JButton buttonManyRed = new JButton("Many red");
        JButton buttonStop = new JButton("Stop");

        buttonManyBlue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int xPos = 0; // Початкова позиція по X

                for (int i = 0; i < LOW_PRIORITY_BALLS; i++) {
                    Ball b = new Ball(canvas, xPos);
                    canvas.add(b, Color.BLUE);
                    BallThread runnable = new BallThread(b);
                    Thread thread = new Thread(runnable);
                    thread.setPriority(Thread.MIN_PRIORITY);
                    thread.start();
                    xPos += BALL_BLUE_SPACING;
                }



                int RED_SPACING = WIDTH/(HIGH_PRIORITY_BALLS+1);
                xPos = RED_SPACING;
                // Створюємо одну червону кульку (високий пріоритет)
                for (int i = 0; i < HIGH_PRIORITY_BALLS; i++) {

                    Ball redBall = new Ball(canvas, xPos);
                    canvas.add(redBall, Color.RED);
                    BallThread redRunnable = new BallThread(redBall);
                    Thread redThread = new Thread(redRunnable);
                    redThread.setPriority(Thread.MAX_PRIORITY);
                    redThread.start();
                    xPos += RED_SPACING;
                }
            }
        });

        buttonManyRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int xPos = 0; // Початкова позиція по X

                for (int i = 0; i < LOW_PRIORITY_BALLS; i++) {
                    Ball b = new Ball(canvas, xPos);
                    canvas.add(b, Color.RED);
                    BallThread runnable = new BallThread(b);
                    Thread thread = new Thread(runnable);
                    thread.setPriority(Thread.MAX_PRIORITY);
                    thread.start();
                    xPos += BALL_BLUE_SPACING;
                }



                int RED_SPACING = WIDTH/(HIGH_PRIORITY_BALLS+1);
                xPos = RED_SPACING;
                // Створюємо одну червону кульку (високий пріоритет)
                for (int i = 0; i < HIGH_PRIORITY_BALLS; i++) {

                    Ball redBall = new Ball(canvas, xPos);
                    canvas.add(redBall, Color.BLUE);
                    BallThread redRunnable = new BallThread(redBall);
                    Thread redThread = new Thread(redRunnable);
                    redThread.setPriority(Thread.MIN_PRIORITY);
                    redThread.start();
                    xPos += RED_SPACING;
                }
            }
        });


        buttonStop.addActionListener(e -> System.exit(0));

        buttonPanel.add(buttonManyBlue);
        buttonPanel.add(buttonManyRed);
        buttonPanel.add(buttonStop);
        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}
