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
public class Cellphones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cellphone;
    private int cellphone_number;
    
    private enum LineType {admin, client};
    @Enumerated(EnumType.STRING)
    private LineType line_type;
    
    private int id_user;
    
    @OneToMany
    @JoinColumn(name = "id_cellphone", referencedColumnName = "id_cellphone")
    private List<Bills> billsList;
}
