package airportsimulation;

import java.util.ArrayList;
import java.util.List;

public class Statistics {
    
    private int totalPlanesServed;
    private int totalPassengersBoarded;
    private List<Long> waitingTimes;
    
    public Statistics() {
        this.totalPlanesServed = 0;
        this.totalPassengersBoarded = 0;
        this.waitingTimes = new ArrayList<>();
    }
    
    // Add a plane's waiting time (in milliseconds)
    public synchronized void addWaitingTime(long waitingTime) {
        waitingTimes.add(waitingTime);
    }
    
    // Record a plane being served
    public synchronized void planeLanded() {
        totalPlanesServed++;
    }
    
    // Record passengers boarded
    public synchronized void addPassengersBoarded(int passengers) {
        totalPassengersBoarded += passengers;
    }
    
    // Print final statistics report
    public void printStatistics(Gate[] gates) {
        System.out.println("\n========================================");
        System.out.println("         AIRPORT STATISTICS REPORT      ");
        System.out.println("========================================");
        
        // Sanity check - all gates should be empty
        System.out.println("\n--- Sanity Check ---");
        boolean allEmpty = true;
        for (Gate gate : gates) {
            if (gate.isEmpty()) {
                System.out.println("Gate-" + gate.getGateNumber() + ": EMPTY (OK)");
            } else {
                System.out.println("Gate-" + gate.getGateNumber() + ": OCCUPIED (ERROR!)");
                allEmpty = false;
            }
        }
        
        if (allEmpty) {
            System.out.println("All gates are empty. Sanity check PASSED!");
        } else {
            System.out.println("WARNING: Some gates are still occupied!");
        }
        
        // Print statistics
        System.out.println("\n--- Flight Statistics ---");
        System.out.println("Total planes served: " + totalPlanesServed);
        System.out.println("Total passengers boarded: " + totalPassengersBoarded);
        
        // Waiting time stats
        if (!waitingTimes.isEmpty()) {
            long max = Long.MIN_VALUE;
            long min = Long.MAX_VALUE;
            long total = 0;
            
            for (long time : waitingTimes) {
                if (time > max) max = time;
                if (time < min) min = time;
                total += time;
            }
            
            long average = total / waitingTimes.size();
            
            System.out.println("\n--- Waiting Time Statistics ---");
            System.out.println("Maximum waiting time: " + max + " ms");
            System.out.println("Minimum waiting time: " + min + " ms");
            System.out.println("Average waiting time: " + average + " ms");
        }
        
        System.out.println("========================================\n");
    }
}