package example.com.lab1.task5;


public class CounterTest {
    private static final int ITERATIONS = 100000;

    public static void main(String[] args) throws InterruptedException {
        CounterTest test = new CounterTest();

        System.out.println("=== Not sync ===");
        test.testCounterWithoutSync();

        System.out.println("=== Sync method ===");
        test.testCounterWithSynchronizedMethods();

        System.out.println("=== Sync block ===");
        test.testCounterWithSynchronizedBlock();

        System.out.println("=== ReentrantLock ===");
        test.testCounterWithLock();
    }

    private void testCounterWithoutSync() throws InterruptedException {
        Counter counter = new Counter();
        runTest(counter, false, false, false);
        System.out.println("Result: " + counter.getCount());
    }

    private void testCounterWithSynchronizedMethods() throws InterruptedException {
        Counter counter = new Counter();
        runTest(counter, true, false, false);
        System.out.println("Result: " + counter.getCount());
    }

    private void testCounterWithSynchronizedBlock() throws InterruptedException {
        Counter counter = new Counter();
        runTest(counter, false, true, false);
        System.out.println("Result: " + counter.getCount());
    }

    private void testCounterWithLock() throws InterruptedException {
        Counter counter = new Counter();
        runTest(counter, false, false, true);
        System.out.println("Result: " + counter.getCount());
    }

    private void runTest(Counter counter, boolean syncMethod, boolean syncBlock, boolean useLock) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                if (syncMethod) counter.synchronizedIncrement();
                else if (syncBlock) counter.blockSynchronizedIncrement();
                else if (useLock) counter.lockIncrement();
                else counter.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                if (syncMethod) counter.synchronizedDecrement();
                else if (syncBlock) counter.blockSynchronizedDecrement();
                else if (useLock) counter.lockDecrement();
                else counter.decrement();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
