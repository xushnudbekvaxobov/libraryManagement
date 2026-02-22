package project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.requestDto.BookDto;
import project.dto.responseDto.AuthorResponseDto;
import project.dto.responseDto.BookResponseDto;
import project.entity.AuthorEntity;
import project.entity.BookEntity;
import project.exception.AppBadException;
import project.mapper.AuthorMapper;
import project.mapper.BookMapper;
import project.repository.AuthorRepository;
import project.repository.BookRepository;

import java.util.Optional;

@Slf4j
@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper mapper;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorMapper authorMapper;

    public Page<BookResponseDto> getAllBookByPage(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("id").ascending());
        Page<BookEntity> bookEntities = bookRepository.findAll(pageRequest);
        return bookEntities.map(mapper::toBookResponseDto);
    }

    public BookResponseDto getBookById(Integer id){
       Optional<BookEntity> bookEntity = bookRepository.findById(id);
         if(bookEntity.isEmpty()){
              throw new AppBadException("Book not found");
         }
        return mapper.toBookResponseDto(bookEntity.get());
    }

    public AuthorResponseDto getAuthorByBookId(Integer bookId){
        Optional<BookEntity> bookEntity = bookRepository.findById(bookId);
        if(bookEntity.isEmpty()){
            throw new AppBadException("Book not found");
        }
       AuthorEntity authorEntity = bookEntity.get().getAuthorEntity();
        return authorMapper.toAuthorResponseDto(authorEntity);
    }

    public BookResponseDto addBook(Integer authorId,BookDto dto){
       Optional<AuthorEntity> authorEntity = authorRepository.findById(authorId);
       if(authorEntity.isEmpty()){
           throw new AppBadException("Author not found");
       }
       BookEntity bookEntity = mapper.toBookEntity(authorEntity.get(),dto);
       log.info("Book added successfully: {}", bookEntity.getTitle());
        return mapper.toBookResponseDto(bookRepository.save(bookEntity));
    }

    @Transactional
    public BookResponseDto editBook(Integer id,BookDto bookDto){
        Optional<BookEntity> optional = bookRepository.findById(id);
        if(optional.isEmpty()){
            throw new AppBadException("Book not found");
        }
        BookEntity entity = optional.get();
        entity.setTitle(bookDto.getTitle());
        entity.setTotalCopies(bookDto.getTotalCopies());
        entity.setAvailableCopies(bookDto.getTotalCopies());
//        BookEntity updatedBook = bookRepository.save(entity);
        return mapper.toBookResponseDto(entity);
    }

    public BookResponseDto deleteById(Integer id){
        Optional<BookEntity> bookEntity = bookRepository.findById(id);
        if(bookEntity.isEmpty()){
            throw new AppBadException("Book not found");
        }
        BookResponseDto bookResponseDto = mapper.toBookResponseDto(bookEntity.get());
        bookRepository.deleteById(id);
        log.info("Book deleted successfully: {}", bookResponseDto.getTitle());
        return bookResponseDto;
    }
}
