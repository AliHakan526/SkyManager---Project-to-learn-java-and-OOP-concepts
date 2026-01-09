package main.java.FlightManagement;

import main.java.CustomExceptions.InsufficientSeatException;

public class LuxPlane extends Plane{
    
    public LuxPlane(String planeID,String planeModel,int capacity,int rows,int cols,int ecoRows,int ecoPrice,int busPrice) throws Exception{
       super(planeID, planeModel, capacity, rows, cols, ecoRows, ecoPrice, busPrice);
       if(rows<40 || cols < 8){
            throw new InsufficientSeatException();
       }
    }
}
