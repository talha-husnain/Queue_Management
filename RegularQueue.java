/**
 * A custom implementation of a queue that supports both static and dynamic
 * (resizable) storage capacities.
 * 
 * @param <T> the type of elements held in this queue
 */
public class RegularQueue<T> {

  // Array to store the elements of the queue.
  private Object[] array;

  // Index for the front of the queue.
  private int front;

  // Index for the rear of the queue.
  private int rear;

  // Maximum number of elements the queue can hold.
  private int capacity;

  // Current number of elements in the queue.
  private int size;

  // Flag to indicate whether the queue should resize itself when full.
  private boolean isDynamic;

  /**
   * Returns the current number of elements in the queue.
   * 
   * @return the size of the queue
   */
  public int size() {
    return this.size;
  }

  /**
   * Constructor to initialize the queue with a given capacity and resize
   * behavior.
   * 
   * @param capacity  Initial capacity of the queue
   * @param isDynamic If true, the queue will resize itself when full; otherwise,
   *                  it will reject new elements.
   */
  public RegularQueue(int capacity, boolean isDynamic) {
    this.capacity = capacity;
    this.array = new Object[capacity];
    this.front = 0;
    this.rear = 0;
    this.size = 0;
    this.isDynamic = isDynamic;
  }

  /**
   * Adds an element to the rear of the queue.
   * If the queue is full and dynamic, it resizes itself. Otherwise, it rejects
   * the new element.
   * 
   * @param element Element to be added
   * @return true if the element was added, false otherwise
   */
  public boolean offer(T element) {
    if (size == capacity) {
      if (isDynamic) {
        resize();
      } else {
        return false;
      }
    }
    array[rear] = element;
    rear = (rear + 1) % capacity;
    size++;
    return true;
  }

  /**
   * Doubles the capacity of the queue.
   * Used when the queue is dynamic and is currently full.
   */
  private void resize() {
    int newCapacity = 2 * capacity;
    Object[] newArray = new Object[newCapacity];
    for (int i = 0; i < size; i++) {
      newArray[i] = array[(front + i) % capacity];
    }
    array = newArray;
    front = 0;
    rear = size;
    capacity = newCapacity;
  }

  /**
   * Removes and returns the front element of the queue.
   * Returns null if the queue is empty.
   * 
   * @return the front element, or null if the queue is empty
   */
  public T poll() {
    if (size == 0)
      return null;
    T data = (T) array[front];
    front = (front + 1) % capacity;
    size--;
    return data;
  }

  /**
   * Returns the front element of the queue without removing it.
   * Returns null if the queue is empty.
   * 
   * @return the front element, or null if the queue is empty
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
