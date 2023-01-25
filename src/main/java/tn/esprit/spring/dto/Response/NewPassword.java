package tn.esprit.spring.dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPassword {

    private String mail;
    private String code;
    private String password;
}
