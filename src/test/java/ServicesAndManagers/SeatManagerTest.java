package test.java.ServicesAndManagers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import main.java.FlightManagement.Seat;
import main.java.ServicesAndManagers.SeatManager;

public class SeatManagerTest {

    @Test
    public void testEmptySeatsCountInitial() {
        int rows = 10;
        int cols = 6;
        Seat[][] matrix = new Seat[rows][cols];

        // Initialize all as empty
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = new Seat("" + i + j, 100, false, 0);
            }
        }

        int count = SeatManager.emptySeatsCount(matrix, rows, cols);
        assertEquals(60, count, "Initial empty count should be 10*6 = 60");
    }

    @Test
    public void testEmptySeatsCountAfterReservation() {
        int rows = 5;
        int cols = 2;
        Seat[][] matrix = new Seat[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = new Seat("" + i + j, 100, false, 0);
            }
        }

        // Reserve 3 seats
        matrix[0][0].setReserveStatus(true);
        matrix[1][1].setReserveStatus(true);
        matrix[4][0].setReserveStatus(true);

        int count = SeatManager.emptySeatsCount(matrix, rows, cols);
        assertEquals(7, count, "Should be 10 - 3 = 7 empty seats");
    }
}
