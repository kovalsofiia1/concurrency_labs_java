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

    public void transfer(int from, int to, int amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0)
            test();
    }

    // --- Метод 1: Synchronized + wait()/notifyAll() ---
    public synchronized void transferSynchronized(int from, int to, int amount) {
        try {
            while (accounts[from] < amount)
                wait();  // Очікуємо, поки буде достатньо коштів
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            if (ntransacts % NTEST == 0)
                test();
            notifyAll();  // Сповіщаємо інші потоки
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Відновлюємо статус потоку
            System.err.println("transferSynchronized interrupted: " + e.getMessage());
        }
    }

    // --- Метод 2: ReentrantLock + Condition ---
    public void transferWithLock(int from, int to, int amount) {
        lock.lock();
        try {
            while (accounts[from] < amount)
                sufficientFunds.await();  // Очікуємо на кошти
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            if (ntransacts % NTEST == 0)
                test();
            sufficientFunds.signalAll();  // Сповіщаємо інші потоки
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("transferWithLock interrupted: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    // --- Метод 3: ReadWriteLock ---
    public void transferWithReadWriteLock(int from, int to, int amount) {
        writeLock.lock();
        try {
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            if (ntransacts % NTEST == 0)
                test();
        } finally {
            writeLock.unlock();
        }
    }

    // --- Метод 4: AtomicIntegerArray ---
    public void transferWithAtomic(int from, int to, int amount) {
        try {
            while (true) {
                int fromBalance = atomicAccounts.get(from);
                if (fromBalance < amount)
                    return;  // Недостатньо коштів

                if (atomicAccounts.compareAndSet(from, fromBalance, fromBalance - amount)) {
                    atomicAccounts.addAndGet(to, amount);
                    break;
                }
            }
            ntransacts++;
            if (ntransacts % NTEST == 0)
                test();
        } catch (Exception e) {
            System.err.println("transferWithAtomic error: " + e.getMessage());
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
