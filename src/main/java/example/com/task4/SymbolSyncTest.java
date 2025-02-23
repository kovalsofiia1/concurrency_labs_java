package example.com.task4;

public class SymbolSyncTest implements Runnable {
    private final char symbol;
    private final Sync sync;
    private final boolean controlValue;

    public SymbolSyncTest(char s, Sync permission, boolean control) {
        symbol = s;
        this.sync = permission;
        this.controlValue = control;
    }
    @Override
    public void run() {
        while(!sync.shouldStop()){
//            if(sync.getPermission()==controlValue){
//                System.out.print(symbol);
//                sync.changePermission();
//            }
            sync.waitAndChange(controlValue, symbol);

        }
    }
}
