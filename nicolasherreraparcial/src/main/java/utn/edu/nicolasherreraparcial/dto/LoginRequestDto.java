package utn.edu.nicolasherreraparcial.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class LoginRequestDto {
    String username;
    String password;
}
