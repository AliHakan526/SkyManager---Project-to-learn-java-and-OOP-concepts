package main.java.ServicesAndManagers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import main.java.FlightManagement.Flight;
import main.java.FlightManagement.Plane;
import main.java.InputOutput.Appender;

public class FlightManager {

    public void createFlight(String flightNum, String planeID, String departurePlace, String arrivalPlace, String date,
            String hour, String duration, ArrayList<Flight> flights) {
        Flight flight = new Flight(flightNum, planeID, departurePlace, arrivalPlace, date, hour, duration);
        flights.add(flight);
        String flightEntry = "%s %s %s %s %s %s\n";
        flightEntry = String.format(flightEntry, flightNum, departurePlace, arrivalPlace, date, hour, duration);
        Appender.Append("Flights.txt", flightEntry);
    }

    public ArrayList<Flight> deleteFlight(ArrayList<Flight> flights, String flightNum) {
        flights.removeIf(f -> f.getFlightNum().equals(flightNum));
        StringBuilder flightEntry = new StringBuilder();
        for (Flight flight : flights) {
            flightEntry.append(
                    String.format("%s %s %s %s %s %s\n",
                            flight.getFlightNum(),
                            flight.getDeparturePlace(),
                            flight.getArrivalPlace(),
                            flight.getDate(),
                            flight.getHour(),
                            flight.getDuration()));
        }
        main.java.InputOutput.Writer.Write("Flights.txt", flightEntry.toString());
        return flights;
    }

    public void updateDate(String date, String flightNum, ArrayList<Flight> flights) {
        String flightEntry = "";
        for (Flight flight : flights) {
            if (flight.getFlightNum().equals(flightNum)) {
                flight.setDate(date);
            }
            flightEntry += String.format("%s %s %s %s %s %s\n", flight.getFlightNum(), flight.getDeparturePlace(),
                    flight.getArrivalPlace(), flight.getDate(), flight.getHour(), flight.getDuration());
        }
        main.java.InputOutput.Writer.Write("Flights.txt", flightEntry);
    }

    public void updateHour(String hour, String flightNum, ArrayList<Flight> flights) {
        String flightEntry = "";
        for (Flight flight : flights) {
            if (flight.getFlightNum().equals(flightNum)) {
                flight.setHour(hour);
            }
            flightEntry += String.format("%s %s %s %s %s %s\n", flight.getFlightNum(), flight.getDeparturePlace(),
                    flight.getArrivalPlace(), flight.getDate(), flight.getHour(), flight.getDuration());
        }
        main.java.InputOutput.Writer.Write("Flights.txt", flightEntry);

    }

    public ArrayList<Plane> deletePlane(String planeID, ArrayList<Plane> planes) {
        planes.removeIf(p -> p.getPlaneID().equals(planeID));
        StringBuilder planeEntry = new StringBuilder();
        for (Plane plane : planes) {
            planeEntry.append(
                    String.format("%s %s %d %d %d %d %d %d\n",
                            plane.getPlaneID(),
                            plane.getPlaneModel(),
                            plane.getCapacity(),
                            plane.getRows(),
                            plane.getCols(),
                            plane.getEcoRows(),
                            plane.getEcoPrice(),
                            plane.getBusPrice()));
        }
        main.java.InputOutput.Writer.Write("Planes.txt", planeEntry.toString());
        return planes;
    }

    public void createPlane(String planeID, String planeModel, int capacity, int rows, int cols, int ecoRows,
            int ecoPrice, int busPrice, ArrayList<Plane> planes) {
        Plane plane = null;
        try {
            plane = new Plane(planeID, planeModel, capacity, rows, cols, ecoRows, ecoPrice, busPrice);
        } catch (Exception e) {
            System.out.println(e);
        }
        if (plane != null) {
            planes.add(plane);
            String planeEntry = "%s %s %d %d %d %d %d %d\n";
            planeEntry = String.format(planeEntry, planeID, planeModel, capacity, rows, cols, ecoRows, ecoPrice,
                    busPrice);
            Appender.Append("Planes.txt", planeEntry);
        }
    }

}
