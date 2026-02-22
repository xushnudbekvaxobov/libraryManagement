package project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.responseDto.ApiResponse;
import project.dto.requestDto.AuthorDto;
import project.dto.responseDto.AuthorResponseDto;
import project.dto.responseDto.BookResponseDto;
import project.service.AuthorService;


@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<AuthorResponseDto>>> getAllAuthorByPage(@RequestParam(defaultValue = "0") Integer page,
                                                                                   @RequestParam(defaultValue = "10") Integer size){
        Page<AuthorResponseDto> authorResponseDtoPage = authorService.allAuthor(page,size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"getting all authors by page",authorResponseDtoPage)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorResponseDto>> getAuthorById(@PathVariable Integer id){
        AuthorResponseDto authorResponseDto = authorService.getById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"getting author by id",authorResponseDto)
        );
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<Page<BookResponseDto>>> getBooksByAuthorId(@PathVariable Integer id,
                                                                                 @RequestParam(defaultValue = "0") Integer page,
                                                                                 @RequestParam(defaultValue = "10") Integer size){
       Page<BookResponseDto> bookResponseDtoPage = authorService.getBooksByAuthorId(id,page,size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"getting books by author id",bookResponseDtoPage)
        );
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AuthorResponseDto>> addAuthor(@RequestBody @Valid AuthorDto authorDto){
        AuthorResponseDto authorResponseDto = authorService.addAuthor(authorDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true,"author added",authorResponseDto)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AuthorResponseDto>> editAuthor(@PathVariable Integer id,
                                                                     @RequestBody @Valid AuthorDto authorDto){
        AuthorResponseDto authorResponseDto = authorService.editAuthor(id,authorDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"author edited",authorResponseDto)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AuthorResponseDto>> deleteAuthor(@PathVariable Integer id){
        AuthorResponseDto authorResponseDto = authorService.deleteAuthor(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"author deleted",authorResponseDto)
        );
    }
}
