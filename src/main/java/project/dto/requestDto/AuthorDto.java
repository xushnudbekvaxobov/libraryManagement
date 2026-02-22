package project.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class AuthorDto {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 65,message = "Name is invalid")
    private String name;

    @NotBlank(message = "surname cannot be blank")
    @Size(min = 2, max = 65,message = "surname is invalid")
    private String surname;

    private LocalDate birthDate;

    @NotBlank(message = "country cannot be blank")
    @Size(min = 2, max = 65,message = "country is invalid")
    private String country;
}
