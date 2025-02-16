package example.com.bouncingBalls_task2;

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
        this.setTitle("Bounce program");
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
                Ball b = new Ball(canvas, canvas.getPockets(), canvas.getPocketSize());
                canvas.add(b);

                BallThread runnable = new BallThread(b, BounceFrame.this);
                Thread thread = new Thread(runnable);
                thread.start();
            }
        });

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

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

