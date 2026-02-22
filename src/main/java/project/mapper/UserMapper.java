package project.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import project.dto.requestDto.UserDto;
import project.dto.responseDto.UserResponseDto;
import project.entity.UserEntity;
import project.enums.UserRole;
import project.enums.UserStatus;

@Component
public class UserMapper {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserEntity toEntity(UserDto dto){
            UserEntity entity = new UserEntity();
            entity.setName(dto.getName());
            entity.setSurname(dto.getSurname());
            entity.setEmail(dto.getEmail());
            entity.setPhone(dto.getPhone());
            entity.setUsername(dto.getUsername());
            entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            entity.setRole(UserRole.ROLE_USER);
            entity.setStatus(UserStatus.ACTIVE);
            return entity;
    }

    public UserResponseDto toUserResponseDto(UserEntity entity){
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(entity.getId());
        userResponseDto.setName(entity.getName());
        userResponseDto.setSurname(entity.getSurname());
        userResponseDto.setEmail(entity.getEmail());
        userResponseDto.setPhone(entity.getPhone());
        userResponseDto.setUsername(entity.getUsername());
        userResponseDto.setRole(entity.getRole());
        userResponseDto.setStatus(entity.getStatus());
        return userResponseDto;
    }


}
