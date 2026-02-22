package project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.entity.AuthorEntity;
import project.mapper.AuthorMapper;
import project.repository.AuthorRepository;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private AuthorMapper authorMapper;
    @InjectMocks
    private AuthorService authorService;

    @Test
    void allAuthor_success() {
        int page = 0;
        int size = 3;

        AuthorEntity a1 = new AuthorEntity();
        a1.setId(1);
        a1.setName("sdf");
        a1.setSurname("sddfvdf");
        a1.setBirthDate(LocalDate.of(1990,10,10));
        a1.setCountry("usa");

        AuthorEntity a2 = new AuthorEntity();
        a2.setId(2);
        a2.setName("sdfdf");
        a2.setSurname("sddfvdffdvre");
        a2.setBirthDate(LocalDate.of(1990,10,10));
        a2.setCountry("usa");

        List<AuthorEntity> authorEntities = List.of(a1, a2);

        

    }
}
