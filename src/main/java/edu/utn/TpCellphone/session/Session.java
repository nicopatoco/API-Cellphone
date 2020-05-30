package edu.utn.TpCellphone.session;

import edu.utn.TpCellphone.model.User;
import lombok.*;

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

