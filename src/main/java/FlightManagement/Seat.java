package main.java.FlightManagement;

public class Seat {
    private String seatNum;
    enum Class{
        ECONOMY,
        BUSINESS
    }
    private Class class_;
    private int price;
    private Boolean reserveStatus;
    public Seat(String seatNum, int price, Boolean reserveStatus,int flagForEnum) {
        this.seatNum = seatNum;
        this.price = price;
        this.reserveStatus = reserveStatus;
        if(flagForEnum==0){
            class_ = Class.ECONOMY;
        }
        else{
            class_ = Class.BUSINESS;
        }
    }
    
    public Boolean getReserveStatus(){
        return reserveStatus;
    }

    public void setReserveStatus(Boolean status){
        reserveStatus = status;
    }

}
