package project.repository;

import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.AuthorEntity;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {
    Boolean existsByNameAndSurname(String name, String surname);
}
