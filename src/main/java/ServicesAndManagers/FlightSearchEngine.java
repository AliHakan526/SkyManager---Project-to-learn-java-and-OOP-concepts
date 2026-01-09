package main.java.ServicesAndManagers;

import main.java.FlightManagement.Flight;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlightSearchEngine {

    public static ArrayList<Flight> searchByRoute(ArrayList<Flight> flights, String departure, String arrival) {
        ArrayList<Flight> result = new ArrayList<>();
        for (Flight f : flights) {
            if (f.getDeparturePlace().equalsIgnoreCase(departure) && f.getArrivalPlace().equalsIgnoreCase(arrival)) {
                result.add(f);
            }
        }
        return result;
    }

    public static ArrayList<Flight> filterPassedFlights(ArrayList<Flight> flights) {
        ArrayList<Flight> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date now = new Date();

        for (Flight f : flights) {
            try {
                String dateTimeStr = f.getDate() + " " + f.getHour();
                Date flightDate = sdf.parse(dateTimeStr);

                if (flightDate.after(now)) {
                    result.add(f);
                }
            } catch (Exception e) {
                System.out.println("Tarih format hatası: " + f.getFlightNum());
            }
        }
        return result;
    }
}
