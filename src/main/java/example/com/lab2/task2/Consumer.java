
package example.com.lab2.task2;

import java.util.Random;

public class Consumer implements Runnable {
    private Drop drop;
    private int receivedCount = 0;

    public Consumer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        Random random = new Random();
        for (String message = drop.take(); !message.equals("DONE"); message = drop.take()) {
            System.out.format("MESSAGE RECEIVED: %s%n", message);
            receivedCount++;

            try {
                Thread.sleep(random.nextInt(10));
            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Total messages received: " + receivedCount);
    }
}
