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
    private String cellphoneNumber;
    
    private enum LineType {mobile, home};
    @Enumerated(EnumType.STRING)
    @Column(name = "line_type")
    private LineType lineType;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user")
    private User idUser;
}