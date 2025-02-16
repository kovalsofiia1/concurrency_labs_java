package example.com.lab1.task5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Counter {
    private int count = 0;
    private final Object lock = new Object();
    private final Lock reentrantLock = new ReentrantLock();

    // 1. Несинхронізовані методи (демонстрація гонки потоків)
    public void increment() {
        count++;
    }

    public void decrement() {
        count--;
    }

    // 2. Синхронізовані методи
    public synchronized void synchronizedIncrement() {
        count++;
    }

    public synchronized void synchronizedDecrement() {
        count--;
    }

    // 3. Синхронізований блок
    public void blockSynchronizedIncrement() {
        synchronized (lock) {
            count++;
        }
    }

    public void blockSynchronizedDecrement() {
        synchronized (lock) {
            count--;
        }
    }

    // 4. Використання ReentrantLock
    public void lockIncrement() {
        reentrantLock.lock();
        try {
            count++;
        } finally {
            reentrantLock.unlock();
        }
    }

    public void lockDecrement() {
        reentrantLock.lock();
        try {
            count--;
        } finally {
            reentrantLock.unlock();
        }
    }

    public int getCount() {
        return count;
    }
}
