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
@Table(name = "Bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bill")
    private int idBill;
    
    @Column(name = "amount_of_calls")
    private int amountOfCalls;
    @Column(name = "final_price")
    private double finalPrice;
    @Column(name = "bill_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date billDate;
    @Column(name = "due_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dueDate;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user")
    private User idUser;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cellphone")
    private Cellphone cellphone;
}
