package jedatarii;

public class Market<V> {
    private V v = null;
    private boolean valSet = false;
    
    public Market() {}
    
    public synchronized V get() {
        if(!valSet) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("Interrupted");
            }
        }
        System.out.println("Got: " + v);
        V temp = v;
        v = null;
        valSet = false;
        notify();
        return temp;
    }
    
    public synchronized void put(V v) {
        if(valSet) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("Interrupted.");
            }
        }
        this.v = v;
        valSet = true;
        System.out.println("Put: " + v);
        notify();
    }
}