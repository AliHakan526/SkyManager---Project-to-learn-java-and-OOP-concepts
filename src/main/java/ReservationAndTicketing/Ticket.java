package main.java.ReservationAndTicketing;

public class Ticket {
    private String ticketID;
    private Reservation reservation;
    private int price;
    private Boolean baggageAllowance;
    public Ticket(String ticketID, Reservation reservation, int price,Baggage baggage) {
        this.ticketID = ticketID;
        this.reservation = reservation;
        this.price = price;
        if(baggage.getWeight() < 15){
            baggageAllowance = true;
        }
        else{
            baggageAllowance = false;
        }
    }
    
}
