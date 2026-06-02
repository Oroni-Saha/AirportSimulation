package airportsimulation;

import java.util.Random;

public class AirportSimulation {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("========================================");
        System.out.println("    Asia Pacific Airport Simulation     ");
        System.out.println("========================================\n");

        // Create airport components
        Gate[] gates = {new Gate(1), new Gate(2), new Gate(3)};
        Runway runway = new Runway();
        RefuelTruck refuelTruck = new RefuelTruck();
        Statistics statistics = new Statistics();

        // Create ATC with all components
        ATC atc = new ATC(gates, runway, refuelTruck, statistics);

        // Create 6 planes total
        // Plane-5 will be the emergency plane (fuel shortage)
        Airplane[] planes = {
            new Airplane("Plane-1", atc, false),
            new Airplane("Plane-2", atc, false),
            new Airplane("Plane-3", atc, false),
            new Airplane("Plane-4", atc, false),
            new Airplane("Plane-5", atc, true),  // Emergency plane!
            new Airplane("Plane-6", atc, false)
        };

        Random random = new Random();

        // Start planes one by one with random delay (0, 1, or 2 seconds)
        for (int i = 0; i < planes.length; i++) {
            
            // Emergency plane (Plane-5) arrives when airport is congested
            // Planes 1-4 start first to create congestion
            if (i == 4) {
                System.out.println("\nMainThread : Simulating congested scenario...");
                System.out.println("MainThread : Plane-5 has a fuel shortage - EMERGENCY LANDING!\n");
                Thread.sleep(1000); // Small delay before emergency plane
            }

            planes[i].start();
            System.out.println("MainThread : " + planes[i].getName() + " has entered the airspace.");

            // Random delay between plane arrivals (0, 1, or 2 seconds)
            Thread.sleep(random.nextInt(2000));
        }

        // Wait for all planes to finish
        for (Airplane plane : planes) {
            plane.join();
        }

        // Print final statistics
        System.out.println("\nMainThread : All planes have departed. Generating report...");
        statistics.printStatistics(gates);

        System.out.println("MainThread : Simulation complete!");
    }
}