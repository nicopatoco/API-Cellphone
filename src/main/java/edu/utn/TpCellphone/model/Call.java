package edu.utn.TpCellphone.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "Calls")
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_call")
    private int idCall;
    
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "final_value")
    private Double finalValue;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cellphone_origin")
    private Cellphone idCellphoneOrigin;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cellphone_destination")
    private Cellphone idCellphoneDestination;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_price")
    private Cellphone idPrice;
}
