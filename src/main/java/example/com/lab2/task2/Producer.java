
package example.com.lab2.task2;

import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;
    private int dataSize;

    public Producer(Drop drop, int dataSize) {
        this.drop = drop;
        this.dataSize = dataSize;
    }

    public void run() {
        Random random = new Random();

        for (int i = 1; i <= dataSize; i++) {
            drop.put(String.valueOf(i));  // Передаємо число як рядок
            try {
                Thread.sleep(random.nextInt(10)); // Зменшена затримка
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        drop.put("DONE"); // Маркер завершення
    }
}
