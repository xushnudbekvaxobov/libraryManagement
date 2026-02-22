package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.BorrowEntity;
import project.enums.BorrowStatus;

import java.util.Optional;

public interface BorrowRepository extends JpaRepository<BorrowEntity,Integer> {
    Optional<BorrowEntity> findByUserEntity_IdAndBookEntity_IdAndStatus(Integer readerId, Integer bookId, BorrowStatus status);
}
