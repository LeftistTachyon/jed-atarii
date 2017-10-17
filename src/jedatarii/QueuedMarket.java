package jedatarii;

import java.util.LinkedList;

public class QueuedMarket<V> extends Market<V> {
    private LinkedList<V> queue;
    
    public QueuedMarket(){}

    @Override
    public synchronized V get() {
        if(!valSet) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("Interrupted");
            }
        }
        System.out.println("Got: " + queue.peekFirst());
        notify();
        return queue.pollFirst();
    }
    
    public synchronized LinkedList<V> getAll() {
        if(!valSet) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("Interrupted");
            }
        }
        System.out.println("Got: " + queue.toString());
        LinkedList<V> copy = new LinkedList<>(queue);
        queue = new LinkedList<>();
        notify();
        return copy;
    }
    
    @Override
    public synchronized void put(V v) {
        if(valSet) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("Interrupted.");
            }
        }
        queue.offer(v);
        valSet = true;
        System.out.println("Put: " + queue.peekLast());
        notify();
    }
    
    public synchronized void putAll(LinkedList<V> v) {
        if(valSet) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("Interrupted.");
            }
        }
        queue.addAll(v);
        valSet = true;
        System.out.println("Put: " + v.toString());
        notify();
    }
}
