package utn.edu.nicolasherreraparcial.model;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    private int idUser;
    private String id;
    private String name;
    private String surname;
    private String username;
    private String password;
    public enum UserType {admin, client, antenna};
    private  UserType userType;
    private City city;
}
