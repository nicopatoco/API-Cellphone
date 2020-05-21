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
public class Calls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_call;
    
    private int id_cellphone_origin;
    private int id_cellphone_destination;
    private int id_price;
    private Date start_time;
    private Date end_time;
    private Double final_value;
}
