package project.dto.responseDto;


import lombok.Getter;
import lombok.Setter;
import project.enums.UserRole;
import project.enums.UserStatus;

@Getter
@Setter
public class UserResponseDto {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String username;
    private UserRole role;
    private UserStatus status;
}
