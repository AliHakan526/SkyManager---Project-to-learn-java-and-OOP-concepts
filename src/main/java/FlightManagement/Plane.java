package main.java.FlightManagement;

import main.java.CustomExceptions.InsufficientSeatException;

public class Plane{
    private String planeID;
    private String planeModel;
    private int capacity;
    private Seat seatMatrix[][];
    private int rows;
    private int cols;
    public int getEcoPrice() {
        return ecoPrice;
    }

    public void setEcoPrice(int ecoPrice) {
        this.ecoPrice = ecoPrice;
    }

    public int getBusPrice() {
        return busPrice;
    }

    public void setBusPrice(int busPrice) {
        this.busPrice = busPrice;
    }

    private int ecoRows;
    private int ecoPrice;
    private int busPrice;
    public Plane(String planeID,String planeModel,int capacity,int rows,int cols,int ecoRows,int ecoPrice,int busPrice) throws Exception{
        this.ecoPrice = ecoPrice;
        this.busPrice = busPrice;
        this.ecoRows = ecoRows;
        this.rows = rows;
        this.cols = cols;
        this.planeID = planeID;
        this.capacity = capacity;
        this.planeModel = planeModel;
        if(rows<30 || cols < 6){
            throw new InsufficientSeatException();
        }
        seatMatrix = new Seat[rows][cols];
        for(int i=0;i<ecoRows;i++){
            char character = (char)('A'+i);
            for(int j = 0; j<cols ; j++){
                String seatNum = String.valueOf(j);
                seatNum = character + seatNum; 
                seatMatrix[i][j] = new Seat(seatNum,ecoPrice,false,0);
            }
        }
        for(int i=ecoRows;i<rows;i++){
            char character = (char)('A'+i);
            for(int j=0;j<cols;j++){
                String seatNum = String.valueOf(j);
                seatNum = character + seatNum; 
                seatMatrix[i][j] = new Seat(seatNum,busPrice,false,1);
            }
        }
    }

    public int getEcoRows() {
        return ecoRows;
    }

    public void setEcoRows(int ecoRows) {
        this.ecoRows = ecoRows;
    }

    public String getPlaneID() {
        return planeID;
    }

    public void setPlaneID(String planeID) {
        this.planeID = planeID;
    }

    public String getPlaneModel() {
        return planeModel;
    }

    public void setPlaneModel(String planeModel) {
        this.planeModel = planeModel;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public Seat[][] getSeatMatrix(){
        return seatMatrix;
    }


}
