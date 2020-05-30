package edu.utn.TpCellphone.model;

import lombok.*;

import javax.persistence.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_city")
    private int idCity;
    private String name;
    private int prefix;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_province")
    private Province province;
}
