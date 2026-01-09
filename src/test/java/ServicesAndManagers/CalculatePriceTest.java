package test.java.ServicesAndManagers;

import main.java.ServicesAndManagers.CalculatePrice;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class CalculatePriceTest {

    @Test
    public void testCalculatePriceSingleSeatEconomy() {
        int ecoRows = 25;
        int ecoPrice = 110;
        int busPrice = 250;

        // Row 10 is Economy (< 25)
        double price = CalculatePrice.calculatePrice(10, ecoRows, ecoPrice, busPrice);
        assertEquals(110, price, "Economy seat price should be 110");
    }

    @Test
    public void testCalculatePriceSingleSeatBusiness() {
        int ecoRows = 25;
        int ecoPrice = 110;
        int busPrice = 250;

        // Row 26 is Business (> 25)
        double price = CalculatePrice.calculatePrice(26, ecoRows, ecoPrice, busPrice);
        assertEquals(250, price, "Business seat price should be 250");
    }

    @Test
    public void testCalculatePriceList() {
        int ecoRows = 25;
        int ecoPrice = 110;
        int busPrice = 250;

        ArrayList<ArrayList<Integer>> seats = new ArrayList<>();

        // Add Economy Seat (Row 5)
        ArrayList<Integer> s1 = new ArrayList<>();
        s1.add(5);
        s1.add(0);
        seats.add(s1);

        // Add Business Seat (Row 28)
        ArrayList<Integer> s2 = new ArrayList<>();
        s2.add(28);
        s2.add(0);
        seats.add(s2);

        double total = CalculatePrice.calculatePrice(seats, ecoRows, ecoPrice, busPrice);
        assertEquals(360, total, "Total price should be 110 + 250 = 360");
    }
}
