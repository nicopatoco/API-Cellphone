package edu.utn.TpCellphone.projections;

import java.util.Date;

public interface GetBill {

    Integer getId();
    String getCellphone();
    Double getTotal();
    Date getExpiration();

}
