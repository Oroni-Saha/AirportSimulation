package airportsimulation;

public class Runway {
    
    private boolean isBusy;
    
    public Runway() {
        this.isBusy = false;
    }
    
    // Acquire runway - wait if busy
    public synchronized void acquireRunway(String planeName) throws InterruptedException {
        while (isBusy) {
            System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Waiting for runway to be free.");
            wait();
        }
        isBusy = true;
        System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Runway acquired.");
    }
    
    // Release runway - notify waiting planes
    public synchronized void releaseRunway(String planeName) {
        isBusy = false;
        System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Runway released.");
        notifyAll();
    }
    
    public boolean isBusy() {
        return isBusy;
    }
}