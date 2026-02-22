package project.dto.responseDto;


import lombok.Getter;
import lombok.Setter;
import project.enums.BorrowStatus;

import java.time.LocalDate;

@Getter
@Setter
public class BorrowResponseDto {

    private Integer id;

    private LocalDate borrowDate;

    private LocalDate returnDate;

    private BorrowStatus status;

    private Integer bookId;

    private Integer userId;
}
