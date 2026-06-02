package airportsimulation;

public class Gate {
    
    private final int gateNumber;
    private boolean isOccupied;
    
    public Gate(int gateNumber) {
        this.gateNumber = gateNumber;
        this.isOccupied = false;
    }
    
    // Returns gate number
    public int getGateNumber() {
        return gateNumber;
    }
    
    // Check if gate is occupied
    public boolean isOccupied() {
        return isOccupied;
    }
    
    // Mark gate as occupied
    public void occupy() {
        isOccupied = true;
    }
    
    // Mark gate as free
    public void free() {
        isOccupied = false;
    }
    
    // Check gate is empty (for sanity check at end)
    public boolean isEmpty() {
        return !isOccupied;
    }
}