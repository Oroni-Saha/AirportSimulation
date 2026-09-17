# ✈️ Airport Simulation

A Java-based concurrent airport simulation developed to demonstrate multithreading, synchronization, shared-resource management, and thread coordination.

The simulation models multiple airplanes arriving at an airport and concurrently performing operations such as landing, gate assignment, passenger handling, refueling, aircraft servicing, and takeoff.

## 📌 Overview

The airport operates with limited shared resources:

- 1 runway
- 3 gates
- 1 refueling truck
- Maximum of 3 airplanes on the ground
- 6 airplanes entering the simulation
- Emergency landing handling

Each airplane runs as an independent thread and competes for shared airport resources. Synchronization is used to coordinate access and prevent multiple airplanes from using exclusive resources simultaneously.

## 🚀 Features

- Concurrent simulation of multiple airplanes
- Air Traffic Control (ATC) coordination
- Synchronized runway access
- Dynamic gate allocation
- Shared refueling truck
- Airport ground-capacity control
- Emergency landing scenario
- Concurrent aircraft servicing operations
- Passenger disembarking and embarking
- Randomized aircraft arrival times
- Thread waiting and notification
- Simulation statistics and final sanity checks

## 🧵 Concurrent Programming Concepts

The project demonstrates several Java concurrency concepts.

### Threads

Each `Airplane` extends the Java `Thread` class, allowing multiple aircraft to operate concurrently.

Additional threads are created while an aircraft is at its gate for:

- Passenger disembarking
- Aircraft refueling
- Cleaning and restocking

These operations execute concurrently and are synchronized using `join()` before the aircraft continues with boarding and departure.

### Synchronization

Java's `synchronized` methods are used to protect shared airport resources and coordinate access between competing airplane threads.

### wait() and notifyAll()

When a resource is unavailable, threads wait instead of continuously checking its state.

`wait()` is used when:

- The airport has reached its maximum ground capacity
- No gates are available
- The runway is occupied
- The refueling truck is being used

`notifyAll()` wakes waiting threads when a resource becomes available again.

### Shared Resources

The simulation coordinates access to several shared resources:

| Resource | Availability |
| --- | --- |
| Runway | 1 |
| Gates | 3 |
| Refueling Truck | 1 |
| Maximum planes on ground | 3 |

## 🛬 Simulation Flow

Each airplane follows this general sequence:

1. Enter airport airspace
2. Request landing permission from ATC
3. Wait if airport capacity has been reached
4. Acquire the runway
5. Land
6. Release the runway
7. Receive an available gate
8. Coast to the assigned gate
9. Dock at the gate
10. Perform concurrent ground operations:
   - Passenger disembarking
   - Refueling
   - Cleaning and restocking
11. Wait for all ground operations to complete
12. Board new passengers
13. Undock from the gate
14. Release the gate
15. Coast to the runway
16. Request takeoff permission
17. Acquire the runway
18. Take off
19. Release the runway

## 🚨 Emergency Landing

The simulation includes an emergency scenario involving `Plane-5`, which experiences a simulated fuel shortage.

The emergency aircraft is given priority when requesting landing permission, demonstrating how exceptional events can be incorporated into a concurrent system.

## 🏗️ Project Structure

```text
AirportSimulation/
├── src/
│   └── airportsimulation/
│       ├── AirportSimulation.java
│       ├── Airplane.java
│       ├── ATC.java
│       ├── Runway.java
│       ├── Gate.java
│       ├── RefuelTruck.java
│       └── Statistics.java
├── nbproject/
├── build.xml
├── manifest.mf
├── .gitignore
└── README.md

## 🧩 Main Components

### AirportSimulation

The main entry point of the application. It initializes the airport resources, creates six airplanes, introduces randomized arrival delays, triggers the emergency scenario, waits for all airplane threads to complete, and generates the final statistics report.

### Airplane

Represents each aircraft as an independent Java thread. Each airplane follows its complete lifecycle from entering the airspace and landing to ground servicing and eventual takeoff.

### ATC

Coordinates airport operations including:

- Landing permission
- Airport ground capacity
- Gate assignment
- Gate release
- Takeoff permission

Synchronized methods and `wait()` / `notifyAll()` are used to coordinate aircraft competing for limited airport resources.

### Runway

Represents the airport's single shared runway. Synchronized methods ensure that only one airplane can use the runway at a time for landing or takeoff.

### Gate

Represents an individual airport gate and maintains whether the gate is occupied or available.

### RefuelTruck

Represents the airport's single shared refueling truck. If the truck is already being used, another aircraft must wait until it becomes available.

### Statistics

Collects and reports simulation information including:

- Total airplanes served
- Total passengers boarded
- Maximum waiting time
- Minimum waiting time
- Average waiting time
- Final gate status

## 📊 Simulation Output

During execution, the console displays the actions performed by the different aircraft threads, including landing requests, runway access, gate allocation, passenger operations, refueling, cleaning, and takeoff.

At the end of the simulation, a statistics report is generated. Since passenger numbers and aircraft arrival delays are randomized, the exact results can vary between runs.

Example:

```text
AIRPORT STATISTICS REPORT

--- Sanity Check ---
Gate-1: EMPTY (OK)
Gate-2: EMPTY (OK)
Gate-3: EMPTY (OK)
All gates are empty. Sanity check PASSED!

--- Flight Statistics ---
Total planes served: 6
Total passengers boarded: 139

--- Waiting Time Statistics ---
Maximum waiting time: 7231 ms
Minimum waiting time: 0 ms
Average waiting time: 2233 ms
```

## 🛠️ Technologies Used

- Java
- Java Threads
- Object-Oriented Programming
- Thread Synchronization
- `wait()` and `notifyAll()`
- Thread `join()`
- Apache Ant
- NetBeans

## ▶️ How to Run

### Using the Command Line

Clone the repository:

```bash
git clone https://github.com/Oroni-Saha/AirportSimulation.git
```

Move into the project directory:

```bash
cd AirportSimulation
```

Compile the Java source files:

```bash
javac -d out src/airportsimulation/*.java
```

Run the simulation:

```bash
java -cp out airportsimulation.AirportSimulation
```

### Using NetBeans

1. Open NetBeans.
2. Select **File → Open Project**.
3. Select the `AirportSimulation` directory.
4. Build the project.
5. Run `AirportSimulation.java`.

## 🎓 Project Purpose

This project was developed as part of a **Concurrent Programming assignment** to demonstrate the practical application of concurrency concepts in Java.

The simulation focuses on thread creation, synchronization, inter-thread communication, resource contention, and coordination of multiple threads accessing limited shared resources.

## 👤 Author

**Oroni Saha**

GitHub: [@Oroni-Saha](https://github.com/Oroni-Saha)
