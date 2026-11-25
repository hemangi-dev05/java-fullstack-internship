package ticketbooking;

public class Theatre {

    private final int rows;
    private final int seatsPerRow;
    // null means the seat is free, otherwise it holds the passenger name
    private final String[][] bookedBy;

    public Theatre(int rows, int seatsPerRow) {
        if (rows < 1 || rows > 26) {
            throw new IllegalArgumentException("Rows must be between 1 and 26.");
        }
        if (seatsPerRow < 1 || seatsPerRow > 9) {
            throw new IllegalArgumentException("Seats per row must be between 1 and 9.");
        }
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
        this.bookedBy = new String[rows][seatsPerRow];
    }

    public void book(String seatLabel, String passengerName) {
        int[] pos = locate(seatLabel);
        if (passengerName == null || passengerName.isBlank()) {
            throw new IllegalArgumentException("Passenger name cannot be empty.");
        }
        String current = bookedBy[pos[0]][pos[1]];
        if (current != null) {
            throw new IllegalStateException("Seat " + labelOf(pos[0], pos[1]) + " is already booked by " + current + ".");
        }
        bookedBy[pos[0]][pos[1]] = passengerName.trim();
    }

    // returns the name of the passenger whose booking was cancelled
    public String cancel(String seatLabel) {
        int[] pos = locate(seatLabel);
        String name = bookedBy[pos[0]][pos[1]];
        if (name == null) {
            throw new IllegalStateException("Seat " + labelOf(pos[0], pos[1]) + " is not booked, nothing to cancel.");
        }
        bookedBy[pos[0]][pos[1]] = null;
        return name;
    }

    // turns text like "b3" into array positions {1, 2}
    private int[] locate(String seatLabel) {
        String hint = "Rows A-" + (char) ('A' + rows - 1) + ", seats 1-" + seatsPerRow + " (example: B3).";
        if (seatLabel == null || seatLabel.trim().length() < 2) {
            throw new IllegalArgumentException("Seat must look like B3. " + hint);
        }
        String s = seatLabel.trim().toUpperCase();
        int row = s.charAt(0) - 'A';
        int col;
        try {
            col = Integer.parseInt(s.substring(1)) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Seat must look like B3. " + hint);
        }
        if (row < 0 || row >= rows || col < 0 || col >= seatsPerRow) {
            throw new IllegalArgumentException("Seat " + s + " does not exist. " + hint);
        }
        return new int[] { row, col };
    }

    public int countBooked() {
        int count = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < seatsPerRow; c++) {
                if (bookedBy[r][c] != null) {
                    count++;
                }
            }
        }
        return count;
    }

    public int countAvailable() {
        return rows * seatsPerRow - countBooked();
    }

    public int getRows() { return rows; }
    public int getSeatsPerRow() { return seatsPerRow; }
    public String getPassenger(int row, int col) { return bookedBy[row][col]; }
    public String labelOf(int row, int col) { return (char) ('A' + row) + "" + (col + 1); }
}