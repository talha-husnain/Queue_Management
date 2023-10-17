/**
 * Represents an event occurring in the modem bank simulation.
 * An event can be of different types such as "DIAL_IN" or "HANG_UP".
 * Each event has attributes like ID, type, occurrence time, and the time it
 * entered the system.
 * The class provides getters and setters for each attribute and implements
 * the Comparable interface to enable prioritizing events based on their
 * occurrence times.
 */
public class Event implements Comparable<Event> {

  // Unique identifier for the event to distinguish between multiple events.
  private int id;

  // Type of event, such as "DIAL_IN" or "HANG_UP", indicating the nature of the
  // event.
  private String type;

  // The specific time unit when this event is set to happen.
  private int time;

  // The time unit when this event was introduced into the system. Useful for
  // calculating wait times.
  private int entryTime;

  /**
   * Constructs a new Event with the given attributes.
   *
   * @param id        Unique identifier assigned to the event.
   * @param type      Describes the nature of the event - e.g., "DIAL_IN" means a
   *                  user is trying to connect.
   * @param time      Indicates when the event will occur in the simulation.
   * @param entryTime Specifies when the event was introduced into the system.
   */
  public Event(int id, String type, int time, int entryTime) {
    this.id = id;
    this.type = type;
    this.time = time;
    this.entryTime = entryTime;
  }

  /**
   * Provides natural ordering for the Event class based on the event's occurrence
   * time.
   * This is essential for prioritizing events in the simulation timeline.
   *
   * @param other Another event to compare this event against.
   * @return Negative value if this event is before the other, positive if after,
   *         and zero if they occur at the same time.
   */
  @Override
  public int compareTo(Event other) {
    return Integer.compare(this.time, other.time);
  }

  // Getter methods:

  /**
   * @return The unique identifier associated with this event.
   */
  public int getId() {
    return id;
  }

  /**
   * @return The type of this event, indicating its nature.
   */
  public String getType() {
    return type;
  }

  /**
   * @return The specific simulation time unit when this event will occur.
   */
  public int getTime() {
    return time;
  }

  /**
   * @return The simulation time unit when this event was first introduced into
   *         the system.
   */
  public int getEntryTime() {
    return entryTime;
  }

  // Setter methods:

  /**
   * Update the ID of this event.
   *
   * @param id The new identifier to be assigned to this event.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Change the type of this event.
   *
   * @param type The new type describing the nature of this event.
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Modify the time of occurrence for this event.
   *
   * @param time The new simulation time unit when this event should occur.
   */
  public void setTime(int time) {
    this.time = time;
  }

  /**
   * Update when this event was first introduced into the system.
   *
   * @param entryTime The new simulation time unit marking this event's
   *                  introduction.
   */
  public void setEntryTime(int entryTime) {
    this.entryTime = entryTime;
  }
}
