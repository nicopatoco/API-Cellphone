package edu.utn.TpCellphone.exceptions;

public class CallNotFoundException extends Throwable {
    
    public String getMessage() {
        return "Call not found";
    }
}
