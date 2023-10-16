/**
 * A generic Priority Queue implemented using an ordered singly linked list.
 *
 * @param <T> The type of elements in this priority queue.
 * @author YourName
 */
public class PriorityQueue<T extends Comparable<T>> {
    private Node head;  // The head node of the singly linked list

    // Inner class representing a Node in the singly linked list
    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * Adds an element to the queue based on its natural order.
     *
     * @param element The element to be added.
     */
    public void offer(T element) {
        Node newNode = new Node(element);
        if (head == null || head.data.compareTo(element) > 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null && current.next.data.compareTo(element) < 0) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
    }

    /**
     * Retrieves and removes the head of this queue.
     *
     * @return The head of the queue.
     */
    public T poll() {
        if (head == null) return null;
        T data = head.data;
        head = head.next;
        return data;
    }

    /**
     * Retrieves but does not remove the head of this queue.
     *
     * @return The head of the queue.
     */
    public T peek() {
        return (head == null) ? null : head.data;
    }

    /**
     * Removes all elements from the queue.
     */
    public void clear() {
        head = null;
    }
}
