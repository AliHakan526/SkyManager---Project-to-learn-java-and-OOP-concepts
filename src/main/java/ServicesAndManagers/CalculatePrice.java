package main.java.ServicesAndManagers;
import java.util.ArrayList;

public class CalculatePrice {
    public static double calculatePrice(ArrayList<ArrayList<Integer>> chosenSeats,int ecoRows,int ecoPrice,int busPrice){
        double totalPrice = 0;
        for(ArrayList<Integer> seats : chosenSeats){
            if(seats.get(0)<ecoRows){
                totalPrice += ecoPrice;
            }
            else{
                totalPrice += busPrice;
            }
        }
        return totalPrice; 
    }

    public static double calculatePrice(int row,int ecoRows,int ecoPrice,int busPrice){
        double price = 0;
        if(row<ecoRows){
            price += ecoPrice;
        }
        else{
            price += busPrice;
        }
        return price; 
    }
}
