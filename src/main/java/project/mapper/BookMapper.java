package project.mapper;

import org.springframework.stereotype.Component;
import project.dto.requestDto.BookDto;
import project.dto.responseDto.BookResponseDto;
import project.entity.AuthorEntity;
import project.entity.BookEntity;

@Component
public class BookMapper {
    public BookResponseDto toBookResponseDto(BookEntity bookEntity) {
        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setId(bookEntity.getId());
        bookResponseDto.setTitle(bookEntity.getTitle());
        bookResponseDto.setAvailableCopies(bookEntity.getAvailableCopies());
        bookResponseDto.setAuthorId(bookEntity.getAuthorEntity().getId());
        return bookResponseDto;
    }

    public BookEntity toBookEntity(AuthorEntity authorEntity,BookDto bookDto){
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setTotalCopies(bookDto.getTotalCopies());
        bookEntity.setAvailableCopies(bookDto.getTotalCopies());
        bookEntity.setAuthorEntity(authorEntity);
        return bookEntity;
    }

}
