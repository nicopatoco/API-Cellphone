package edu.utn.TpCellphone.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Bills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_bill;
    private int id_cellphone;
    private int id_user;
    private int amount_of_calls;
    private double final_price;
    private Date due_date;
    
}
