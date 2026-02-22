package project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.responseDto.ApiResponse;
import project.dto.requestDto.BookDto;
import project.dto.responseDto.AuthorResponseDto;
import project.dto.responseDto.BookResponseDto;
import project.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<BookResponseDto>>> getAllBookByPage(@RequestParam(defaultValue = "0") Integer page,
                                                                               @RequestParam(defaultValue = "10") Integer size){
       Page<BookResponseDto> bookResponseDtoPage = bookService.getAllBookByPage(page,size);
       return ResponseEntity
               .status(HttpStatus.OK)
               .body(new ApiResponse<>(true,"getting all books",bookResponseDtoPage));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponseDto>> getBookById(@PathVariable Integer id){
        BookResponseDto bookResponseDto = bookService.getBookById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"get book by id",bookResponseDto));
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AuthorResponseDto>> getAuthorByBookId(@PathVariable Integer id){
       AuthorResponseDto authorResponseDto = bookService.getAuthorByBookId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"getting author by book id",authorResponseDto));
    }


    @PostMapping("/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BookResponseDto>> addBook(@PathVariable Integer authorId,
                                                                @RequestBody @Valid BookDto bookDto){
        BookResponseDto bookResponseDto = bookService.addBook(authorId, bookDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true,"book added",bookResponseDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BookResponseDto>> editBook(@PathVariable Integer id,
                                                                 @RequestBody @Valid BookDto bookDto){
        BookResponseDto bookResponseDto = bookService.editBook(id,bookDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"book edited",bookResponseDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BookResponseDto>> deleteById(@PathVariable Integer id){
        BookResponseDto bookResponseDto = bookService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"book deleted by id",bookResponseDto));
    }
}
