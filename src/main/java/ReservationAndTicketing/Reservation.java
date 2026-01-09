package main.java.ReservationAndTicketing;
import main.java.FlightManagement.Flight;
import main.java.FlightManagement.Seat;

public class Reservation {
    private String reservationCode;
    private Flight flight;
    private Passenger passenger;
    private Seat seat;
    private String dateOfReservation;
    public Reservation(String reservationCode, Flight flight, Passenger passenger, Seat seat, String dateOfReservation) {
        this.reservationCode = reservationCode;
        this.flight = flight;
        this.passenger = passenger;
        this.seat = seat;
        this.dateOfReservation = dateOfReservation;
    }
    
}
