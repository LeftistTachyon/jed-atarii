package producerconsumer;

class Market {
    int n;
    boolean valSet = false;
    synchronized int get() {
        if(!valSet) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("Interrupted");
            }
        }
        System.out.println("Got: " + n);
        valSet = false;
        notify();
        return n;
    }
    
    synchronized void put(int n) {
        if(valSet) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("Interrupted.");
            }
        }
        this.n = n;
        valSet = true;
        System.out.println("Put: " + n);
        notify();
    }
}

abstract class Producer implements Runnable {
    Market m;
    Thread t;
    
    public Producer(Market m) {
        this.m = m;
        t = new Thread(this, "Producer");
    }
    
    public void start() {
        t.start();
    }
    
    @Override
    public abstract void run();
}

abstract class Consumer implements Runnable {
    Market m;
    Thread t;
    public Consumer(Market m) {
        this.m = m;
        t = new Thread(this, "Consumer");
    }
    
    public void start() {
        t.start();
    }
    
    @Override
    public abstract void run();
}

public class PandC {
    public static void main(String[] args) {
        Market m = new Market();
        new Producer(m) {
            @Override
            public void run() {
                int i = 0;
                while(true) {
                    m.put(i++);
                }
            }
        }.start();
        new Consumer(m) {
            @Override
            public void run() {
                while(true) {
                    m.get();
                }
            }
        }.start();
        System.out.println("Stop by pressing Crtl-C");
    }
}