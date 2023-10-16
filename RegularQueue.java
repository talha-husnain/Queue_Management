/**
 * A generic Regular Queue implemented using a circular array.
 *
 * @param <T> The type of elements in this regular queue.
 * @author YourName
 */
public class RegularQueue<T> {
    private Object[] array;  // The circular array
    private int front;  // Index of the front element
    private int rear;  // Index one step past the rear element
    private int capacity;  // Capacity of the circular array
    private int size;  // Current size of the queue
    public int size() {
    return this.size;
}


    /**
     * Constructs a new RegularQueue with the given capacity.
     *
     * @param capacity The capacity of the queue.
     */
    public RegularQueue(int capacity) {
        this.capacity = capacity;
        this.array = new Object[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    /**
     * Adds an element to the rear of the queue.
     *
     * @param element The element to be added.
     * @return True if the element was added, false otherwise.
     */
    public boolean offer(T element) {
        if (size == capacity) return false;
        array[rear] = element;
        rear = (rear + 1) % capacity;
        size++;
        return true;
    }

    /**
     * Retrieves and removes the front element of this queue.
     *
     * @return The front element of the queue or null if the queue is empty.
     */
    public T poll() {
        if (size == 0) return null;
        T data = (T) array[front];
        front = (front + 1) % capacity;
        size--;
        return data;
    }

    /**
     * Retrieves but does not remove the front element of this queue.
     *
     * @return The front element of the queue or null if the queue is empty.
     */
    public T peek() {
        return (size == 0) ? null : (T) array[front];
    }

    /**
     * Removes all elements from the queue.
     */
    public void clear() {
        front = rear = size = 0;
    }
}
