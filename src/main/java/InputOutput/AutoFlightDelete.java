package main.java.InputOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;


import main.java.FlightManagement.Flight;

public class AutoFlightDelete {
    public static ArrayList<Flight> deletion(){
        ArrayList<Flight> flights = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader("data/Flights.txt"));
            while((line = br.readLine()) != null){
                String[] parts = line.split(" ");
                LocalDate flightDate = LocalDate.parse(parts[4], dtf);
                if(!flightDate.isBefore(today)){
                    Flight flight = new Flight(parts[0],parts[1],parts[2],parts[3],parts[4],parts[5],parts[6]);
                    flights.add(flight);
                }
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter("data/Flights.txt"));
            bw.write("");
            bw.close(); 
        } catch (Exception e) {
            System.out.println(e);
        }
        return flights;
    }
}
