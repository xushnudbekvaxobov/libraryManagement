package project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.requestDto.AuthorDto;
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
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorMapper mapper;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;

    public Page<AuthorResponseDto> allAuthor(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<AuthorEntity> authorPage = authorRepository.findAll(pageRequest);
        return authorPage.map(mapper::toAuthorResponseDto);
    }

    public AuthorResponseDto getById(Integer id){
        AuthorEntity authorEntity = authorRepository.findById(id).orElseThrow();
        return mapper.toAuthorResponseDto(authorEntity);
    }

    public Page<BookResponseDto> getBooksByAuthorId(Integer id,
                                                Integer page,
                                                Integer size){
        PageRequest pageRequest = PageRequest.of(page,size,Sort.by("id"));
        Page<BookEntity> bookEntity = bookRepository.findAllByAuthorEntityId(id,pageRequest);
        return bookEntity.map(bookMapper::toBookResponseDto);
    }

    public AuthorResponseDto addAuthor(AuthorDto authorDto){
        if(authorRepository.existsByNameAndSurname(authorDto.getName(),authorDto.getSurname())){
            throw new AppBadException("Author already exists");
        }
        AuthorEntity authorEntity = mapper.toAuthorEntity(authorDto);
        AuthorEntity savedAuthor = authorRepository.save(authorEntity);
        return mapper.toAuthorResponseDto(savedAuthor);
    }

    @Transactional
    public AuthorResponseDto editAuthor(Integer id,AuthorDto authorDto){
        Optional<AuthorEntity> authorEntity = authorRepository.findById(id);
        if(authorEntity.isEmpty()){
            throw new AppBadException("Author not found");
        }
        AuthorEntity authorToUpdate = authorEntity.get();
        authorToUpdate.setName(authorDto.getName());
        authorToUpdate.setSurname(authorDto.getSurname());
        authorToUpdate.setBirthDate(authorDto.getBirthDate());
        authorToUpdate.setCountry(authorDto.getCountry());
//        AuthorEntity updatedAuthor = authorRepository.save(authorToUpdate);
        return mapper.toAuthorResponseDto(authorToUpdate);
    }

    public AuthorResponseDto deleteAuthor(Integer id){
        Optional<AuthorEntity> authorEntity = authorRepository.findById(id);
        if(authorEntity.isEmpty()){
            throw new AppBadException("Author not found");
        }
        AuthorEntity deleteAuthor = authorEntity.get();
        authorRepository.deleteById(id);
        return mapper.toAuthorResponseDto(deleteAuthor);
    }

}
