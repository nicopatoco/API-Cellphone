package edu.utn.TpCellphone.exceptions;

public class CityNotFoundException extends Throwable {
    
    public String getMessage() {
        return "Cities not found";
    }
}
