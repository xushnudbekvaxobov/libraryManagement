package project.dto.responseDto;

import lombok.Getter;
import lombok.Setter;
import project.enums.UserRole;

@Getter
@Setter
public class JwtResponseDto {
    private String username;

    private UserRole role;

    private String jwt;
}
