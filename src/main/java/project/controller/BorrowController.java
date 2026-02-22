package project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.responseDto.ApiResponse;
import project.dto.responseDto.BorrowResponseDto;
import project.service.BorrowService;

@RestController
@RequestMapping("/borrows")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<BorrowResponseDto>>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                                                   @RequestParam(defaultValue = "10") Integer size){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"getting borrows by page",borrowService.getAllBorrowByPage(page,size)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ApiResponse<BorrowResponseDto>> getById(@PathVariable Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "getting borrow by id", borrowService.findById(id)));
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BorrowResponseDto>> borrowBook(@RequestParam Integer userId,
                                                                     @RequestParam Integer bookId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "book borrowed successfully", borrowService.borrowBook(userId, bookId)));
    }

    @PutMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BorrowResponseDto>> returnBook(@RequestParam Integer userId,
                                                                     @RequestParam Integer bookId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"book returned successfully", borrowService.returnBook(userId, bookId)));
    }

}
