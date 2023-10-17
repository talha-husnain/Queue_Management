/**
 * A generic Priority Queue implemented using an ordered singly linked list.
 * The elements in the priority queue are sorted in natural order (smallest to
 * largest).
 * This queue supports basic operations like offer, poll, peek, and clear.
 *
 * @param <T> The type of elements in this priority queue. The type T must be
 *            comparable.
 * @author YourName
 */
public class PriorityQueue<T extends Comparable<T>> {

  // The head node of the singly linked list representing the front of the
  // priority queue.
  private Node head;

  /**
   * Inner class representing a Node in the singly linked list.
   * Each Node contains data of type T and a reference to the next Node in the
   * list.
   */
  private class Node {
    T data; // Data element of the Node.
    Node next; // Reference to the next Node in the list.

    /**
     * Constructs a new Node with the given data.
     *
     * @param data Data element for the Node.
     */
    Node(T data) {
      this.data = data;
      this.next = null;
    }
  }

  /**
   * Inserts an element into the priority queue.
   * The position of the element is determined based on its natural order.
   * 
   * @param element The element to be inserted.
   */
  public void offer(T element) {
    Node newNode = new Node(element);
    // If the queue is empty or the head element is greater than the new element,
    // then insert the new node at the beginning.
    if (head == null || head.data.compareTo(element) > 0) {
      newNode.next = head;
      head = newNode;
    } else {
      // Otherwise, traverse the list to find the correct position for the new node.
      Node current = head;
      while (current.next != null && current.next.data.compareTo(element) < 0) {
        current = current.next;
      }
      newNode.next = current.next;
      current.next = newNode;
    }
  }

  /**
   * Removes and returns the element at the front of the priority queue.
   * Returns null if the queue is empty.
   *
   * @return The front element of the queue or null if empty.
   */
  public T poll() {
    if (head == null)
      return null;
    T data = head.data;
    head = head.next;
    return data;
  }

  /**
   * Retrieves the front element of the priority queue without removing it.
   * Returns null if the queue is empty.
   *
   * @return The front element of the queue or null if empty.
   */
  public T peek() {
    return (head == null) ? null : head.data;
  }

  /**
   * Removes all elements from the priority queue.
   * After a call to clear(), the queue will be empty.
   */
  public void clear() {
    head = null;
  }
}
