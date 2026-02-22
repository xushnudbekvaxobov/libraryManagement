package project.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDto {
    @NotBlank(message = "username is not created")
    private String username;
    @NotBlank(message = "password is not created")
    private String password;
}
