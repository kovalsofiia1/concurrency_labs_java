package example.com.lab2.task4;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Sync {
    private int permission = 0;
    private int num = 0;//symbols printed
    private boolean stop = false;
    private final int numLines;
    private final int symbolsPerLine;

    public Sync(int lines, int symb ){
        this.numLines = lines;
        this.symbolsPerLine = symb;
    }
    public synchronized void waitAndChange(int control, char s){
        while (getPermission()!=control){
            try{
                wait();
            }catch(InterruptedException e){
                Logger.getLogger(Sync.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        System.out.print(s);
        permission++;
        num++;
        if(num%symbolsPerLine==0){
            System.out.println();
        }
        if(num+2==numLines*symbolsPerLine){
            stop=true;
        }
        notifyAll();
    }
    public void print(char s){
        num++;
        System.out.print(s);
        if(num%symbolsPerLine==0){
            System.out.println();
        }
        if(num+2==numLines*symbolsPerLine){
            stop=true;
        }
    }

    int getPermission() {
        return permission%3;
    }

    boolean shouldStop() {
        return stop;
    }
}
