package example.com.bouncingBalls_task4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    private ArrayList<Ball> balls = new ArrayList<>(); // Store created balls

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce Program");
        this.canvas = new BallCanvas();
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        JButton buttonCreate = new JButton("Create");
        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");

        // Button to create balls but not start them yet
        buttonCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ball b = new Ball(canvas);
                balls.add(b);  // Store the ball for later
                canvas.add(b);
                canvas.repaint();
                System.out.println("Ball created.");
            }
        });

        // Button to start balls one by one with join()
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {  // Run in a separate thread
                    for (Ball ball : balls) {
                        System.out.println("Ball thread created.");
                        BallThread runnable = new BallThread(ball);
                        Thread thread = new Thread(runnable);
                        thread.start();
                        try {
                            thread.join(); // Wait for the previous ball to finish
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    System.out.println("All balls completed.");
                }).start();
            }
        });

        // Button to stop the program
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(buttonCreate);
        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);
        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}



//package example.com.bouncingBalls_task4;
//
//import example.com.bouncingBalls_task4.Ball;
//import example.com.bouncingBalls_task4.BallCanvas;
//import example.com.bouncingBalls_task4.BallThread;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class BounceFrame extends JFrame {
//    private BallCanvas canvas;
//    public static final int WIDTH = 450;
//    public static final int HEIGHT = 350;
//    public BounceFrame() {
//        this.setSize(WIDTH, HEIGHT);
//        this.setTitle("Bounce programm");
//        this.canvas = new BallCanvas();
//        System.out.println("In Frame Thread name = "
//                + Thread.currentThread().getName());
//        Container content = this.getContentPane();
//        content.add(this.canvas, BorderLayout.CENTER);
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setBackground(Color.lightGray);
//        JButton buttonStart = new JButton("Start");
//        JButton buttonStop = new JButton("Stop");
//        buttonStart.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                Ball b = new Ball(canvas);
//                canvas.add(b);
//
//                BallThread runnable = new BallThread(b);
//                Thread thread = new Thread(runnable);
//                thread.start();
//                System.out.println("Thread name = " +
//                        thread.getName());
//            }
//        });
//        buttonStop.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                System.exit(0);
//            }
//        });
//
//
//        buttonPanel.add(buttonStart);
//        buttonPanel.add(buttonStop);
//
//        content.add(buttonPanel, BorderLayout.SOUTH);
//    }
//}