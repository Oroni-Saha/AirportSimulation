package airportsimulation;

public class RefuelTruck {
    
    private boolean isBusy;
    
    public RefuelTruck() {
        this.isBusy = false;
    }
    
    // Acquire the refuel truck - wait if busy
    public synchronized void acquireTruck(String planeName) throws InterruptedException {
        while (isBusy) {
            System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Waiting for refuel truck.");
            wait();
        }
        isBusy = true;
        System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Refuel truck acquired.");
    }
    
    // Release the refuel truck - notify waiting planes
    public synchronized void releaseTruck(String planeName) {
        isBusy = false;
        System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Refuel truck released.");
        notifyAll();
    }
    
    public boolean isBusy() {
        return isBusy;
    }
}