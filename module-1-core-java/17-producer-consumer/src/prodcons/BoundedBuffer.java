package prodcons;

import java.util.ArrayDeque;
import java.util.Queue;

// A bounded buffer: producers wait when it is full, consumers wait when it is empty.
public class BoundedBuffer {

    private final Queue<Integer> items = new ArrayDeque<>();
    private final int capacity;
    private boolean closed = false;
    private int maxSize = 0;

    public BoundedBuffer(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1.");
        }
        this.capacity = capacity;
    }

    // waits while the buffer is full
    public synchronized void put(int item) throws InterruptedException {
        // while (not if): after waking up, the condition must be checked again
        while (items.size() == capacity && !closed) {
            wait();
        }
        if (closed) {
            throw new IllegalStateException("Buffer is closed.");
        }
        items.add(item);
        if (items.size() > maxSize) {
            maxSize = items.size();
        }
        notifyAll();   // wake waiting consumers (and producers)
    }

    // waits while the buffer is empty, returns null when the buffer is closed AND empty
    public synchronized Integer take() throws InterruptedException {
        while (items.isEmpty() && !closed) {
            wait();
        }
        if (items.isEmpty()) {
            return null;   // closed and nothing left: end of stream
        }
        int item = items.remove();
        notifyAll();   // wake waiting producers (and consumers)
        return item;
    }

    // no more items will be added; items already inside can still be taken
    public synchronized void close() {
        closed = true;
        notifyAll();   // wake everyone who is waiting so they can finish
    }

    public synchronized int size() {
        return items.size();
    }

    // the highest number of items the buffer ever held at one time
    public synchronized int maxSizeSeen() {
        return maxSize;
    }

    public int capacity() {
        return capacity;
    }
}