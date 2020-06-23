package edu.utn.TpCellphone.exceptions;

public class CellphoneNotFoundException extends Throwable {
    
    @Override
    public String getMessage() {
        return "Cellphone not found";
    }
}
