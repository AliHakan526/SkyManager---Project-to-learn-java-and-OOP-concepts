package main.java.ReservationAndTicketing;

import main.java.ServicesAndManagers.ReservationManager;
import main.java.ServicesAndManagers.SeatManager;
import main.java.Uncategorized.Person;
import main.java.FlightManagement.*;
import java.awt.Color;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class Passenger extends Person implements Runnable{
    private String passengerID;
    private Plane planeToBoard;
    private JButton buttons[][];
    private Boolean isThreaded;
    private static ArrayList<ArrayList<Integer>> chosenSeats = new ArrayList<>();
    public Passenger(String passengerID, String name,String surname, String contactInfo) {
        super(name,surname,contactInfo);
        this.passengerID = passengerID;
    }

    
    @Override
public void run() {
    System.out.println(passengerID + " çalışıyor...");
    boolean success = false;
    
    // Sadece sayı alıyoruz, rezerve etmiyoruz!
    ArrayList<Integer> nums = SeatManager.takeRandomSeat(planeToBoard.getSeatMatrix(), planeToBoard.getRows(), planeToBoard.getCols());
    int row = nums.get(0);
    int col = nums.get(1);

    if (this.isThreaded) {
        synchronized (this.planeToBoard) {
            if (!planeToBoard.getSeatMatrix()[row][col].getReserveStatus()) {
                planeToBoard.getSeatMatrix()[row][col].setReserveStatus(true);
                success = true;
            }
        }
    } else {
        if (!planeToBoard.getSeatMatrix()[row][col].getReserveStatus()) {
            planeToBoard.getSeatMatrix()[row][col].setReserveStatus(true);
            success = true;
        }
    }

    if (success) {
        chosenSeats.add(nums);
        System.out.println("Koltuk kapıldı: " + row + "," + col);
        SwingUtilities.invokeLater(() -> {
            System.out.println("BOYANIYOR...");
            buttons[row][col].setOpaque(true);
            buttons[row][col].setContentAreaFilled(false); // Bu satır hayat kurtarır
            buttons[row][col].setBackground(Color.RED);
            buttons[row][col].setEnabled(false);
        });
    }
}

    public static ArrayList<ArrayList<Integer>> getChosenSeats(){
        return chosenSeats;
    }    

    public static void clearChosenSeats(){
        chosenSeats.clear();
    }


    public Plane getPlaneToBoard(){
        return planeToBoard;
    }

    public void setPlaneToBoard(Plane plane){
        this.planeToBoard = plane;
    }

    public JButton[][] getButtons(){
        return buttons;
    }

    public void setButtons(JButton[][] buttons){
        this.buttons = buttons;
    }


    public String getPassengerID() {
        return passengerID;
    }


    public void setPassengerID(String passengerID) {
        this.passengerID = passengerID;
    }


    public Boolean getIsThreaded() {
        return isThreaded;
    }


    public void setIsThreaded(Boolean isThreaded) {
        this.isThreaded = isThreaded;
    }
    
}
