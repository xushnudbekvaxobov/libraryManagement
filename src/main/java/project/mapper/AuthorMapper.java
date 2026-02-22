package project.mapper;

import org.springframework.stereotype.Component;
import project.dto.requestDto.AuthorDto;
import project.dto.responseDto.AuthorResponseDto;
import project.entity.AuthorEntity;

@Component
public class AuthorMapper {
    public AuthorResponseDto toAuthorResponseDto(AuthorEntity authorEntity) {
        AuthorResponseDto dto = new AuthorResponseDto();
        dto.setId(authorEntity.getId());
        dto.setName(authorEntity.getName());
        dto.setSurname(authorEntity.getSurname());
        dto.setBirthDate(authorEntity.getBirthDate());
        dto.setCountry(authorEntity.getCountry());
        return dto;
    }
    public AuthorEntity toAuthorEntity(AuthorDto authorDto){
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName(authorDto.getName());
        authorEntity.setSurname(authorDto.getSurname());
        authorEntity.setBirthDate(authorDto.getBirthDate());
        authorEntity.setCountry(authorDto.getCountry());
        return authorEntity;
    }
}
