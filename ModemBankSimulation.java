import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

public class ModemBankSimulation {
  private PriorityQueue<Event> eventQueue;
  private RegularQueue<Integer> waitingQueue;
  private int numModems;
  private int queueCapacity;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

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

    ModemBankSimulation simulation = new ModemBankSimulation();
    simulation.runSimulation(lengthOfSimulation, averageDialInTime, averageConnectionTime, numModems, queueCapacity);

    scanner.close();
  }

  public void runSimulation(int lengthOfSimulation, double averageDialInTime, int averageConnectionTime,
      int numModems, int queueCapacity) {
    this.eventQueue = new PriorityQueue<>();
    this.waitingQueue = new RegularQueue<>(queueCapacity == -1 ? Integer.MAX_VALUE : queueCapacity);
    this.numModems = numModems;
    this.queueCapacity = queueCapacity;

    Random rand = new Random();
    for (int i = 0; i < lengthOfSimulation; i++) {
      int numDialInEvents = (int) (rand.nextFloat() < averageDialInTime ? 1 : 0);
      for (int j = 0; j < numDialInEvents; j++) {
        eventQueue.offer(new Event(i, "DIAL_IN", i));  // Updated this line
      }
    }

    int currentTime = 0;
    int totalWaitTime = 0;
    int totalModemBusyTime = 0;
    int totalUsers = 0;

    while (currentTime < lengthOfSimulation) {
      Event event = eventQueue.poll();
      while (event != null && event.getTime() == currentTime) {
        if (event.getType().equals("DIAL_IN")) {
          totalUsers++;
          if (!waitingQueue.offer(totalUsers)) {
            System.out.println("Queue is full. Dropping call from user " + totalUsers);
          }
        } else if (event.getType().equals("HANG_UP")) {
          numModems++;
        }
        event = eventQueue.poll();
      }

      while (numModems > 0 && waitingQueue.peek() != null) {
        int userId = waitingQueue.poll();
        totalWaitTime += (currentTime - userId);
        int connectionTime = rand.nextInt(averageConnectionTime) + 1;
        eventQueue.offer(new Event(currentTime + connectionTime, "HANG_UP", currentTime + connectionTime));  // Updated this line
        totalModemBusyTime += connectionTime;
        numModems--;
      }

      currentTime++;
    }

    double averageWaitTime = (double) totalWaitTime / totalUsers;
    double modemUsagePercentage = (double) totalModemBusyTime / (numModems * lengthOfSimulation) * 100;

    System.out.println("Average wait time: " + averageWaitTime);
    System.out.println("Modem usage percentage: " + modemUsagePercentage);
    System.out.println("Remaining users in queue: " + waitingQueue.size());  // Updated this line

    try (BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt", true))) {
      writer.write(lengthOfSimulation + "\t" + averageDialInTime + "\t" + averageConnectionTime + "\t" +
          numModems + "\t" + queueCapacity + "\t" + averageWaitTime + "\t" + modemUsagePercentage + "\t" +
          waitingQueue.size() + "\n");  // Updated this line
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
