package edu.utn.TpCellphone.model;

import lombok.*;

import javax.persistence.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int idUser;

    private String id;
    private String name;
    private String surname;
    private String username;
    private String password;
    
    public enum UserType {admin, client};
    
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private  UserType userType;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_city")
    private City city;
}