package edu.utn.TpCellphone.exceptions;

public class BillNotFoundException  extends Throwable {

    public String getMessage() {
        return "Bill not found";
    }

}
