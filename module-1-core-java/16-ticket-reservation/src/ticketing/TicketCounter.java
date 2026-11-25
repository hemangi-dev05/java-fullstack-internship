package ticketing;

// Used only for the race condition demo: a plain counter of tickets left.
public class TicketCounter {

    private int remaining;

    public TicketCounter(int tickets) {
        this.remaining = tickets;
    }

    // UNSAFE: "check" and "update" are two separate steps, so other threads can slip in between
    public boolean buyUnsafe() {
        if (remaining > 0) {
            pauseBriefly();
            remaining--;
            return true;
        }
        return false;
    }

    // SAFE: synchronized turns check + update into one step
    public synchronized boolean buySafe() {
        if (remaining > 0) {
            pauseBriefly();
            remaining--;
            return true;
        }
        return false;
    }

    // read this only after all threads have finished (after join)
    public int getRemaining() {
        return remaining;
    }

    // stands in for work like payment processing, and makes the race show up reliably
    private static void pauseBriefly() {
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}