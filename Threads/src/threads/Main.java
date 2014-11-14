package threads;

/**
 * Created by stagiaire on 06/11/2014.
 */
public class Main {

    static class MyThread implements Runnable {

        @Override
        public void run() {
            int i = 0;
            while(!Thread.interrupted()) {
                i++;
                System.out.println("Thread iteration " + i + Thread.interrupted());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    break;
                    //e.printStackTrace();
                }
            }
        }
    }

    static class MyThread2 extends Thread {
        @Override
        public void run() {
            int i = 0;
            while(!isInterrupted()) {
                i++;
                System.out.println("Thread iteration " + i + Thread.interrupted());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    break;
                    //e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {

        Thread th = new MyThread2();
        th.start();

        Thread.sleep(2000);

        System.out.println("Interrupt");
        th.interrupt();

    }
}
