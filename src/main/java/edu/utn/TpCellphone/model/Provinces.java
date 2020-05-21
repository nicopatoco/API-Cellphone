package edu.utn.TpCellphone.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Provinces {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_province;
    private String name;
    
    @OneToMany()
    @JoinColumn(name = "id_province", referencedColumnName = "id_province")
    private List<Cities> cities;
}
