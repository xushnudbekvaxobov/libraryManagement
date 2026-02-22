package project.dto.responseDto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import project.entity.AuthorEntity;

import java.time.LocalDate;

@Getter
@Setter
public class BookResponseDto {
    private Integer id;

    private String title;

    private Integer availableCopies;

    private Integer authorId;

}
