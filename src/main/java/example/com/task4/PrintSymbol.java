package example.com.task4;

public class PrintSymbol {

    public static void main(String[] args) throws InterruptedException {
        int symbolsPerLine = 100;
        int numLines = 10;
        Sync permission = new Sync(numLines, symbolsPerLine);

        Thread thread1 = new Thread(new SymbolSyncTest('-', permission, true) );
        Thread thread2 = new Thread(new SymbolSyncTest('+', permission, false) );

        thread1.start();
        thread2.start();
    }
}
