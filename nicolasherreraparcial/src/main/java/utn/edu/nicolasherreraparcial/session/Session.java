package utn.edu.nicolasherreraparcial.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import utn.edu.nicolasherreraparcial.model.User;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Session {
    String token;
    User loggedUser;
    Date lastAction;
}

