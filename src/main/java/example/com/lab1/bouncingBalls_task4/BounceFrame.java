package example.com.lab1.bouncingBalls_task4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    private JLabel counterLabel;
    private int counter = 0;

    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce program - task 4");
        this.canvas = new BallCanvas();

        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");

        counterLabel = new JLabel("Balls disappeared: 0");

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Ball redBall = new Ball(canvas, canvas.getPockets(), canvas.getPocketSize(), Color.RED);
                    Ball blueBall = new Ball(canvas, canvas.getPockets(), canvas.getPocketSize(), Color.BLUE);
                    Ball greenBall = new Ball(canvas, canvas.getPockets(), canvas.getPocketSize(), Color.GREEN);

                    canvas.add(redBall);
                    canvas.add(blueBall);
                    canvas.add(greenBall);

                    BallThread redThread = new BallThread(redBall, BounceFrame.this, null);
                    Thread t1 = new Thread(redThread);
                    t1.start();

                    BallThread blueThread = new BallThread(blueBall, BounceFrame.this, t1);
                    Thread t2 = new Thread(blueThread);
                    t2.start();

                    BallThread greenThread = new BallThread(greenBall, BounceFrame.this, t2);
                    Thread t3 = new Thread(greenThread);
                    t3.start();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonStop.addActionListener(e -> System.exit(0));

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);
        buttonPanel.add(counterLabel);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    public synchronized void incrementCounter() {
        counter++;
        counterLabel.setText("Balls disappeared: " + counter);
        canvas.repaint();
    }
}
