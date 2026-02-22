package project.dto.requestDto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDto {
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
    @NotBlank(message = "username cannot be blank")
    @Size(min = 6, max = 8,message = "username must be between 6 and 8 characters")
    private String username;
    @NotBlank(message = "password cannot be blank")
    @Size(min = 5,max = 5,message = "password must be 5 characters")
    private String password;
}
