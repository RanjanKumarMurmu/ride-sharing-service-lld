# Ride-Sharing Service (Low-Level Design)

An object-oriented, low-level design (LLD) implementation of a scalable **Ride-Sharing Service**. This repository contains the complete class architecture, state machines, and design patterns utilized to build a fully functional backend system matching passengers with nearby drivers.

---

## 📌 Project Overview
This project simulates the core functionalities of a modern ride-sharing application like Uber or Lyft. It showcases clean code practices, structural design patterns, and robust state management for lifecycle mapping of trips.

---

## 🎯 System Requirements

Based on the core system requirements, this platform supports:
1. **Passenger Requests & Driver Fulfillment:** Allows passengers to request rides and available drivers to accept or decline them.
2. **Location & Preference Specifications:** Passengers can specify pick-up locations, destinations, and a desired `RideType` (e.g., SUV, SEDAN, AUTO).
3. **Driver Availability Management:** Drivers can view active nearby requests and change their operational status.
4. **Proximity-Based Matching:** Implements proximity metrics to match ride requests with the closest available drivers.
5. **Dynamic Fare Calculation:** Dynamically computes trip fares based on distance, time, and the chosen `RideType`.
6. **Transaction Processing:** Handles simulated transaction workflows between passengers and drivers upon ride completion.
7. **Real-Time Notifications & Tracking:** Employs observer mechanics to provide ride updates to active stakeholders instantly.
8. **Concurrency & Consistency:** Architected to handle simultaneous requests and guarantee state integrity.

---

## 🏗️ Architecture & Design Patterns

### 1. State Pattern (Trip Lifecycle)
The lifecycle of a `Trip` is managed dynamically using the **State Pattern** through the `TripState` interface. This eliminates complex nested conditionals and encapsulates behavior within explicit state classes:
* `RequestedState`
* `AssignedState`
* `InProgressState`
* `CompletedState`

### 2. Strategy Pattern (Matching & Pricing)
* **`DriverMatchingStrategy`:** Decouples the algorithm used to find drivers. The default implementation is `NearestDriverMatchingStrategy`, which finds the closest operational drivers within a bounding radius (`MAX_DISTANCE_KM`).
* **`PricingStrategy`:** Allows modular calculations for fares.
  * `FlatRatePricingStrategy`: Applies standard base fares and flat rates.
  * `VehicleBasedPricingStrategy`: Scales base fares and per-kilometer rates based on the `RideType` enumeration map.

### 3. Observer Pattern (Real-Time Notifications)
The system leverages the **Observer Pattern** via the `TripObserver` interface. Objects like `Rider` and `Driver` implement this interface to listen for real-time state mutations in `Trip` subjects, trigger view updates, and log operations automatically.

---

## 📊 Class Diagram Topology

The structural relations captured in the design system map out as follows:

```
                  +-----------------------+
                  |  RideSharingService   |
                  +-----------------------+
                             |
         +-------------------+-------------------+
         |                   |                   |
         v                   v                   v
+----------------+  +----------------+  +-----------------+
|  Driver        |  |  User          |  |  Trip           |
+----------------+  +----------------+  +-----------------+
         |                   |                   |
         +---------+---------+                   v
                   v                     +-----------------+
               [ Observer ]------------->|   TripState     |
                                         +-----------------+
                                                 |
                                     +-----------+-----------+
                                     v                       v
                           +-------------------+   +-------------------+
                           | InProgressState   |   | CompletedState    |
                           +-------------------+   +-------------------+
```

---

## 🛠️ Class Specifications Reference

### Core Entities
* **`RideSharingService`:** The central controller facade tracking maps of drivers, riders, active trips, and injecting structural strategies.
* **`Trip`:** Holds metadata regarding status, unique IDs, locations, assigned pairs, and calculates final metrics via pricing strategies.
* **`Driver`:** Encapsulates driver profile, current geo-coordinates (`Location`), vehicle details, and online/offline status states.
* **`User` / `Rider`:** Contains customer histories, base profiles, and ride request triggers.

### Enums
* **`RideType`:** `SUV`, `SEDAN`, `AUTO`
* **`DriverStatus`:** `IN_TRIP`, `OFFLINE`, `ONLINE`
* **`TripStatus`:** `REQUESTED`, `ASSIGNED`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`

---

## 🚀 Setup & Execution

### Prerequisites
* Java Development Kit (JDK) 8 or higher.

### Run the Demo
Compile and run the main driver class inside your terminal environment to observe simulated matchings, fare lookups, and state mutations:

```bash
# Compile the project
javac *.java

# Execute the driver demo
java RideSharingServiceDemo
```
