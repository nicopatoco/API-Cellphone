package edu.utn.TpCellphone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cellphones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cellphone;

    private int cellphone_number;
    private String user_type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_client", referencedColumnName = "id_client")
    private Clients client;

}
