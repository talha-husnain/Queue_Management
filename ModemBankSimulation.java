import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class ModemBankSimulation {

  // Priority queue to store events
  private PriorityQueue<Event> eventQueue;
  // Regular queue to store waiting users
  private RegularQueue<Integer> waitingQueue;
  // Number of modems in the bank
  private int numModems;
  // Capacity of the waiting queue
  private int queueCapacity;
  // ID to be assigned to the next user
  private int nextUserId;
  // Total number of users in the simulation
  private int totalUsers;

  public static void main(String[] args) {
    // Scanner to read user inputs
    Scanner scanner = new Scanner(System.in);
    // Variable to store user choice for running another simulation
    String anotherSimulation;

    do {
      // Reading various simulation parameters from user
      System.out.println("Enter the length of the simulation:");
      int lengthOfSimulation = scanner.nextInt();
      System.out.println("Enter the average time between dial-in attempts:");
      double averageDialInTime = scanner.nextDouble();
      System.out.println("Enter the average connection time:");
      int averageConnectionTime = scanner.nextInt();
      System.out.println("Enter the number of modems in the bank:");
      int numModems = scanner.nextInt();
      int numModem = numModems;

      System.out.println("Enter the size of the waiting queue or -1 for an infinite queue:");
      int queueCapacity = scanner.nextInt();

      // Creating and running a simulation
      ModemBankSimulation simulation = new ModemBankSimulation();
      simulation.runSimulation(lengthOfSimulation,numModem, averageDialInTime, averageConnectionTime, numModems, queueCapacity);

      // Ask if user wants to run another simulation
      System.out.println("Do you want to run another simulation? (yes/no)");
      anotherSimulation = scanner.next();

    } while (anotherSimulation.equalsIgnoreCase("yes"));

    // Close the scanner
    scanner.close();
  }

  public void runSimulation(int lengthOfSimulation,int numModem, double averageDialInTime, int averageConnectionTime,
      int numModems, int queueCapacity) {
    // Initialize simulation parameters
    this.eventQueue = new PriorityQueue<>();
    this.waitingQueue = new RegularQueue<>(queueCapacity == -1 ? 100 : queueCapacity, queueCapacity == -1);
    this.numModems = numModems;
    this.queueCapacity = queueCapacity;
    this.nextUserId = 1;
    this.totalUsers = 0;

    // Initialize random number generator and simulation variables
    Random rand = new Random();
    int currentTime = 0;
    int totalWaitTime = 0;
    int totalModemBusyTime = 0;

    // Main simulation loop
    while (currentTime < lengthOfSimulation) {
      // Calculate number of dial-ins at the current time
      double lambda = 1.0 / averageDialInTime;
      double p = Math.exp(-lambda);
      int k = 0;
      for (double product = rand.nextDouble(); product >= p; product *= rand.nextDouble()) {
        k++;
      }

      // Generate k DIAL_IN events
      for (int j = 0; j < k; j++) {
        totalUsers++; // Increment totalUsers
        eventQueue.offer(new Event(nextUserId, "DIAL_IN", currentTime, 0)); // Generate DIAL_IN events
        nextUserId++;
      }

      // Process events for current time
      Event event = eventQueue.poll();
      while (event != null && event.getTime() == currentTime) {
        // Handle DIAL_IN events
        if (event.getType().equals("DIAL_IN")) {
          if (numModems > 0) {
            // Generate HANG_UP event if a modem is available
            int connectionTime = rand.nextInt(averageConnectionTime) + 1;
            eventQueue.offer(new Event(event.getId(), "HANG_UP", currentTime + connectionTime, 0));
            totalModemBusyTime += connectionTime;
            numModems--;
          } else if (!waitingQueue.offer(event.getId())) {
            // User denied service
            System.out.println("customer " + nextUserId + " was denied service at time unit " + currentTime);
          }
          // Handle HANG_UP events
        } else if (event.getType().equals("HANG_UP")) {
          numModems++;
          if (waitingQueue.peek() != null) {
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
    if (numModems == 0) {
        numModems = numModem ;
    }

    // Compute and display summary statistics
    double averageWaitTime = (double) (totalWaitTime*-1) / totalUsers;
    double modemUsagePercentage = (double) totalModemBusyTime / (numModems * lengthOfSimulation) * 100;
    System.out.println("Average wait time: " + averageWaitTime);
    System.out.println("Modem usage percentage: " + modemUsagePercentage);
    System.out.println("Remaining users in queue: " + waitingQueue.size());

    // Write results to a file
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
