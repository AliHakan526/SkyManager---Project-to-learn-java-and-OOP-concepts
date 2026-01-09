package main.java.InputOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import main.java.FlightManagement.Flight;
import main.java.FlightManagement.Plane;

public class Reader {

    public static String Read(String fileName){
        fileName = "data/" + fileName;
        String all = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = bufferedReader.readLine()) != null){
                all = all + "\n" + line;
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return all;
    }

    public static ArrayList<Flight> getFlightsFromFile(String path) {
        ArrayList<Flight> flightList = new ArrayList<>();
        path = "data/" + path;
        
        try (BufferedReader br = new BufferedReader( new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] attr = line.split(" ");
                if (attr.length >= 7) {
                    // Dizideki verilerden yeni bir Flight nesnesi yaratıyoruz
                    Flight f = new Flight(attr[0], attr[1], attr[2], attr[3], attr[4], attr[5], attr[6]);
                    flightList.add(f);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flightList;
    }

    public static ArrayList<Plane> getPlanesFromFile(String path) {
        path = "data/" + path;
        ArrayList<Plane> planeList = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] attr = line.split(" ");
                // Constructor 8 parametre bekliyor
                if (attr.length >= 8) {
                    try {
                        // Verileri uygun tiplere dönüştürüyoruz
                        Plane p = new Plane(
                            attr[0],                         // planeID
                            attr[1],                         // planeModel
                            Integer.parseInt(attr[2]),       // capacity
                            Integer.parseInt(attr[3]),       // rows
                            Integer.parseInt(attr[4]),       // cols
                            Integer.parseInt(attr[5]),       // ecoRows
                            Integer.parseInt(attr[6]),       // ecoPrice
                            Integer.parseInt(attr[7])        // busPrice
                        );
                        planeList.add(p);
                    } catch (Exception e) {
                        // Eğer InsufficientSeatException fırlarsa buraya düşer
                        System.err.println("Uçak oluşturulamadı (Koltuk yetersiz): " + attr[0]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return planeList;
    }

}
