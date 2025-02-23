package example.com.task4;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Sync {
    private boolean permission = true;
    private int num = 0;//symbols printed
    private boolean stop = false;
    private final int numLines;
    private final int symbolsPerLine;

    public Sync(int lines, int symb ){
        this.numLines = lines;
        this.symbolsPerLine = symb;
    }
    public synchronized void waitAndChange(boolean control, char s){
        while (getPermission()!=control){
            try{
                wait();
            }catch(InterruptedException e){
                Logger.getLogger(Sync.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        System.out.print(s);
        permission = !permission;
        num++;
        if(num%symbolsPerLine==0){
            System.out.println();
        }
        if(num+1==numLines*symbolsPerLine){
            stop=true;
        }
        notifyAll();
    }

    boolean getPermission() {
        return permission;
    }

    boolean shouldStop() {
        return stop;
    }
}
