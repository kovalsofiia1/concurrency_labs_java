package example.com.task4;

public class PrintSymbol {

    public static void main(String[] args) throws InterruptedException {
        int symbolsPerLine = 99;
        int numLines = 99;
        Sync permission = new Sync(numLines, symbolsPerLine);
        int controlValue = 0;

        Thread thread1 = new Thread(new SymbolSyncTest('|', permission, controlValue) );
        Thread thread2 = new Thread(new SymbolSyncTest('\\', permission, ++controlValue) );
        Thread thread3 = new Thread(new SymbolSyncTest('/', permission, ++controlValue) );

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
