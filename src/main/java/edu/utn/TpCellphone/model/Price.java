package edu.utn.TpCellphone.model;

import lombok.*;

import javax.persistence.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_price")
    private int idPrice;
    
    @Column(name = "price_per_minute")
    private double pricePerMinute;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_origin_city")
    private City cityOrigin;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_destination_city")
    private City cityDestination;
}
