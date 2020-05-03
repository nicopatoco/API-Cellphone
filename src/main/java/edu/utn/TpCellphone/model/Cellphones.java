package edu.utn.TpCellphone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private int id_client;
}
