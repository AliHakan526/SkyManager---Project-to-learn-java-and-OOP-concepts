package main.java.FlightManagement;

public class Flight {
    private String flightNum;
    private String departurePlace;
    private String arrivalPlace;
    private String date;
    private String hour;
    private String duration;
    private String planeID;
    public Flight(String flightNum,String planeID, String departurePlace, String arrivalPlace, String date, String hour, String duration) {
        this.planeID = planeID;
        this.flightNum = flightNum;
        this.departurePlace = departurePlace;
        this.arrivalPlace = arrivalPlace;
        this.date = date;
        this.hour = hour;
        this.duration = duration;
    }
    public String getFlightNum() {
        return flightNum;
    }
    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }
    public String getDeparturePlace() {
        return departurePlace;
    }
    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }
    public String getArrivalPlace() {
        return arrivalPlace;
    }
    public void setArrivalPlace(String arrivalPlace) {
        this.arrivalPlace = arrivalPlace;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getHour() {
        return hour;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getPlaneID() {
        return planeID;
    }
    public void setPlaneID(String planeID) {
        this.planeID = planeID;
    }
    
}
