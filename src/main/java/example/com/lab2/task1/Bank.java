package example.com.lab2.task1;
import java.util.concurrent.locks.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.*;

class Bank {
    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;

    // Об'єкти синхронізації
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition sufficientFunds = lock.newCondition();
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock writeLock = rwLock.writeLock();
    private final Semaphore semaphore = new Semaphore(1);
    private final AtomicIntegerArray atomicAccounts;

    public Bank(int n, int initialBalance) {
        accounts = new int[n];
        atomicAccounts = new AtomicIntegerArray(n);
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = initialBalance;
            atomicAccounts.set(i, initialBalance);
        }
        ntransacts = 0;
    }

//    public void transfer(int from, int to, int amount) {
//        accounts[from] -= amount;
//        accounts[to] += amount;
//        ntransacts++;
//    }

    // --- Метод 1: synchronized + wait()/notifyAll() ---

//    public synchronized void transfer(int from, int to, int amount) throws InterruptedException {
//        while (accounts[from] < amount)
//            wait();  // Очікуємо, поки буде достатньо коштів
//        accounts[from] -= amount;
//        accounts[to] += amount;
//        ntransacts++;
//        notifyAll();  // Сповіщаємо інші потоки
//    }


    // --- Метод 2: ReentrantLock + Condition ---
//    public void transfer(int from, int to, int amount) throws InterruptedException {
//        lock.lock();
//        try {
//            while (accounts[from] < amount)
//                sufficientFunds.await();  // Очікуємо на кошти
//            accounts[from] -= amount;
//            accounts[to] += amount;
//            ntransacts++;
//            sufficientFunds.signalAll();  // Сповіщаємо інші потоки
//        } finally {
//            lock.unlock();
//        }
//    }


    // --- Метод 3: ReadWriteLock ---
    public void transfer(int from, int to, int amount) throws InterruptedException {
        writeLock.lock();
        try {
            while (accounts[from] < amount)
                Thread.sleep(10); // Імітація очікування
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
        } finally {
            writeLock.unlock();
        }
    }


    public void test() {
        int sum = 0;
        for (int account : accounts)
            sum += account;
        String threadName = Thread.currentThread().getName();

        // Print transaction count and total sum with thread info
        System.out.println("[" + threadName + "] Transactions: " + ntransacts + " | Sum: " + sum);
    }

    public int size() {
        return accounts.length;
    }
}
