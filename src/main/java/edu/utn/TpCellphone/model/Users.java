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
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_user;

    private String id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private int id_city;
    
    private enum UserType {admin, client};
    
    @Enumerated(EnumType.STRING)
    private  UserType user_type;
    
    @OneToMany
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private List<Cellphones> cellphonesList;
    
    @OneToMany
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private List<Bills> billsList;
    
}