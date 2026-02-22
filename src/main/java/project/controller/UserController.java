package project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.responseDto.ApiResponse;
import project.dto.requestDto.AuthDto;
import project.dto.requestDto.ChangePasswordDto;
import project.dto.requestDto.UserDto;
import project.dto.responseDto.JwtResponseDto;
import project.dto.responseDto.UserResponseDto;
import project.dto.updateDto.UpdateUserDto;
import project.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
        private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody @Valid UserDto dto) {
        userService.register(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "User registered successfully", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponseDto>> login(@RequestBody AuthDto dto) {
        JwtResponseDto userResponseDto = userService.login(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "User logged in successfully", userResponseDto));
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<UserResponseDto>>> getAllReader(@RequestParam(defaultValue = "0") Integer page,
                                                                           @RequestParam(defaultValue = "10") Integer size){
        Page<UserResponseDto> readerResponseDto = userService.getAllReader(page,size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"getting all reader by page",readerResponseDto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<UserResponseDto>> getById(@PathVariable Integer id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"getting by id",userService.getById(id)));
    }

    @PatchMapping("/password/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<Void>> changePassword(@PathVariable Integer id,
                                                            @RequestBody ChangePasswordDto dto){
        userService.changePassword(id,dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"password changed successfully",null));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Integer id,
                                                    @RequestBody @Valid UpdateUserDto updateDto){
        userService.update(id,updateDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true,"updated",null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "user deleted by id", null));
    }
}
