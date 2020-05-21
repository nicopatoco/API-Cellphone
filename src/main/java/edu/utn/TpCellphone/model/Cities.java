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
public class Cities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_city;
    private String name;
    private int prefix;
    private int id_province;
    
    @OneToMany()
    @JoinColumn(name = "id_city", referencedColumnName = "id_city")
    private List<Users> usersList;
}
