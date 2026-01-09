package main.java.ServicesAndManagers;

import main.java.FlightManagement.Seat;
import java.util.ArrayList;
import java.util.Random;

public class SeatManager {
    public static ArrayList<Integer> takeRandomSeat(Seat[][] seatMatrix, int rows, int cols) {
        Random rand = new Random();
        int i;
        int j;

        // KRİTİK DÜZELTME:
        // Boş koltuk (false) bulana kadar dön demeliyiz.
        // Yani "Eğer seçilen koltuk doluysa (true), tekrar seç"
        do {
            i = rand.nextInt(rows);
            j = rand.nextInt(cols);
        } while (seatMatrix[i][j].getReserveStatus() == true);

        // Not: Burada setReserveStatus(true) YAPMA!
        // Çünkü Passenger içindeki synchronized blokta yapacağız bunu.

        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(i);
        nums.add(j);
        return nums;
    }

    public static int emptySeatsCount(Seat[][] seatMatrix, int rows, int cols) {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!seatMatrix[i][j].getReserveStatus()) {
                    count++;
                }
            }
        }
        return count;
    }
}