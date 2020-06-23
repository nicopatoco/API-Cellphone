package edu.utn.TpCellphone.exceptions;

public class CellphoneUnavailableException extends Throwable {
    
    public String getMessage() {
        return "The cellphone have a downLine";
    }
}
