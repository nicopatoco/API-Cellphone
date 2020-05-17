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
public class Provinces {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_province;

    private String name;
}
