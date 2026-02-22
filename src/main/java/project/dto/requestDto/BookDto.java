package project.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    @NotBlank(message = "title cannot be blank")
    private String title;

    private Integer totalCopies;
}
