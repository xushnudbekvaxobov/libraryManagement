package project.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDto {
    @NotBlank(message = "password cannot be blank")
    @Size(min = 5,max = 5,message = "password must be 5 characters")
    private String oldPassword;
    @NotBlank(message = "password cannot be blank")
    @Size(min = 5,max = 5,message = "password must be 5 characters")
    private String newPassword;
}
