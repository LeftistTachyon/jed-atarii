package producerconsumer;

import java.util.LinkedList;

public class ProducerConsumer {
    public static void main(String[] args) throws InterruptedException {
        final Market market = new Market();
        
        Thread t1 = new Thread(() -> {
            try {
                market.produce();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        
        Thread t2 = new Thread(() -> {
            try {
                market.consume();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
    }
    
    static class Market {
        LinkedList<Integer> list = new LinkedList<>();
        int capacity = 2;
        
        public void produce() throws InterruptedException {
            int value = 0;
            while(true) {
                synchronized(this) {
                    while(list.size()==capacity)
                        wait();
                    System.out.println("Producer produced " + value);
                    list.add(value++);
                    notify();
                    Thread.sleep(1000);
                }
            }
        }
        
        public void consume() throws InterruptedException {
            while(true) {
                synchronized(this) {
                    while(list.isEmpty())
                        wait();
                    int val = list.removeFirst();
                    System.out.println("Consumer consumed " + val);
                    notify();
                    Thread.sleep(1000);
                }
            }
        }
    }
}