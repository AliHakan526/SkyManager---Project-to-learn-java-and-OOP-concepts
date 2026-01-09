package main.java.ServicesAndManagers;
import main.java.ReservationAndTicketing.Passenger;

import java.util.ArrayList;

import javax.swing.JButton;

import main.java.FlightManagement.*;

public class ReservationManager {
    
    public static void startSimulation(ArrayList<Passenger> passengers,Plane plane,Boolean isThreaded,JButton[][] buttons){

        for(Passenger passenger : passengers){
            passenger.setButtons(buttons);
            passenger.setPlaneToBoard(plane);
            passenger.setIsThreaded(isThreaded);
            Thread t = new Thread(passenger);
            t.start();
            try {
                t.sleep(100);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    
    

}
