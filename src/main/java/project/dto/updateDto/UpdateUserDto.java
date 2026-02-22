package project.dto.updateDto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import project.enums.UserRole;
import project.enums.UserStatus;

@Getter
@Setter
public class UpdateUserDto {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 65,message = "Name is invalid")
    private String name;
    @NotBlank(message = "surname cannot be blank")
    @Size(min = 2, max = 65,message = "surname is invalid")
    private String surname;
    @NotBlank(message = "email cannot be blank")
    @Email
    private String email;
    @NotBlank(message = "phone cannot be blank")
    @Size(min = 9, max = 9,message = "phone is invalid")
    private String phone;
}
