/**
 * This class represents an event in the simulation.
 * Each event has an ID, a type, and a time of occurrence.
 * 
 * @author YourName
 */
public class Event implements Comparable<Event> {
    private int id;  // Event ID
    private String type;  // Type of the event ("Dial-In" or "Hang-Up")
    private int time;  // Time of occurrence

    /**
     * Constructs a new Event.
     * 
     * @param id   The ID of the event.
     * @param type The type of the event.
     * @param time The time of occurrence.
     */
    public Event(int id, String type, int time) {
        this.id = id;
        this.type = type;
        this.time = time;
    }

    /**
     * Compares this event with another event based on their time of occurrence.
     * 
     * @param other The other event.
     * @return A negative, zero, or positive integer based on the comparison.
     */
    @Override
    public int compareTo(Event other) {
        return Integer.compare(this.time, other.time);
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getTime() {
        return time;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
