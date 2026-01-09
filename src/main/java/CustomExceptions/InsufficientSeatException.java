package main.java.CustomExceptions;

public class InsufficientSeatException extends Exception{
    public InsufficientSeatException(){
        super("You entered a number of seats that is far too few for an airplane.");
    }
}
