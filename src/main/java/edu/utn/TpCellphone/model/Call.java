package edu.utn.TpCellphone.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "final_value")
    private Double finalValue;
    @Column(name = "number_origin")
    private String numberOrigin;
    @Column(name = "number_destination")
    private String numberDestination;
    private int duration;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cellphone_origin")
    private Cellphone cellphoneOrigin;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cellphone_destination")
    private Cellphone  cellphoneDestination;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_price")
    private Price price;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_bill")
    private Bill bill;
}
