public class RegularQueue<T> {
    private Object[] array;
    private int front;
    private int rear;
    private int capacity;
    private int size;
    private boolean isDynamic;

    public int size() {
        return this.size;
    }

    public RegularQueue(int capacity, boolean isDynamic) {
        this.capacity = capacity;
        this.array = new Object[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
        this.isDynamic = isDynamic;
    }

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

    public T poll() {
        if (size == 0) return null;
        T data = (T) array[front];
        front = (front + 1) % capacity;
        size--;
        return data;
    }

    public T peek() {
        return (size == 0) ? null : (T) array[front];
    }

    public void clear() {
        front = rear = size = 0;
    }
}
