package project.dto.responseDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AuthorResponseDto {
    private Integer id;

    private String name;

    private String surname;

    private LocalDate birthDate;

    private String country;
}
