package example.com.lab1.bouncingBalls_task4;

public class BallThread implements Runnable {
    private Ball ball;
    private BounceFrame frame;
    private Thread waitForThread;

    public BallThread(Ball ball, BounceFrame frame, Thread waitForThread) {
        this.ball = ball;
        this.frame = frame;
        this.waitForThread = waitForThread;
    }

    @Override
    public void run() {
        try {
            if (waitForThread != null) {
                waitForThread.join(); // Чекаємо завершення попереднього потоку
            }

            while (ball.isRunning()) {
                ball.move();
                Thread.sleep(7);
            }

            frame.incrementCounter();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
