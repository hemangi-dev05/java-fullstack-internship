package prodcons;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProducerConsumerApp {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome! Producer-consumer with thread synchronization.");
        int choice;
        do {
            printMenu();
            choice = readInt("Choose an option (0-4): ");
            switch (choice) {
                case 1 -> simulate();
                case 2 -> blockingDemo();
                case 3 -> shutdownDemo();
                case 4 -> explain();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Please choose a number from 0 to 4.");
            }
            if (choice >= 1 && choice <= 4) {
                pause();
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("========= PRODUCER-CONSUMER MENU =========");
        System.out.println("  1. Run a producer-consumer simulation");
        System.out.println("  2. Blocking demo (full buffer and empty buffer)");
        System.out.println("  3. Shutdown demo (closing the buffer)");
        System.out.println("  4. Read the explanation");
        System.out.println("  0. Exit");
        System.out.println("==========================================");
    }

    private static void simulate() {
        int producers = readInt("Number of producers (1-10): ");
        int consumers = readInt("Number of consumers (1-10): ");
        int perProducer = readInt("Items per producer (1-50): ");
        int capacity = readInt("Buffer capacity (1-20): ");
        if (!inRange(producers, 1, 10, "Producers")
                || !inRange(consumers, 1, 10, "Consumers")
                || !inRange(perProducer, 1, 50, "Items per producer")
                || !inRange(capacity, 1, 20, "Buffer capacity")) {
            return;
        }

        BoundedBuffer buffer = new BoundedBuffer(capacity);
        AtomicInteger produced = new AtomicInteger();
        AtomicInteger consumed = new AtomicInteger();
        AtomicLong sumConsumed = new AtomicLong();
        AtomicInteger duplicates = new AtomicInteger();
        AtomicInteger orderViolations = new AtomicInteger();
        Set<Integer> seen = ConcurrentHashMap.newKeySet();
        int[] perConsumer = new int[consumers];   // each consumer writes only its own slot
        List<Thread> producerThreads = new ArrayList<>();
        List<Thread> consumerThreads = new ArrayList<>();

        for (int c = 0; c < consumers; c++) {
            int index = c;
            Thread t = new Thread(() -> {
                int[] last = new int[producers + 1];   // last sequence number seen from each producer
                try {
                    Integer item;
                    while ((item = buffer.take()) != null) {
                        consumed.incrementAndGet();
                        sumConsumed.addAndGet(item);
                        perConsumer[index]++;
                        if (!seen.add(item)) {
                            duplicates.incrementAndGet();
                        }
                        int from = item / 1000;   // item = producerNumber * 1000 + sequenceNumber
                        int seq = item % 1000;
                        if (seq <= last[from]) {
                            orderViolations.incrementAndGet();
                        }
                        last[from] = seq;
                        Thread.sleep(1);   // pretend each item takes time to process
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "consumer-" + (c + 1));
            consumerThreads.add(t);
            t.start();
        }

        for (int p = 1; p <= producers; p++) {
            int id = p;
            Thread t = new Thread(() -> {
                try {
                    for (int i = 1; i <= perProducer; i++) {
                        buffer.put(id * 1000 + i);
                        produced.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "producer-" + id);
            producerThreads.add(t);
            t.start();
        }

        joinAll(producerThreads);   // all items have been put
        buffer.close();             // tell consumers: nothing more is coming
        joinAll(consumerThreads);   // consumers drain the rest and finish

        int total = producers * perProducer;
        long expectedSum = 1000L * perProducer * producers * (producers + 1) / 2
                + (long) producers * perProducer * (perProducer + 1) / 2;
        boolean ok = produced.get() == total
                && consumed.get() == total
                && duplicates.get() == 0
                && orderViolations.get() == 0
                && sumConsumed.get() == expectedSum
                && buffer.size() == 0
                && buffer.maxSizeSeen() <= capacity;

        StringBuilder split = new StringBuilder();
        for (int c = 0; c < consumers; c++) {
            if (c > 0) {
                split.append(", ");
            }
            split.append("consumer-").append(c + 1).append("=").append(perConsumer[c]);
        }

        System.out.println();
        System.out.println("SIMULATION RESULT");
        System.out.println("  Producers x items : " + producers + " x " + perProducer + " = " + total);
        System.out.println("  Consumers         : " + consumers);
        System.out.println("  Buffer capacity   : " + capacity);
        System.out.println("  Produced          : " + produced.get());
        System.out.println("  Consumed          : " + consumed.get());
        System.out.println("  Duplicates        : " + duplicates.get());
        System.out.println("  Order violations  : " + orderViolations.get());
        System.out.println("  Checksum          : " + (sumConsumed.get() == expectedSum
                ? "OK (sum " + sumConsumed.get() + ")"
                : "WRONG (sum " + sumConsumed.get() + ", expected " + expectedSum + ")"));
        System.out.println("  Left in buffer    : " + buffer.size());
        System.out.println("  Max buffer size   : " + buffer.maxSizeSeen() + " (capacity " + capacity + ")");
        System.out.println("  Per consumer      : " + split + "  (varies each run)");
        System.out.println("  Check             : " + (ok ? "PASSED" : "FAILED"));
    }

    private static void blockingDemo() {
        try {
            fullBufferDemo();
            emptyBufferDemo();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Demo interrupted.");
        }
    }

    private static void fullBufferDemo() throws InterruptedException {
        System.out.println();
        System.out.println("BLOCKING DEMO 1: full buffer (capacity 2), one producer puts 5 items, nobody consumes yet");
        BoundedBuffer buffer = new BoundedBuffer(2);
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.put(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        sleep(300);
        System.out.println("  After 300 ms: buffer size " + buffer.size() + " of " + buffer.capacity()
                + ", producer thread state " + producer.getState());

        StringBuilder order = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            order.append(buffer.take()).append(' ');
        }
        producer.join(2000);
        System.out.println("  Consumed in order: " + order.toString().trim());
        System.out.println("  Producer finished: " + !producer.isAlive());
    }

    private static void emptyBufferDemo() throws InterruptedException {
        System.out.println();
        System.out.println("BLOCKING DEMO 2: empty buffer, one consumer asks for an item");
        BoundedBuffer buffer = new BoundedBuffer(2);
        AtomicInteger received = new AtomicInteger(-1);
        Thread consumer = new Thread(() -> {
            try {
                Integer value = buffer.take();
                if (value != null) {
                    received.set(value);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        consumer.start();
        sleep(300);
        System.out.println("  After 300 ms: buffer size " + buffer.size()
                + ", consumer thread state " + consumer.getState());
        buffer.put(42);
        consumer.join(2000);
        System.out.println("  Consumer received: " + received.get() + ", finished: " + !consumer.isAlive());
    }

    private static void shutdownDemo() {
        try {
            System.out.println();
            System.out.println("SHUTDOWN DEMO (close)");
            BoundedBuffer buffer = new BoundedBuffer(5);
            buffer.put(1);
            buffer.put(2);
            buffer.put(3);
            buffer.close();
            buffer.close();   // closing twice must be harmless
            System.out.println("  Closed twice with no error. Items still inside: " + buffer.size());

            try {
                buffer.put(4);
                System.out.println("  put after close: ACCEPTED (wrong)");
            } catch (IllegalStateException e) {
                System.out.println("  put after close: rejected (" + e.getMessage() + ")");
            }

            StringBuilder drained = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                drained.append(buffer.take()).append(' ');
            }
            System.out.println("  Drained after close: " + drained.toString().trim());
            Integer end = buffer.take();
            System.out.println("  Next take returns: " + (end == null ? "end of stream (null)" : end));

            // a consumer already waiting on an empty buffer must wake up when the buffer is closed
            BoundedBuffer empty = new BoundedBuffer(2);
            AtomicBoolean gotEnd = new AtomicBoolean(false);
            Thread waiter = new Thread(() -> {
                try {
                    gotEnd.set(empty.take() == null);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            waiter.start();
            sleep(300);
            System.out.println("  Waiting consumer state before close: " + waiter.getState());
            empty.close();
            waiter.join(2000);
            System.out.println("  After close: consumer got end of stream: " + gotEnd.get()
                    + ", finished: " + !waiter.isAlive());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Demo interrupted.");
        }
    }

    private static void explain() {
        System.out.println();
        System.out.println("PRODUCER-CONSUMER");
        System.out.println("  Producers add items to a shared buffer, consumers remove them.");
        System.out.println("  The buffer has a limit, so producers must wait when it is full");
        System.out.println("  and consumers must wait when it is empty.");
        System.out.println();
        System.out.println("wait() AND notifyAll()");
        System.out.println("  wait() releases the lock and sleeps until another thread calls notify/notifyAll.");
        System.out.println("  Both must be called inside synchronized code.");
        System.out.println("  The wait is inside a while loop, not an if: after waking up the condition");
        System.out.println("  must be checked again (another thread may have taken the slot first).");
        System.out.println("  notifyAll is used because producers and consumers wait on the same lock,");
        System.out.println("  and notify() could wake the wrong kind of thread.");
        System.out.println();
        System.out.println("SHUTDOWN");
        System.out.println("  close() marks the buffer as finished and wakes every waiting thread.");
        System.out.println("  Consumers drain what is left, then take() returns null and they stop.");
        System.out.println();
        System.out.println("CHECKS IN THE SIMULATION");
        System.out.println("  produced = consumed, no duplicates, checksum matches, buffer empty at the end,");
        System.out.println("  buffer never above capacity, and each consumer sees one producer's items in order.");
    }

    private static boolean inRange(int value, int min, int max, String what) {
        if (value < min || value > max) {
            System.out.println(what + " must be " + min + " to " + max + ".");
            return false;
        }
        return true;
    }

    private static void joinAll(List<Thread> threads) {
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void pause() {
        System.out.print("\nPress Enter to continue...");
        sc.nextLine();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  That is not a whole number. Try again (example: 1).");
            }
        }
    }
}