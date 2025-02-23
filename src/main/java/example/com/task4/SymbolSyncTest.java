package example.com.task4;

public class SymbolSyncTest implements Runnable {
    private final char symbol;
    private final Sync sync;
    private final int controlValue;

    public SymbolSyncTest(char s, Sync permission, int control) {
        symbol = s;
        this.sync = permission;
        this.controlValue = control;
    }
    @Override
    public void run() {
        while(!sync.shouldStop()){
            sync.waitAndChange(controlValue, symbol);

        }
    }
}
