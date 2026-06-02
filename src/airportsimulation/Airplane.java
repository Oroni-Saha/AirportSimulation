package airportsimulation;

import java.util.Random;

public class Airplane extends Thread {

    private final String planeName;
    private final ATC atc;
    private final boolean isEmergency;
    private final Random random;
    private final long arrivalTime;
    private int passengers;

    public Airplane(String planeName, ATC atc, boolean isEmergency) {
        super(planeName); // Set thread name to plane name
        this.planeName = planeName;
        this.atc = atc;
        this.isEmergency = isEmergency;
        this.random = new Random();
        this.arrivalTime = System.currentTimeMillis();
        this.passengers = random.nextInt(50) + 1; // 1 to 50 passengers
    }

    @Override
    public void run() {
        try {
            // Step 1: Request landing
            long waitStart = System.currentTimeMillis();
            atc.requestLanding(planeName, isEmergency);
            long waitEnd = System.currentTimeMillis();

            // Record waiting time
            atc.getStatistics().addWaitingTime(waitEnd - waitStart);

            // Step 2: Acquire runway to land
            atc.getRunway().acquireRunway(planeName);
            System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Landing.");
            Thread.sleep(2000); // Landing takes 2 seconds
            System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Landed.");
            atc.getRunway().releaseRunway(planeName);

            // Step 3: Assign gate
            Gate assignedGate = atc.assignGate(planeName);

            // Step 4: Coast to gate
            System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Coasting to Gate-" + assignedGate.getGateNumber() + ".");
            Thread.sleep(1500); // Coasting takes 1.5 seconds

            // Step 5: Dock at gate
            System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Docked at Gate-" + assignedGate.getGateNumber() + ".");

            // Step 6: All 3 concurrent operations
            // Thread 1 - Passengers disembarking
            // Thread 2 - Refuel (uses shared refuel truck)
            // Thread 3 - Cleaning + Restocking combined

            Thread disembarkThread = new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " : " + planeName + "'s Passengers: " + passengers + " disembarking from " + planeName + ".");
                    Thread.sleep(2000); // Disembarking takes 2 seconds
                    System.out.println(Thread.currentThread().getName() + " : " + planeName + "'s Passengers: All passengers disembarked.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, planeName + "-DisembarkThread");

            Thread refuelThread = new Thread(() -> {
                try {
                    atc.getRefuelTruck().acquireTruck(planeName);
                    System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Refueling started.");
                    Thread.sleep(3000); // Refueling takes 3 seconds
                    System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Refueling complete.");
                    atc.getRefuelTruck().releaseTruck(planeName);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, planeName + "-RefuelThread");

            Thread cleanRestockThread = new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Cleaning aircraft and restocking food/supplies.");
                    Thread.sleep(2500); // Cleaning + restocking takes 2.5 seconds
                    System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Cleaning and restocking complete.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, planeName + "-CleanRestockThread");

            // Start all 3 concurrently
            disembarkThread.start();
            refuelThread.start();
            cleanRestockThread.start();

            // Wait for all 3 to finish
            disembarkThread.join();
            refuelThread.join();
            cleanRestockThread.join();

            // Step 7: New passengers embark
            int newPassengers = random.nextInt(50) + 1;
            System.out.println(Thread.currentThread().getName() + " : " + planeName + "'s Passengers: " + newPassengers + " embarking onto " + planeName + ".");
            Thread.sleep(2000); // Embarking takes 2 seconds
            System.out.println(Thread.currentThread().getName() + " : " + planeName + "'s Passengers: All passengers embarked.");

            // Record statistics
            atc.getStatistics().addPassengersBoarded(newPassengers);
            atc.getStatistics().planeLanded();

            // Step 8: Undock
            System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Undocking from Gate-" + assignedGate.getGateNumber() + ".");
            Thread.sleep(1000); // Undocking takes 1 second

            // Step 9: Release gate
            atc.releaseGate(assignedGate, planeName);

            // Step 10: Coast to runway
            System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Coasting to runway.");
            Thread.sleep(1500); // Coasting takes 1.5 seconds

            // Step 11: Request takeoff
            atc.requestTakeoff(planeName);

            // Step 12: Acquire runway to take off
            atc.getRunway().acquireRunway(planeName);
            System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Taking off.");
            Thread.sleep(2000); // Takeoff takes 2 seconds
            System.out.println(Thread.currentThread().getName() + " : " + planeName + ": Took off successfully. Goodbye!");
            atc.getRunway().releaseRunway(planeName);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(planeName + ": Interrupted!");
        }
    }
}