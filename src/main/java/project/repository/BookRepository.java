package project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.AuthorEntity;
import project.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity,Integer> {

    Page<BookEntity> findAllByAuthorEntityId(Integer id, Pageable pageable);
}
