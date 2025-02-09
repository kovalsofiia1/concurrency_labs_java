package example.com.bouncingBalls_task4;

import example.com.bouncingBalls_task4.Ball;

public class BallThread implements Runnable {
    private Ball b;

    public BallThread(Ball ball){
        b = ball;
    }
    @Override
    public void run(){
        try{
            for(int i=1; i<10000; i++){
                b.move();
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                Thread.sleep(5);

            }
        } catch(InterruptedException ex){

        }
    }
}

//public class BallThread extends Thread {
//    private example.com.bouncingBalls_task5.Ball b;
//
//    public BallThread(Ball ball){
//        b = ball;
//    }
//    @Override
//    public void run(){
//        try{
//            for(int i=1; i<10000; i++){
//                b.move();
//                System.out.println("Thread name = "
//                        + Thread.currentThread().getName());
//                Thread.sleep(5);
//
//            }
//        } catch(InterruptedException ex){
//
//        }
//    }
//}
