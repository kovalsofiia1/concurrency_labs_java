package example.com.bouncingBalls_task2;

public class BallThread implements Runnable {
    private Ball b;
    private BounceFrame frame;

    public BallThread(Ball ball, BounceFrame frame) {
        this.b = ball;
        this.frame = frame;
    }

    @Override
    public void run() {
        try {
            while (b.isRunning()) {
                b.move();
                Thread.sleep(5);
            }
            System.out.println("Ball disappeared. Thread finished: " + Thread.currentThread().getName());
            frame.incrementCounter(); // Оновлення лічильника зниклих кульок
        } catch (InterruptedException ex) {
        }
    }
}
