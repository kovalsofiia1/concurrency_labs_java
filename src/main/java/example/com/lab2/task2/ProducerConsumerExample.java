
package example.com.lab2.task2;

public class ProducerConsumerExample {
    public static void main(String[] args) {
        int dataSize = 5000; // Змініть на 100, 1000 або 5000 для тестування
        Drop drop = new Drop();

        Thread producerThread = new Thread(new Producer(drop, dataSize));
        Thread consumerThread = new Thread(new Consumer(drop));

        producerThread.start();
        consumerThread.start();

    }
}
