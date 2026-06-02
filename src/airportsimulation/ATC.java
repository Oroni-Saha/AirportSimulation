package airportsimulation;

public class ATC {
    
    private final Gate[] gates;
    private final Runway runway;
    private final RefuelTruck refuelTruck;
    private final Statistics statistics;
    private int planesOnGround;
    private static final int MAX_PLANES_ON_GROUND = 3;
    
    public ATC(Gate[] gates, Runway runway, RefuelTruck refuelTruck, Statistics statistics) {
        this.gates = gates;
        this.runway = runway;
        this.refuelTruck = refuelTruck;
        this.statistics = statistics;
        this.planesOnGround = 0;
    }
    
    // Request landing permission - synchronized so only one plane requests at a time
    public synchronized void requestLanding(String planeName, boolean isEmergency) throws InterruptedException {
        
        // Emergency planes skip the queue and land immediately when runway is free
        if (isEmergency) {
            System.out.println(Thread.currentThread().getName() + " : " + planeName + ": EMERGENCY! Requesting emergency landing!");
            System.out.println(Thread.currentThread().getName() + " : ATC: EMERGENCY LANDING granted for " + planeName + "! Clearing runway!");
            return;
        }
        
        // Wait if airport is full (3 planes on ground already)
        while (planesOnGround >= MAX_PLANES_ON_GROUND) {
            System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Requesting Landing.");
            System.out.println(Thread.currentThread().getName() + " : ATC: Landing Permission Denied for " + planeName + ", Airport Full.");
            wait();
        }
        
        System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Requesting Landing.");
        System.out.println(Thread.currentThread().getName() + " : ATC: Landing permission granted for " + planeName + ".");
        planesOnGround++;
    }
    
    // Assign a gate to a plane - wait if no gates available
    public synchronized Gate assignGate(String planeName) throws InterruptedException {
        Gate assignedGate = null;
        
        // Wait until a gate is free
        while (assignedGate == null) {
            for (Gate gate : gates) {
                if (!gate.isOccupied()) {
                    assignedGate = gate;
                    break;
                }
            }
            if (assignedGate == null) {
                System.out.println(Thread.currentThread().getName() + " : ATC: No gates available for " + planeName + ". Waiting...");
                wait();
            }
        }
        
        assignedGate.occupy();
        System.out.println(Thread.currentThread().getName() + " : ATC: Gate-" + assignedGate.getGateNumber() + " assigned for " + planeName + ".");
        return assignedGate;
    }
    
    // Release gate when plane is done
    public synchronized void releaseGate(Gate gate, String planeName) {
        gate.free();
        System.out.println(Thread.currentThread().getName() + " : ATC: Gate-" + gate.getGateNumber() + " is now free after " + planeName + " departed.");
        planesOnGround--;
        notifyAll(); // Notify waiting planes that a spot is free
    }
    
    // Grant takeoff permission
    public synchronized void requestTakeoff(String planeName) {
        System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Requesting Taking off.");
        System.out.println(Thread.currentThread().getName() + " : ATC: Taking-off is granted for " + planeName + ". Runway is free.");
    }
    
    public Runway getRunway() {
        return runway;
    }
    
    public RefuelTruck getRefuelTruck() {
        return refuelTruck;
    }
    
    public Statistics getStatistics() {
        return statistics;
    }
}
    