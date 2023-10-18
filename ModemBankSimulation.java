// import java.io.BufferedWriter;
// import java.io.File;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.Random;
// import java.util.Scanner;

// /**
//  * This class simulates a modem bank, where users dial in and either get a modem or are placed in a waiting queue.
//  * If all modems are occupied, the user request can either be placed in a queue (if not full) or dropped.
//  */
// public class ModemBankSimulation {

//     // Event queue to handle user dial-in and hang-up events.
//     private PriorityQueue<Event> eventQueue;

//     // Queue to handle users who are waiting for a modem.
//     private RegularQueue<Integer> waitingQueue;

//     // Total number of modems.
//     private int numModems;

//     // Maximum size of the waiting queue. -1 indicates an infinite queue.
//     private int queueCapacity;

//     // Used to generate unique IDs for users.
//     private int nextUserId = 1;

//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);
//         String anotherSimulation;

//         // Loop to keep running simulations until the user decides to stop.
//         do {
//             // Collecting parameters for the simulation from the user.
//             System.out.println("Enter the length of the simulation:");
//             int lengthOfSimulation = scanner.nextInt();

//             System.out.println("Enter the average time between dial-in attempts:");
//             double averageDialInTime = scanner.nextDouble();

//             System.out.println("Enter the average connection time:");
//             int averageConnectionTime = scanner.nextInt();

//             System.out.println("Enter the number of modems in the bank:");
//             int numModems = scanner.nextInt();

//             System.out.println("Enter the size of the waiting queue or -1 for an infinite queue:");
//             int queueCapacity = scanner.nextInt();

//             // Running the simulation with the provided parameters.
//             ModemBankSimulation simulation = new ModemBankSimulation();
//             simulation.runSimulation(lengthOfSimulation, averageDialInTime, averageConnectionTime, numModems, queueCapacity);

//             System.out.println("Do you want to run another simulation? (yes/no)");
//             anotherSimulation = scanner.next();

//         } while (anotherSimulation.equalsIgnoreCase("yes"));

//         scanner.close();
//     }

//     /**
//      * This method runs a simulation based on the provided parameters.
//      */
//     public void runSimulation(int lengthOfSimulation, double averageDialInTime, int averageConnectionTime,
//                               int numModems, int queueCapacity) {
//         // Initializing event and waiting queues.
//         this.eventQueue = new PriorityQueue<>();
//         this.waitingQueue = new RegularQueue<>(queueCapacity == -1 ? 100 : queueCapacity, queueCapacity == -1);
//         this.numModems = numModems;
//         this.queueCapacity = queueCapacity;
//         this.nextUserId = 1;

//         Random rand = new Random();
//         int currentTime = 0;
//         int totalWaitTime = 0;
//         int totalModemBusyTime = 0;
//         int totalUsers = 0;

//         // Running the simulation for the specified length of time.
//         while (currentTime < lengthOfSimulation) {
//             // Randomly determining how many users are trying to dial in at the current time.
//             double lambda = averageDialInTime;
//             double p = Math.exp(-lambda);
//             int k = 0;
//             for (double product = rand.nextDouble(); product >= p; product *= rand.nextDouble()) {
//                 k++;
//             }

//             // Creating dial-in events for the users.
//             for (int j = 0; j < k; j++) {
//                 eventQueue.offer(new Event(nextUserId++, "DIAL_IN", currentTime, currentTime));
//                 totalUsers++;
//             }

//             // Processing events that occur at the current time.
//             Event event = eventQueue.poll();
//             while (event != null && event.getTime() <= currentTime) {
//                 if (event.getType().equals("DIAL_IN")) {
//                     // If the user dials in, we either assign a modem or place the user in the waiting queue.
//                     if (!waitingQueue.offer(event.getId())) {
//                         System.out.println("Queue is full. Dropping call from user " + event.getId());
//                     }
//                 } else if (event.getType().equals("HANG_UP")) {
//                     // If the user hangs up, we free up a modem.
//                     numModems++;
//                 }
//                 event = eventQueue.poll();
//             }

//             if (event != null) {
//                 eventQueue.offer(event);
//             }

//             // If we have free modems, assign them to users in the waiting queue.
//             while (numModems > 0 && waitingQueue.peek() != null) {
//                 int userId = waitingQueue.poll();
//                 totalWaitTime += (currentTime - userId);
//                 int connectionTime = rand.nextInt(averageConnectionTime) + 1;
//                 // Creating a hang-up event for the user after the connection time ends.
//                 eventQueue.offer(new Event(userId, "HANG_UP", currentTime + connectionTime, 0));
//                 totalModemBusyTime += connectionTime;
//                 numModems--;
//             }

//             currentTime++;
//         }
//         // Calculating and displaying results of the simulation.
//         double averageWaitTime = (totalUsers > 0) ? (double) totalWaitTime / totalUsers : 0;
//         double modemUsagePercentage = (numModems > 0) ? (double) totalModemBusyTime / (numModems * lengthOfSimulation) * 100 : 0;

//         System.out.println("Average wait time: " + averageWaitTime);
//         System.out.println("Modem usage percentage: " + modemUsagePercentage);
//         System.out.println("Remaining users in queue: " + waitingQueue.size());

//         // Writing the results to report.txt.
//         try (BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt", true))) {
//             File f = new File("report.txt");
//             if (f.length() == 0) {
//                 writer.write("  1- Length of simulation\n  2- Average time between dial-in attempts\n   3- Average connection time\n   4- Number of modems in the bank\n  5- Size of the waiting queue, -1 for an infinite queue\n  6- Averge wait time\n   7- Percentage of time modems were busy\n  8- Number of customers left in the waiting queue\n ");
//                 writer.write("    1   |    2   |    3   |    4   |    5   |     6     |     7     |    8\n");
//                 writer.write("-----------------------------------------------------------------------------\n");
//             }
//             writer.write(String.format("%5d   | %5.1f  | %5.1f  | %5d  | %5d  | %9.2f  | %9.2f  | %5d\n",
//                 lengthOfSimulation, averageDialInTime, (double) averageConnectionTime, numModems, queueCapacity,
//                 averageWaitTime, modemUsagePercentage, waitingQueue.size()));
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }







import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * This class simulates a modem bank, where users dial in and either get a modem
 * or are placed in a waiting queue.
 * If all modems are occupied, the user request can either be placed in a queue
 * (if not full) or dropped.
 */
public class ModemBankSimulation {

  // Event queue to handle user dial-in and hang-up events.
  private PriorityQueue<Event> eventQueue;

  // Queue to handle users who are waiting for a modem.
  private RegularQueue<Integer> waitingQueue;

  // Total number of modems.
  private int numModems;

  // Maximum size of the waiting queue. -1 indicates an infinite queue.
  private int queueCapacity;

  // Used to generate unique IDs for users.
  private int nextUserId = 1;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String anotherSimulation;

    // Loop to keep running simulations until the user decides to stop.
    do {
      // Collecting parameters for the simulation from the user.
      System.out.println("Enter the length of the simulation:");
      int lengthOfSimulation = scanner.nextInt();

      System.out.println("Enter the average time between dial-in attempts:");
      double averageDialInTime = scanner.nextDouble();

      System.out.println("Enter the average connection time:");
      int averageConnectionTime = scanner.nextInt();

      System.out.println("Enter the number of modems in the bank:");
      int numModems = scanner.nextInt();

      System.out.println("Enter the size of the waiting queue or -1 for an infinite queue:");
      int queueCapacity = scanner.nextInt();

      // Running the simulation with the provided parameters.
      ModemBankSimulation simulation = new ModemBankSimulation();
      simulation.runSimulation(lengthOfSimulation, averageDialInTime, averageConnectionTime, numModems, queueCapacity);

      System.out.println("Do you want to run another simulation? (yes/no)");
      anotherSimulation = scanner.next();

    } while (anotherSimulation.equalsIgnoreCase("yes"));

    scanner.close();
  }

  /**
   * This method runs a simulation based on the provided parameters.
   */
  public void runSimulation(int lengthOfSimulation, double averageDialInTime, int averageConnectionTime,
      int numModems, int queueCapacity) {
    // Initializing event and waiting queues.
    this.eventQueue = new PriorityQueue<>();
    this.waitingQueue = new RegularQueue<>(queueCapacity == -1 ? 100 : queueCapacity, queueCapacity == -1);
    this.numModems = numModems;
    this.queueCapacity = queueCapacity;
    this.nextUserId = 1;

    Random rand = new Random();
    int currentTime = 0;
    int totalWaitTime = 0;
    int totalModemBusyTime = 0;
    int totalUsers = 0;

    // Running the simulation for the specified length of time.
      while (currentTime < lengthOfSimulation) {
      // Randomly determining how many users are trying to dial in at the current time.
      double lambda = 1.0 / averageDialInTime;
      double p = Math.exp(-lambda);
      int k = 0;
      for (double product = rand.nextDouble(); product >= p; product *= rand.nextDouble()) {
          k++;
      }
      // Creating dial-in events for the users.
      for (int j = 0; j < k; j++) {
          if (numModems > 0) {
              // Schedule a HANG_UP event for the user based on the connection time.
              int connectionTime = rand.nextInt(averageConnectionTime) + 1;
              eventQueue.offer(new Event(nextUserId, "HANG_UP", currentTime + connectionTime, 0));
              totalModemBusyTime += connectionTime;
              numModems--;
          } else if (!waitingQueue.offer(nextUserId)) {
              System.out.println("customer " + nextUserId + " was denied service at time unit " + currentTime);
          }
          nextUserId++;
      }

      // Processing events that occur at the current time.
      // Processing events that occur at the current time.
      // Processing events that occur at the current time.
    Event event = eventQueue.poll();
        while (event != null && event.getTime() <= currentTime) {
            if (event.getType().equals("DIAL_IN")) {
                if (numModems > 0) { // If a modem is available
                    // Schedule a HANG_UP event for the user based on the connection time.
                    int connectionTime = rand.nextInt(averageConnectionTime) + 1;
                    eventQueue.offer(new Event(event.getId(), "HANG_UP", currentTime + connectionTime, 0));
                    totalModemBusyTime += connectionTime;
                    numModems--;
                } else if (!waitingQueue.offer(event.getId())) {
                    System.out.println("Queue is full. Dropping call from user " + event.getId());
                }
            } else if (event.getType().equals("HANG_UP")) {
                numModems++; // Free up a modem
                if (waitingQueue.peek() != null) { // If there are users in the waiting queue
                    int userId = waitingQueue.poll();
                    int waitTime = currentTime - userId;
                    totalWaitTime += waitTime;
                    int connectionTime = rand.nextInt(averageConnectionTime) + 1;
                    eventQueue.offer(new Event(userId, "HANG_UP", currentTime + connectionTime, 0));
                    totalModemBusyTime += connectionTime;
                    numModems--;
                }
            }
            event = eventQueue.poll();
        }

        if (event != null) {
            eventQueue.offer(event);
        }

        currentTime++;
    }

    double averageWaitTime = (totalUsers > 0) ? (double) totalWaitTime / totalUsers : 0;
    double modemUsagePercentage = (numModems > 0) ? (double) totalModemBusyTime / (numModems * lengthOfSimulation) * 100 : 0;

    System.out.println("Average wait time: " + averageWaitTime);
    System.out.println("Modem usage percentage: " + modemUsagePercentage);
    System.out.println("Remaining users in queue: " + waitingQueue.size());
    // Writing the results to report.txt.
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt", true))) {
      File f = new File("report.txt");
      if (f.length() == 0) {
        writer.write(
            "  1- Length of simulation\n  2- Average time between dial-in attempts\n   3- Average connection time\n   4- Number of modems in the bank\n  5- Size of the waiting queue, -1 for an infinite queue\n  6- Averge wait time\n   7- Percentage of time modems were busy\n  8- Number of customers left in the waiting queue\n ");
        writer.write("    1   |    2   |    3   |    4   |    5   |     6     |     7     |    8\n");
        writer.write("-----------------------------------------------------------------------------\n");
      }
      writer.write(String.format("%5d   | %5.1f  | %5.1f  | %5d  | %5d  | %9.2f  | %9.2f  | %5d\n",
          lengthOfSimulation, averageDialInTime, (double) averageConnectionTime, numModems, queueCapacity,
          averageWaitTime, modemUsagePercentage, waitingQueue.size()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

