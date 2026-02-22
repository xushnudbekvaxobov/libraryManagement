package project.mapper;

import org.springframework.stereotype.Component;
import project.dto.responseDto.BorrowResponseDto;
import project.entity.BorrowEntity;
import project.enums.BorrowStatus;

@Component
public class BorrowMapper {
    public BorrowResponseDto toBorrowResponseDto(BorrowEntity entity){
        BorrowResponseDto responseDto = new BorrowResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setBorrowDate(entity.getBorrowDate());
        responseDto.setReturnDate(entity.getReturnDate());
        responseDto.setStatus(entity.getStatus());
        responseDto.setUserId(entity.getUserEntity().getId());
        responseDto.setBookId(entity.getBookEntity().getId());
        return responseDto;
    }
}
