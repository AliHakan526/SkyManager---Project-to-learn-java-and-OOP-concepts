package test.java.ServicesAndManagers;

import main.java.ServicesAndManagers.FlightSearchEngine;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import main.java.FlightManagement.Flight;

public class FlightSearchEngineTest {

    @Test
    public void testSearchByRoute() {
        ArrayList<Flight> list = new ArrayList<>();
        list.add(new Flight("F1", "P1", "Izmir", "Istanbul", "01-01-2027", "12:00", "1"));
        list.add(new Flight("F2", "P2", "Ankara", "Istanbul", "01-01-2027", "13:00", "1"));
        list.add(new Flight("F3", "P3", "Izmir", "Antalya", "01-01-2027", "14:00", "1"));

        ArrayList<Flight> results = FlightSearchEngine.searchByRoute(list, "Izmir", "Istanbul");

        assertEquals(1, results.size());
        assertEquals("F1", results.get(0).getFlightNum());
    }

    @Test
    public void testFilterPassedFlights() {
        ArrayList<Flight> list = new ArrayList<>();
        // Past flight (2020)
        list.add(new Flight("OLD", "P1", "A", "B", "01-01-2020", "12:00", "1"));
        // Future flight (2030)
        list.add(new Flight("NEW", "P2", "A", "B", "01-01-2030", "12:00", "1"));

        ArrayList<Flight> results = FlightSearchEngine.filterPassedFlights(list);

        assertEquals(1, results.size());
        assertEquals("NEW", results.get(0).getFlightNum());
    }
}
