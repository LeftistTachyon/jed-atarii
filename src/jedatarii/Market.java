package jedatarii;

public class Market {
    private int n;
    private boolean valSet = false;
    private Producer producer;
    private Consumer consumer;
    
    public Market() {}
    
    public Market(Producer p, Consumer c) {
        consumer = c;
        producer = p;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setConsumer(Consumer c) {
        this.consumer = c;
    }

    public void setProducer(Producer p) {
        this.producer = p;
    }
    
    public void startProducer() {
        producer.start();
    }
    
    public void startConsumer() {
        consumer.start();
    }
    
    public void startAll() {
        producer.start();
        consumer.start();
    }
    
    public synchronized int get() {
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
    
    public synchronized void put(int n) {
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
    
    abstract class Producer implements Runnable {
        private Market m;
        private Thread t;
        protected volatile boolean stop = false;

        public Producer(Market m) {
            this.m = m;
            t = new Thread(this, "Producer");
        }

        public void start() {
            stop = false;
            t.start();
        }
        
        public void stop() {
            stop = true;
        }

        @Override
        public abstract void run();
    }

    abstract class Consumer implements Runnable {
        private Market m;
        private Thread t;
        protected volatile boolean stop = false;
        
        public Consumer(Market m) {
            this.m = m;
            t = new Thread(this, "Consumer");
        }

        public void start() {
            stop = false;
            t.start();
        }
        
        public void stop() {
            stop = true;
        }

        @Override
        public abstract void run();
    }
}