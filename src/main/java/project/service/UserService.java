package project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.requestDto.AuthDto;
import project.dto.requestDto.ChangePasswordDto;
import project.dto.requestDto.UserDto;
import project.dto.responseDto.JwtResponseDto;
import project.dto.responseDto.UserResponseDto;
import project.dto.updateDto.UpdateUserDto;
import project.entity.UserEntity;
import project.enums.UserStatus;
import project.exception.AppBadException;
import project.mapper.UserMapper;
import project.repository.UserRepository;
import project.util.JwtUtil;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserMapper mapper;
    @Autowired
    private JwtUtil jwtUtil;

    public void register(UserDto dto) {
        if(userRepository.existsByUsername(dto.getUsername())){
            throw new AppBadException("Username already exists,change username");
        }
        if (userRepository.existsByEmail(dto.getEmail())){
            throw new AppBadException("Email already exists,change email");
        }
        if (userRepository.existsByPhone(dto.getPhone())){
            throw new AppBadException("Phone number already exists,change phone number");
        }
        UserEntity userEntity = mapper.toEntity(dto);
        userRepository.save(userEntity);
        log.info("User registered successfully: {}", userEntity.getUsername());
    }

    public JwtResponseDto login(AuthDto authDto){
        Optional<UserEntity> optional = userRepository.findByUsername(authDto.getUsername());
        if(optional.isEmpty()) {
            throw new AppBadException("username not found");
        }
        UserEntity userEntity = optional.get();
        if(!bCryptPasswordEncoder.matches(authDto.getPassword(),userEntity.getPassword())) {
            throw new AppBadException("password is incorrect");
        }
        if(userEntity.getStatus() != UserStatus.ACTIVE) {
            throw new AppBadException("user is not active");
        }
        JwtResponseDto responseDto = new JwtResponseDto();
        responseDto.setUsername(userEntity.getUsername());
        responseDto.setRole(userEntity.getRole());
        responseDto.setJwt(jwtUtil.encode(userEntity.getUsername(), userEntity.getRole().name()));
        log.info("User logged in successfully: {}", userEntity.getUsername());
        return responseDto;
    }

    public Page<UserResponseDto> getAllReader(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("id"));
        Page<UserEntity> userEntityPage = userRepository.findAll(pageRequest);
        return userEntityPage.map(mapper::toUserResponseDto);
    }

    public  UserResponseDto getById(Integer id){
        Optional<UserEntity> readerEntity = userRepository.findById(id);
        if(readerEntity.isEmpty()){
            throw new AppBadException("Reader not found");
        }
        return mapper.toUserResponseDto(readerEntity.get());
    }

    public void changePassword(Integer id,ChangePasswordDto dto){
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isEmpty()){
            throw new AppBadException("User not found");
        }
        UserEntity user = userEntity.get();
        if(!bCryptPasswordEncoder.matches(dto.getOldPassword(),user.getPassword())){
            throw new AppBadException("Old password is incorrect");
        }
        user.setPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void update(Integer id, UpdateUserDto dto){
        Optional<UserEntity> optional = userRepository.findById(id);
        if(optional.isEmpty()){
            throw new AppBadException("user not found");
        }
        UserEntity entity = optional.get();
        if(dto.getName() != null){
            entity.setName(dto.getName());
        }
        if(dto.getSurname() != null){
            entity.setSurname(dto.getSurname());
        }
        if(dto.getEmail() != null){
            entity.setEmail(dto.getEmail());
        }
        if(dto.getPhone() != null){
            entity.setPhone(dto.getPhone());
        }
    }

    public void delete(Integer id){
        Optional<UserEntity> optional = userRepository.findById(id);
        if(optional.isEmpty()){
            throw new AppBadException("User not found");
        }
        userRepository.deleteById(id);
        log.info("user deleted successfully:{}", optional.get().getUsername());
    }

}
