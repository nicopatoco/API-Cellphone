package edu.utn.TpCellphone.model;

import lombok.*;

import javax.persistence.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "Cellphones")
public class Cellphone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cellphone")
    private int idCellphone;
    
    @Column(name = "cellphone_number")
    private int cellphoneNumber;
    
    private enum LineType {admin, client};
    @Enumerated(EnumType.STRING)
    private LineType line_type;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user")
    private User idUser;
}