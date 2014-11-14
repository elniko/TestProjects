package threads;

class AutoResetEvent {
    private boolean isSet;
    public AutoResetEvent(boolean initialState) {
        isSet=initialState;
    }
    public synchronized void await() throws InterruptedException {
        while(!isSet)
            wait();
        isSet=false;
    }
    public synchronized void set() {
        isSet=true;
        notifyAll();
    }
}
// тестовый поток, который по нажатию ENTER устанавливает событие
class TestThread implements Runnable {
    AutoResetEvent ev;
    TestThread(AutoResetEvent ev) {
        this.ev=ev;
    }
    public void run() {
        System.out.println("press ENTER to set event");
        try {
            while(System.in.read()!='\n');
            ev.set();
        }
        catch (Exception e) {
        }
    }
}
// демонстрация работы класса AutoResetEvent
class jfirts {
    public static void main(String[] args) {
        AutoResetEvent ev=new AutoResetEvent(false);
        Thread t1=new Thread(new TestThread(ev));
        t1.start();
        try {
            ev.await();
            System.out.println("event is set");
        }
        catch(Exception e){
        }
    }
}