package edu.utn.TpCellphone.exceptions;

public class PriceNotFoundException extends Throwable {
    
    public String getMessage() {
        return "Price not found";
    }
}
